Feature: What is the fare for this CEP?
  Given a certain state, how much would one need to pay for fare?

  Scenario Outline: What is the fare for this CEP
    Given the CEP is "<cep>"
    When I ask what the fare is
    Then I should be told "<fare>"

  Examples:
    | cep            | fare   |
    | 01001000       | 7.85   |
    | 66615-850      | 20.83  |
    | 89848-000      | 17.30  |