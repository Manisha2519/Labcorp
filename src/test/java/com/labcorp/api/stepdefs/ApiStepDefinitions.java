package com.labcorp.api.stepdefs;

import com.labcorp.api.base.ApiTestBase;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;

import java.util.List;
import java.util.Map;

public class ApiStepDefinitions extends ApiTestBase {

    @Given("I set the base URI to {string}")
    public void iSetTheBaseURITo(String baseUri) {
        initializeRequestSpec(baseUri);
    }

    @When("I send a GET request to {string}")
    public void iSendAGETRequestTo(String endpoint) {
        sendGetRequest(endpoint);
    }

    @Then("the response status code should be {int}")
    public void theResponseStatusCodeShouldBe(int expectedStatusCode) {
        int actualStatusCode = response.getStatusCode();
        Assert.assertEquals("Expected status code doesn't match actual status code", 
                expectedStatusCode, actualStatusCode);
    }

    @Then("the response should contain the following values:")
    public void theResponseShouldContainTheFollowingValues(DataTable dataTable) {
        // Get response as JSON string
        String responseBody = response.getBody().asString();
        
        // Convert DataTable to Map
        Map<String, String> expectedValues = dataTable.asMap(String.class, String.class);
        
        // Validate response status code
        Assert.assertEquals("Response status code is not 200", 200, response.getStatusCode());
        
        // Validate each expected value from the feature file
        for (Map.Entry<String, String> entry : expectedValues.entrySet()) {
            String key = entry.getKey();
            String expectedValue = entry.getValue();
            
            // Check if the key exists in the response
            Assert.assertTrue(String.format("%s is missing in response", key),
                    responseBody.contains(key));
            
            // Check if the value matches
            Assert.assertTrue(String.format("%s value is incorrect. Expected: %s", key, expectedValue),
                    responseBody.contains(String.format("\"%s\": \"%s\"", key, expectedValue)));
        }
        
        // Additional validations
        Assert.assertTrue("Headers are missing in response", 
                responseBody.contains("headers"));
        Assert.assertTrue("Content-Type header is missing", 
                responseBody.contains("Content-Type"));
        Assert.assertTrue("User-Agent header is missing", 
                responseBody.contains("User-Agent"));
        
        // Validate response headers
        Assert.assertTrue("Response headers are empty", response.getHeaders().size() > 0);
        Assert.assertNotNull("Content-Type header is missing in response", 
                response.getHeader("Content-Type"));
        
        // Log the response for debugging
        System.out.println("GET Response: " + responseBody);
    }

    @When("I prepare the order payload with the following details:")
    public void iPrepareTheOrderPayloadWithTheFollowingDetails(DataTable dataTable) {
        Map<String, String> orderDetails = dataTable.asMap(String.class, String.class);
        
        // Create customer object
        JSONObject customer = new JSONObject();
        customer.put("name", orderDetails.get("customer_name"));
        customer.put("email", orderDetails.get("customer_email"));
        customer.put("phone", orderDetails.get("customer_phone"));
        
        // Create address object
        JSONObject address = new JSONObject();
        address.put("street", orderDetails.get("street_address"));
        address.put("city", orderDetails.get("city"));
        address.put("state", orderDetails.get("state"));
        address.put("zipcode", orderDetails.get("zipcode"));
        address.put("country", orderDetails.get("country"));
        
        // Add order details to main payload
        requestPayload.put("order_id", orderDetails.get("order_id"));
        requestPayload.put("customer", customer);
        requestPayload.put("address", address);
    }

    @When("I add the following items to the order:")
    public void iAddTheFollowingItemsToTheOrder(DataTable dataTable) {
        List<Map<String, String>> items = dataTable.asMaps(String.class, String.class);
        JSONArray itemsArray = new JSONArray();
        
        for (Map<String, String> item : items) {
            JSONObject itemObj = new JSONObject();
            itemObj.put("product_id", item.get("product_id"));
            itemObj.put("name", item.get("name"));
            itemObj.put("color", item.get("color"));
            itemObj.put("price", Double.parseDouble(item.get("price")));
            itemsArray.put(itemObj);
        }
        
        requestPayload.put("items", itemsArray);
    }

    @When("I add payment information:")
    public void iAddPaymentInformation(DataTable dataTable) {
        Map<String, String> paymentInfo = dataTable.asMap(String.class, String.class);
        
        JSONObject payment = new JSONObject();
        payment.put("method", paymentInfo.get("method"));
        payment.put("number", paymentInfo.get("number"));
        payment.put("amount", Double.parseDouble(paymentInfo.get("amount")));
        payment.put("currency", paymentInfo.get("currency"));
        
        requestPayload.put("payment", payment);
    }

