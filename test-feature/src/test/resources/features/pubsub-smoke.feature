Feature: Pub/Sub smoke with provider-neutral steps
  # Creates a topic, subscribes a queue, publishes a message and verifies delivery.

  Background:
    Given cloud provider is "aws"
    And cloud mode is "emulator"
    And cloud region is "eu-west-1"

  @pubsub
  Scenario: Publish to topic and receive on subscribed queue
    Given a topic named "dev-easy-test-topic"
    And a subscription from "dev-easy-test-topic" to queue "dev-easy-test-subscription"
    When I publish "{\"event\":\"hello\"}" to topic "dev-easy-test-topic"
    Then within 5s the queue "dev-easy-test-subscription" receives a message containing "hello"
