// This is a documentation-only scaffold, not part of the Maven build.
// It illustrates how a JUnit 5 test could look once provider capabilities are available.
// Copy into a module's src/test/java to try it once AWS features land.

package examples.junit;

/**
 * Illustrative JUnit 5 test showing the intended DX for obtaining capabilities.
 * NOTE: This file is not compiled by Maven in this repo layout.
 */
public class WithCloudExampleTest {

    // Example (pseudo-code):
    //
    // @WithCloud(provider = CloudProvider.AWS, mode = CloudMode.EMULATOR, services = { CloudServiceType.STORAGE })
    // class AwsS3Test {
    //
    //     @Test
    //     void putAndGetObject(BlobStorage storage) {
    //         storage.ensureBucket("test-bucket");
    //         storage.putJson("test-bucket", "k1", "{\"id\":1,\"name\":\"Alice\"}");
    //         String json = storage.getAsString("test-bucket", "k1");
    //         assertTrue(json.contains("\"id\":1"));
    //     }
    // }
    //
    // Until the AWS adapter implements capabilities, keep this as a reference only.
}
