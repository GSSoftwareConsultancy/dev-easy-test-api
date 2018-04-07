package org.deveasy.test.feature;

import com.fasterxml.jackson.databind.ObjectMapper;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.deveasy.test.core.ResourceHelper;
import org.deveasy.test.feature.org.deveasy.test.feature.state.ScenarioState;
import org.junit.Assert;
import org.skyscreamer.jsonassert.JSONAssert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

import static org.apache.http.protocol.HTTP.USER_AGENT;

/**
 * Generic steps for managing http requests /responses using GET, POST and PUT methods
 *
 * @see HttpClient
 *
 */

public class RestClientSteps {


    private static final Logger logger = LoggerFactory.getLogger(RestClientSteps.class);

    public static final String PORT = "port";
    public static final String HTTP_RESPONSE_CODE = "httpResponseCode";
    public static final String RESPONSE_OBJECT = "responseObject";
    public static final String APPLICATION_JSON = "application/json";
    public static final String HTTP_RESPONSE_ENTITY = "httpResponseEntity";

    private final String baseUri = "http://localhost:%s/%s";

    private final String basePort;

    private final ScenarioState sharedCucumberStepsData;

    public RestClientSteps(ScenarioState sharedCucumberStepsData) {
        this.sharedCucumberStepsData = sharedCucumberStepsData;
        this.basePort = (String) sharedCucumberStepsData.keyValuePairs.get(PORT);
    }


    @And("^I perform a GET on \"([^\"]*)\"$")
    public void iPerformAGetOnProvidedUrl(String uri) throws Throwable {
        HttpGet request = new HttpGet(String.format(baseUri, basePort, uri));
        logger.info("get-request : " + request);

        HttpClient httpClient = HttpClientBuilder.create().build();
        request.addHeader("User-Agent", USER_AGENT);
        request.addHeader("Content-type", "application/json");
        HttpResponse response = httpClient.execute(request);
        logger.info("get-response : " + response);

        sharedCucumberStepsData.keyValuePairs.put(HTTP_RESPONSE_CODE, response.getStatusLine().getStatusCode());
        sharedCucumberStepsData.keyValuePairs.put(HTTP_RESPONSE_ENTITY, response);
    }

    @And("^I perform a POST on \"([^\"]*)\" with data \"([^\"]*)\"$")
    public void iPerformAPostTWithProvidedJsonData(String uri, String jsonFile) throws Throwable {
        HttpPost request = new HttpPost(String.format(baseUri, basePort, uri));
        logger.info("post-request : " + request);
        HttpClient httpClient = HttpClientBuilder.create().build();
        request.addHeader("User-Agent", USER_AGENT);
        request.addHeader("Content-type", APPLICATION_JSON);
        if (!jsonFile.equals("")) {
            String json = readFile(jsonFile);
            logger.info("post-json : " + json);
            request.setEntity(new StringEntity(json));
        }
        HttpResponse response = httpClient.execute(request);

        sharedCucumberStepsData.keyValuePairs.put(HTTP_RESPONSE_CODE, response.getStatusLine().getStatusCode());
        sharedCucumberStepsData.keyValuePairs.put(HTTP_RESPONSE_ENTITY, response);
    }

    @And("^I perform a PUT on \"([^\"]*)\" with data \"([^\"]*)\"$")
    public void iPerformAPutOnWithProvidedJsonData(String uri, String jsonFile) throws Throwable {
        HttpPut request = new HttpPut(String.format(baseUri, basePort, uri));
        logger.info("put-request : " + request);
        HttpClient httpClient = HttpClientBuilder.create().build();
        request.addHeader("User-Agent", USER_AGENT);
        request.addHeader("Content-type", APPLICATION_JSON);
        if (!jsonFile.equals("")) {
            String json = readFile(jsonFile);
            logger.info("put-json : " + request);
            request.setEntity(new StringEntity(json));
        }
        HttpResponse response = httpClient.execute(request);

        sharedCucumberStepsData.keyValuePairs.put(HTTP_RESPONSE_CODE, response.getStatusLine().getStatusCode());
        sharedCucumberStepsData.keyValuePairs.put(HTTP_RESPONSE_ENTITY, response);
    }


    @Then("^The response should map to \"([^\"]*)\"$")
    public void theResponseShouldMapToProvidedClass(String classMapping) throws Throwable {
        Class marshallClass = Class.forName(classMapping);

        HttpResponse response = (HttpResponse) sharedCucumberStepsData.keyValuePairs.get(HTTP_RESPONSE_ENTITY);
        String strResponse = EntityUtils.toString(response.getEntity());
        System.out.println(strResponse);

        ObjectMapper objectMapper = new ObjectMapper();
        Object responseObject = objectMapper.readValue(strResponse, marshallClass);

        sharedCucumberStepsData.keyValuePairs.put(RESPONSE_OBJECT, responseObject);
    }


    @Then("^The response should map to a list of \"([^\"]*)\"$")
    public void theResponseShouldMapToAListOf(String classMapping) throws Throwable {
        Class marshallClass = Class.forName(classMapping);

        HttpResponse response = (HttpResponse) sharedCucumberStepsData.keyValuePairs.get(HTTP_RESPONSE_ENTITY);
        String strResponse = EntityUtils.toString(response.getEntity());
        System.out.println(strResponse);

        ObjectMapper objectMapper = new ObjectMapper();
        List<Object> responseObject = objectMapper.readValue(strResponse, objectMapper.getTypeFactory().constructCollectionType(List.class, marshallClass));

        sharedCucumberStepsData.keyValuePairs.put(RESPONSE_OBJECT, responseObject);
    }


    @And("^The response code should be \"([^\"]*)\"$")
    public void theResponseCodeShouldBe(String responseCode) throws Throwable {
        String response = sharedCucumberStepsData.keyValuePairs.get(HTTP_RESPONSE_CODE).toString();
        Assert.assertEquals(responseCode, response);
    }


    @Then("^The response should match  to \"([^\"]*)\"$")
    public void theResponseShouldMatchProvidedJson(String expectedJson) throws Throwable {
        HttpResponse response = (HttpResponse) sharedCucumberStepsData.keyValuePairs.get(HTTP_RESPONSE_ENTITY);
        String strResponse = EntityUtils.toString(response.getEntity());
        JSONAssert.assertEquals(readFile(expectedJson), strResponse, Boolean.FALSE);

    }


    public static String readFile(String path) throws IOException {
        return ResourceHelper.readJson(path);
    }

}
