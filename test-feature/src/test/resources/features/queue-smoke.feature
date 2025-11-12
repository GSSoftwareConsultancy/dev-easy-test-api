Feature: Queue smoke with provider-neutral steps
  # Runs the queue scenarios end-to-end using provider-neutral glue.

  Background:
    Given cloud provider is "aws"
    And cloud mode is "emulator"
    And cloud region is "eu-west-1"

  @queue
  Scenario: Send and receive a simple message
    Given a queue named "dev-easy-test-orders"
    When I send a message "{\"orderId\":123}"
    Then within 5s I receive a message matching "orderId"

  @queue
  Scenario: Send multiple messages and receive one
    Given a queue named "dev-easy-test-batch"
    When I send 3 messages
    Then within 5s I receive a message matching "message-"

  @queue
  Scenario: Empty queue has no messages
    Given a queue named "dev-easy-test-empty"
    Then no messages are available
