Feature: (e2e) Validate pets

  @pets
  Scenario: (e2e) Validate that the response of the pet request is 200
    Given the following get request that brings us the pets
    And the response is 200

  @petsFindByStatus
  Scenario: (e2e) Validate that the response of the pet request is 200 for getPetsFindByStatus
    Given the following get request that brings us the pets finding by status
    And the response is 200 for getPetsFindByStatus

  @petPost
  Scenario Outline: (e2e) Validate pet post
    Given the following post request that add pets
    And the response is 200 for the pet post
    And the pet body response contains key name
    Then the body response contains the "<name>" of the pet created

    Examples:
      | name     |
      | ponchito |

  @petPut
  Scenario Outline: (e2e) Validate update create pet
    Given  the following put request that update pet
    And the response is 200 for the pet put
    Then the body response contains update "<updated_name>"

    Examples:
      | updated_name |
      | kira         |

  @petDelete
  Scenario: (e2e) Validate delete pet
    Given the following post request that add pets
    And the following delete request that delete pet
    And the response is 200 for the pet delete