Feature: API Testing with REST Assured

  Scenario: Verify GET request to retrieve customer information
    Given I set the base URI to "https://echo.free.beeceptor.com"
    When I send a GET request to "/sample-request/author-beeceptor"
    Then the response status code should be 200
    And the response should contain the following values:
      | path     | /sample-request/author-beeceptor |
      | protocol | https                           |
      | host     | echo.free.beeceptor.com         |
      | method   | GET                             |

  Scenario: Verify POST request to create a new customer order
    Given I set the base URI to "https://echo.free.beeceptor.com"
    When I prepare the order payload with the following details:
      | order_id         | 12345                |
      | customer_name    | Jane Smith           |
      | customer_email   | jane.smith@example.com |
      | customer_phone   | 1-987-654-3210       |
      | street_address   | 123 Main Street      |
      | city             | Metropolis           |
      | state            | NY                   |
      | zipcode          | 10001                |
      | country          | USA                  |
    And I add the following items to the order:
      | product_id | name                | color  | price |
      | A101       | Wireless Headphones | black  | 79.99 |
      | B202       | Smartphone Case     | blue   | 19.99 |
    And I add payment information:
      | method     | credit_card         |
      | number     | 1234-5678-9012-3456 |
      | amount     | 111.97              |
      | currency   | USD                 |
    And I add shipping details:
      | method              | standard            |
      | cost                | 8.99                |
      | estimated_delivery  | 2024-11-15          |
    And I send a POST request to "/sample-request/author-beeceptor"
    Then the response status code should be 200
    And the response should validate the following fields:
      | customer.name          | Jane Smith           |
      | customer.email         | jane.smith@example.com |
      | payment.method         | credit_card          |
      | payment.amount         | 111.97               |
      | payment.currency       | USD                  |
      | items[0].name          | Wireless Headphones  |
      | items[1].name          | Smartphone Case      |
      | shipping.method        | standard             |
      | shipping.cost          | 8.99                 |
      | order_status           | processing           |
