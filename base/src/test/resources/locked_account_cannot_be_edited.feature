Feature: Locked accounts cannot be edited
  Only unlocked accounts can be edited

  Scenario: Account is locked
    Given account is locked
    When I try to edit the locked account's attributes
    Then I should "fail"

  Scenario: Account is not locked
    Given account is not locked
    When I try to edit the locked account's attributes
    Then I should "pass"