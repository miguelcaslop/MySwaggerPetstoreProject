Feature: (e2e) Validate store

  @store
  Scenario: (e2e) Validate that the response of the order request is 200
    Given the following get request that brings us the order by ID
    And the response is 200 for getOrderByID

  @storeFindByStatus
  Scenario: (e2e) Validate that the response of the pet request is 200 for getInventories
    Given the following get request that brings us the inventories finding by status
    And the response is 200 for getInventories

  @storePost
  Scenario: (e2e) Validate post an order for a pet
    Given the following post request that add order for a pet
    And the response is 200 for the store post
    And the store body response contains key name

  @storeDelete
  Scenario: (e2e) Validate delete order
    Given the following post request that add order for a pet
    And the following delete request that delete order
    And the response is 200 for the order delete