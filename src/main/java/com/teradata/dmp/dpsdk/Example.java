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

        client.execute(request);
    }

    public static void test() {
        try {
            URIBuilder builder = new URIBuilder();
            builder.setScheme("https").setHost("go.flx1.com").setPath("/dp");
            builder.setParameter("_check", "1");
            builder.setParameter("_nr", "1");
            builder.setParameter("t", "js");
            builder.setParameter("m", "11");

            AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
            final AtomicLong runningCounter = new AtomicLong();

            try {
                builder.setParameter("id", "1");
                builder.setParameter("uuid", "123-456");
                builder.setParameter("data", "{\"total_exposure_time\": 123}");

                URI url = builder.build();

                long running = runningCounter.incrementAndGet();
                if (running > 100) {
                    try {
                        synchronized (runningCounter) {
                            runningCounter.wait();
                        }
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }

                asyncHttpClient.prepareGet(url.toString()).execute(new AsyncCompletionHandler<Response>() {

                    @Override
                    public Response onCompleted(Response response) throws Exception {
                        synchronized (runningCounter) {
                            runningCounter.decrementAndGet();
                            runningCounter.notify();
                        }

                        return response;
                    }

                    @Override
                    public void onThrowable(Throwable t) {
                        System.out.println("onThrowable: " + t.getMessage());
                    }
                });
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            // Close when done
            asyncHttpClient.closeAsynchronously();
        } catch (Exception ex) {
            Logger.getLogger(Example.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
