Feature: Storage smoke with provider-neutral steps
  # Runs the storage scenario end-to-end using provider-neutral glue.

  Background:
    Given cloud provider is "aws"
    And cloud mode is "emulator"
    And cloud region is "eu-west-1"

  @storage
  Scenario: Put and get a JSON object in storage
    Given a bucket named "dev-easy-test-bucket"
    When I put object from file "payloads/sample.json" as key "k1"
    Then I can get object with key "k1" containing JSON matching "{\"hello\":\"world\"}"
    And the bucket "dev-easy-test-bucket" contains 1 objects

  @storage
  Scenario: Put raw JSON and verify
    Given a bucket named "dev-easy-test-bucket-2"
    When I put JSON "{\"a\":1,\"b\":2}" as key "k2"
    Then I can get object with key "k2" containing JSON matching "{\"b\":2,\"a\":1}"
    And the bucket "dev-easy-test-bucket-2" contains 1 objects
