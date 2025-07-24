package com.labcorp.api.base;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;

/**
 * Base class for API tests providing common functionality
 */
public class ApiTestBase {
    
    protected RequestSpecification requestSpec;
    protected Response response;
    protected JSONObject requestPayload;
    
    /**
     * Initialize the base request specification
     * @param baseUri the base URI for the API
     */
    public void initializeRequestSpec(String baseUri) {
        requestSpec = new RequestSpecBuilder()
                .setBaseUri(baseUri)
                .setContentType(ContentType.JSON)
                .build();
        
        // Initialize an empty JSON payload
        requestPayload = new JSONObject();
    }
    
    /**
     * Send a GET request to the specified endpoint
     * @param endpoint the API endpoint
     * @return the Response object
     */
    public Response sendGetRequest(String endpoint) {
        response = RestAssured.given()
                .spec(requestSpec)
                .when()
                .get(endpoint);
        return response;
    }
    
    /**
     * Send a POST request to the specified endpoint with the current payload
     * @param endpoint the API endpoint
     * @return the Response object
     */
    public Response sendPostRequest(String endpoint) {
        response = RestAssured.given()
                .spec(requestSpec)
                .body(requestPayload.toString())
                .when()
                .post(endpoint);
        return response;
    }
    
    /**
     * Get the current response
     * @return the Response object
     */
    public Response getResponse() {
        return response;
    }
}
