package com.labcorp.api.utils;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Utility class for handling JSON payloads
 */
public class PayloadUtils {

    /**
     * Creates a sample order payload matching the example in the requirements
     * @return JSONObject containing the order payload
     */
    public static JSONObject createSampleOrderPayload() {
        JSONObject payload = new JSONObject();
        
        // Order ID
        payload.put("order_id", "12345");
        
        // Customer information
        JSONObject customer = new JSONObject();
        customer.put("name", "Jane Smith");
        customer.put("email", "jane.smith@example.com");
        customer.put("phone", "1-987-654-3210");
        payload.put("customer", customer);
        
        // Address information
        JSONObject address = new JSONObject();
        address.put("street", "123 Main Street");
        address.put("city", "Metropolis");
        address.put("state", "NY");
        address.put("zipcode", "10001");
        address.put("country", "USA");
        payload.put("address", address);
        
        // Items
        JSONArray items = new JSONArray();
        
        JSONObject item1 = new JSONObject();
        item1.put("product_id", "A101");
        item1.put("name", "Wireless Headphones");
        item1.put("color", "black");
        item1.put("price", 79.99);
        items.put(item1);
        
        JSONObject item2 = new JSONObject();
        item2.put("product_id", "B202");
        item2.put("name", "Smartphone Case");
        item2.put("color", "blue");
        item2.put("price", 19.99);
        items.put(item2);
        
        payload.put("items", items);
        
        // Payment information
        JSONObject payment = new JSONObject();
        payment.put("method", "credit_card");
        payment.put("number", "1234-5678-9012-3456");
        payment.put("amount", 111.97);
        payment.put("currency", "USD");
        payload.put("payment", payment);
        
        // Shipping information
        JSONObject shipping = new JSONObject();
        shipping.put("method", "standard");
        shipping.put("cost", 8.99);
        shipping.put("estimated_delivery", "2024-11-15");
        payload.put("shipping", shipping);
        
        // Order status and timestamp
        payload.put("order_status", "processing");
        payload.put("created_at", "2024-11-07T12:00:00Z");
        
        return payload;
    }
    
    /**
     * Validates if the response contains all required fields for an order
     * @param responseJson the JSON response to validate
     * @return boolean indicating if all required fields are present
     */
    public static boolean validateOrderResponse(JSONObject responseJson) {
        try {
            // Check for required top-level fields
            if (!responseJson.has("order_id") || 
                !responseJson.has("customer") || 
                !responseJson.has("items") || 
                !responseJson.has("payment")) {
                return false;
            }
            
            // Check customer details
            JSONObject customer = responseJson.getJSONObject("customer");
            if (!customer.has("name") || !customer.has("email")) {
                return false;
            }
            
            // Check items array
            JSONArray items = responseJson.getJSONArray("items");
            if (items.length() == 0) {
                return false;
            }
            
            // Check first item
            JSONObject firstItem = items.getJSONObject(0);
            if (!firstItem.has("product_id") || !firstItem.has("name") || !firstItem.has("price")) {
                return false;
            }
            
            // Check payment
            JSONObject payment = responseJson.getJSONObject("payment");
            if (!payment.has("method") || !payment.has("amount")) {
                return false;
            }
            
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
