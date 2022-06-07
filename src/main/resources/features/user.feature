Feature: (e2e) Validate users

  @users
  Scenario: (e2e) Validate that the response of the user request is 200
    Given the following get request that brings us the users
    And the response is 200 for getUsers

  @userLogin
  Scenario: (e2e) Validate that the response of the user login request is 200
    Given the following get request that brings us the user login
    And the response is 200 for getUserLogin

  @userLogout
  Scenario: (e2e) Validate that the response of the user logout request is 200
    Given the following get request that brings us the user logout
    And the response is 200 for getUserLogout

  @userPost
  Scenario Outline: (e2e) Validate user post
    Given the following post request that add users
    And the response is 200 for the user post
    And the user body response contains key name
    Then the body response contains the "<code>" of the user created

    Examples:
      | code |
      | 200  |

  @userWithArrayPost
  Scenario Outline: (e2e) Validate users with array post
    Given the following post request that add users with array
    And the response is 200 for the user with array post
    And the users with array body response contains key name
    Then the body response contains the "<message>" of the users with array created

    Examples:
      | message |
      | ok      |

  @userWithListPost
  Scenario Outline: (e2e) Validate users with list post
    Given the following post request that add users with list
    And the response is 200 for the user with list post
    And the users with list body response contains key name
    Then the body response contains the "<message>" of the users with list created

    Examples:
      | message |
      | ok      |

  @userPut
  Scenario Outline: (e2e) Validate update create user
    Given  the following put request that update user
    And the response is 200 for the user put
    Then the body response contains update "<code>" of the user put

    Examples:
      | code |
      | 200  |

  @userDelete
  Scenario: (e2e) Validate delete user
    Given the following post request that add users
    And the following delete request that delete user
    And the response is 200 for the user delete