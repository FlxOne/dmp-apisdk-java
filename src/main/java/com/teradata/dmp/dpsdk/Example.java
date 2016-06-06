package com.teradata.dmp.dpsdk;

import com.google.gson.JsonObject;
import com.ning.http.client.AsyncCompletionHandler;
import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.Response;
import java.net.URI;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.client.utils.URIBuilder;

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

        Request request = new Request();

        // Default properties
        request.set(Dimensions.PIXEL_ID, "1");
        request.set(Dimensions.FLXONE_CUSTOMER_ID, "11");
        request.set(Dimensions.CAMPAIGN_ID, "123");

        // Custom properties
        request.setData("gender", "male");

        // Custom property as json object
        JsonObject user = new JsonObject();
        user.addProperty("id", 1);
        user.addProperty("company", "Teradata");
        request.setData("user", user);

        for (int i = 0; i < 10; i++) {
            client.execute(request);
        }
        
        client.close();
    }
    
}
