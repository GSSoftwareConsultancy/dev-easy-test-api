Feature: Storage and Queue smoke with provider-neutral steps
  # This feature demonstrates how to select a cloud provider/mode and
  # exercise storage and queue capabilities via provider-neutral steps.
  # It is intended for illustration. The AWS adapter gains functionality in upcoming PRs.

  Background:
    Given cloud provider is "aws"
    And cloud mode is "emulator"
    And cloud region is "eu-west-1"

  @storage
  Scenario: Put and get a JSON object in storage
    Given a bucket named "dev-easy-test-bucket"
    When I put object from file "examples/payloads/sample.json" as key "k1"
    Then I can get object with key "k1" containing JSON matching "{\"hello\":\"world\"}"

  @queue @pending
  Scenario: Send and receive a simple message (pending until SQS implemented)
    Given a queue named "dev-easy-test-orders"
    When I send a message "{\"orderId\":123}"
    Then within 5s I receive a message matching "orderId=123"
