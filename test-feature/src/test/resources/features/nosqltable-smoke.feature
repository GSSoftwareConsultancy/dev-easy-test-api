Feature: NoSqlTable smoke with provider-neutral steps
  # Runs the NoSqlTable scenarios end-to-end using provider-neutral glue.

  Background:
    Given cloud provider is "aws"
    And cloud mode is "emulator"
    And cloud region is "eu-west-1"

  @nosql
  Scenario: Create table, put item, get it, and scan
    Given a table named "dev-easy-test-users" with partition key "userId"
    When I put item with "userId"="u-1" and data "{\"name\":\"Alice\",\"age\":30}"
    Then I can get item with "userId"="u-1"
    And scanning the table returns 1 items
