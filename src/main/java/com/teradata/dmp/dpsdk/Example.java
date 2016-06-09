package com.teradata.dmp.dpsdk;

import com.google.gson.JsonObject;

/**
 * Example
 *
 * @author Teradata
 */
public class Example {

    public static void main(String args[]) {
        Client client = new Client();
        client.setScheme("http");
        client.setHost("go.flx1.com");
        client.setPath("/dp");

        for (int i = 0; i < 10; i++) {
            Request request = new Request();

            // Default properties
            request.set(Dimensions.PIXEL_ID, "1");
            request.set(Dimensions.FLXONE_CUSTOMER_ID, "11");
            request.set(Dimensions.CAMPAIGN_ID, "123");

            // Custom properties
            request.setCustomData("gender", "male");

            // Custom property as json object
            JsonObject user = new JsonObject();
            user.addProperty("id", 1);
            user.addProperty("company", "Teradata");
            request.setCustomData("user", user);

            // Execute
            client.execute(request);
        }
    }
    
}
