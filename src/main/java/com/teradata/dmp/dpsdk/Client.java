package com.teradata.dmp.dpsdk;

import com.google.gson.Gson;
import com.ning.http.client.AsyncCompletionHandler;
import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.Response;
import java.net.URI;
import java.util.concurrent.atomic.AtomicLong;
import org.apache.http.client.utils.URIBuilder;

/**
 * Client
 *
 * @author Teradata
 */
public class Client {

    private final URIBuilder builder = new URIBuilder();
    private final AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
    private final AtomicLong runningCounter = new AtomicLong();

    public void setScheme(String scheme) {
        builder.setScheme(scheme);
    }

    public void setHost(String host) {
        builder.setHost(host);
    }

    public void setPath(String path) {
        builder.setPath(path);
    }

    public void execute(Request request) {
        request.set(Dimensions.EXTERNAL_DATA, new Gson().toJson(request.getData()));
        request.getDefaults().entrySet().stream().forEach((entry) -> {
            builder.addParameter(entry.getKey(), entry.getValue().toString());
        });

        System.out.println(builder.toString());

        // @todo: clear map?

        try {
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
    }

    public void close() {
        asyncHttpClient.closeAsynchronously();
    }

}
