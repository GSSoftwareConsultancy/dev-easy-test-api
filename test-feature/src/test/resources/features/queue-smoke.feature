Feature: Queue smoke with provider-neutral steps
  # Runs the queue scenario end-to-end using provider-neutral glue.

  Background:
    Given cloud provider is "aws"
    And cloud mode is "emulator"
    And cloud region is "eu-west-1"

  @queue
  Scenario: Send and receive a simple message
    Given a queue named "dev-easy-test-orders"
    When I send a message "{\"orderId\":123}"
    Then within 5s I receive a message matching "orderId=123"
