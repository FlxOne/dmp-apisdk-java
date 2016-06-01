package com.teradata.dmp.dpsdk;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.ning.http.client.AsyncCompletionHandler;
import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.Response;
import java.net.URI;
import java.util.HashMap;
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

    private final HashMap<String, Object> map = new HashMap<String, Object>();

    public static void main(String args[]) {
        Example example = new Example();
        example.data("gender", "male");

        JsonObject user = new JsonObject();
        user.addProperty("id", 1);
        user.addProperty("company", "Teradata");
        example.data("user", user);

        JsonArray users = new JsonArray();
        users.add(user);
        example.data("users", users);

        example.data("amount", 2);

        System.out.println(example.map.toString());
    }

    // setPixelId
    // setCustomerId
    // etc
    public void data(String key, JsonObject jsonObject) {
        map.put(key, jsonObject.toString());
    }

    public void data(String key, JsonArray jsonArray) {
        map.put(key, jsonArray.toString());
    }

    public void data(String key, JsonElement jsonElement) {
        map.put(key, jsonElement.toString());
    }

    public void data(String key, int integer) {
        map.put(key, integer);
    }

    public void data(String key, String string) {
        map.put(key, string);
    }

    public void data(String key, boolean bool) {
        map.put(key, bool);
    }

    public void data(String key, long lon) {
        map.put(key, lon);
    }

    public static void test() {
        try {
            URIBuilder builder = new URIBuilder();
            builder.setScheme("http").setHost("go.flx1.com").setPath("/dp");
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
                            runningCounter.notify(); // Done one
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