    @When("I add shipping details:")
    public void iAddShippingDetails(DataTable dataTable) {
        Map<String, String> shippingInfo = dataTable.asMap(String.class, String.class);
        
        JSONObject shipping = new JSONObject();
        shipping.put("method", shippingInfo.get("method"));
        shipping.put("cost", Double.parseDouble(shippingInfo.get("cost")));
        shipping.put("estimated_delivery", shippingInfo.get("estimated_delivery"));
        
        requestPayload.put("shipping", shipping);
        
        // Add order status and creation date
        requestPayload.put("order_status", "processing");
        requestPayload.put("created_at", "2024-11-07T12:00:00Z");
        
        // Log the complete payload for debugging
        System.out.println("POST Request Payload: " + requestPayload.toString(2));
    }

    @When("I send a POST request to {string}")
    public void iSendAPOSTRequestTo(String endpoint) {
        sendPostRequest(endpoint);
    }

    @Then("the response should validate customer information, payment details, and product information")
    public void theResponseShouldValidateCustomerInformationPaymentDetailsAndProductInformation() {
        // Get response as JSON string
        String responseBody = response.getBody().asString();
        
        // Validate response status code
        Assert.assertEquals("Response status code is not 200", 200, response.getStatusCode());
        
        // Validate customer information
        Assert.assertTrue("Customer information is missing in response", 
                responseBody.contains("customer"));
        Assert.assertTrue("Customer name is missing or incorrect", 
                responseBody.contains("\"name\": \"Jane Smith\""));
        Assert.assertTrue("Customer email is missing or incorrect", 
                responseBody.contains("jane.smith@example.com"));
        Assert.assertTrue("Customer phone is missing or incorrect", 
                responseBody.contains("1-987-654-3210"));
        
        // Validate payment details
        Assert.assertTrue("Payment details are missing in response", 
                responseBody.contains("payment"));
        Assert.assertTrue("Payment method is missing or incorrect", 
                responseBody.contains("\"method\": \"credit_card\""));
        Assert.assertTrue("Payment amount is missing or incorrect", 
                responseBody.contains("111.97"));
        Assert.assertTrue("Payment currency is missing or incorrect", 
                responseBody.contains("\"currency\": \"USD\""));
        
        // Validate product information
        Assert.assertTrue("Product information is missing in response", 
                responseBody.contains("items"));
        Assert.assertTrue("First product is missing or incorrect", 
                responseBody.contains("Wireless Headphones"));
        Assert.assertTrue("Second product is missing or incorrect", 
                responseBody.contains("Smartphone Case"));
        Assert.assertTrue("Product price is missing or incorrect", 
                responseBody.contains("79.99") && responseBody.contains("19.99"));
        
        // Validate shipping information
        Assert.assertTrue("Shipping information is missing in response", 
                responseBody.contains("shipping"));
        Assert.assertTrue("Shipping method is missing or incorrect", 
                responseBody.contains("\"method\": \"standard\""));
        Assert.assertTrue("Shipping cost is missing or incorrect", 
                responseBody.contains("8.99"));
        
        // Validate order status
        Assert.assertTrue("Order status is missing or incorrect", 
                responseBody.contains("\"order_status\": \"processing\""));
        
        // Log the response for debugging
        System.out.println("POST Response: " + responseBody);
    }

    @Then("the response should validate the following fields:")
    public void theResponseShouldValidateTheFollowingFields(DataTable dataTable) {
        // Get response as JSON string
        String responseBody = response.getBody().asString();
        
        // Log the response for debugging
        System.out.println("POST Response: " + responseBody);
        
        // Convert DataTable to Map
        Map<String, String> expectedFields = dataTable.asMap(String.class, String.class);
        
        // Validate response status code
        Assert.assertEquals("Response status code is not 200", 200, response.getStatusCode());
        
        // Since Beeceptor is an echo service, we'll check if our values are present in the response
        // rather than expecting a specific JSON structure
        for (Map.Entry<String, String> entry : expectedFields.entrySet()) {
            String fieldPath = entry.getKey();
            String expectedValue = entry.getValue();
            
            // For each expected value, check if it exists in the response
            Assert.assertTrue(
                String.format("Expected value '%s' for field '%s' not found in response", 
                    expectedValue, fieldPath),
                responseBody.contains(expectedValue)
            );
            
            // Also check if the field name exists in the response
            String fieldName = fieldPath;
            if (fieldPath.contains(".")) {
                fieldName = fieldPath.substring(fieldPath.lastIndexOf(".") + 1);
            } else if (fieldPath.contains("[")) {
                fieldName = fieldPath.substring(0, fieldPath.indexOf("["));
            }
            
            Assert.assertTrue(
                String.format("Field '%s' not found in response", fieldName),
                responseBody.contains(fieldName)
            );
        }
        
        System.out.println("POST Response: " + responseBody);
    }
}
