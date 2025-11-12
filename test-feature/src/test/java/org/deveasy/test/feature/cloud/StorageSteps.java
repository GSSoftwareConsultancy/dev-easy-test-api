package org.deveasy.test.feature.cloud;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.deveasy.test.core.cloud.capability.BlobStorage;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Provider-neutral Storage (BlobStorage) steps backed by the resolved CloudAdapter.
 */
public class StorageSteps {

    private BlobStorage storage;
    private String bucketName;

    @Given("a bucket named {string}")
    public void aBucketNamed(String name) {
        this.storage = CloudSelectionSteps.adapter().blobStorage();
        this.bucketName = name;
        storage.ensureBucket(name);
        ScenarioState.put("bucketName", name);
    }

    @When("I put object from file {string} as key {string}")
    public void iPutObjectFromFileAsKey(String path, String key) throws IOException {
        if (storage == null) {
            storage = CloudSelectionSteps.adapter().blobStorage();
            bucketName = ScenarioState.get("bucketName");
        }
        byte[] data = readBytesSmart(path);
        storage.putObject(bucketName, key, data, contentTypeFor(path));
        ScenarioState.put("lastKey", key);
    }

    @Then("I can get object with key {string} containing JSON matching {string}")
    public void iCanGetObjectContainingJson(String key, String expectedSubstring) {
        if (storage == null) {
            storage = CloudSelectionSteps.adapter().blobStorage();
            bucketName = ScenarioState.get("bucketName");
        }
        byte[] bytes = storage.getObject(bucketName, key);
        assertNotNull(bytes, "Object should exist for key: " + key);
        String json = new String(bytes);
        assertTrue(json.contains(expectedSubstring), () -> "JSON did not contain expected substring. json=" + json);
    }

    private static String contentTypeFor(String path) {
        String p = path.toLowerCase();
        if (p.endsWith(".json")) return "application/json";
        if (p.endsWith(".txt")) return "text/plain";
        return "application/octet-stream";
    }

    private static byte[] readBytesSmart(String path) throws IOException {
        // Try file system path relative to repo/module
        Path p = Paths.get(path);
        if (Files.exists(p)) {
            return Files.readAllBytes(p);
        }
        // Try classpath resource under test resources
        String cleaned = path.startsWith("/") ? path.substring(1) : path;
        try (InputStream is = StorageSteps.class.getClassLoader().getResourceAsStream(cleaned)) {
            if (is != null) {
                return is.readAllBytes();
            }
        }
        // Try without folders, just the file name in resources
        String fileName = Paths.get(cleaned).getFileName().toString();
        try (InputStream is = StorageSteps.class.getClassLoader().getResourceAsStream(fileName)) {
            if (is != null) {
                return is.readAllBytes();
            }
        }
        throw new IOException("Could not read payload from path or classpath: " + path);
    }
}
