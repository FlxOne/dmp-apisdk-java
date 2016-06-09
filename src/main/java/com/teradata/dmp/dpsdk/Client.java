package com.teradata.dmp.dpsdk;

import com.google.gson.Gson;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.client.utils.URIBuilder;
import org.asynchttpclient.AsyncCompletionHandler;
import org.asynchttpclient.AsyncHttpClientConfig;
import org.asynchttpclient.DefaultAsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClientConfig;
import org.asynchttpclient.Response;

/**
 * Client
 *
 * @author Teradata
 */
public class Client {

    private final URIBuilder builder = new URIBuilder();
    private final DefaultAsyncHttpClient asyncHttpClient;
    private final AtomicLong runningCounter = new AtomicLong();
    private final ArrayList<String> hostAddresses = new ArrayList<>();
    private final String host;
    private final Random random;

    public Client(String host) {
        this.host = host;
        this.random = new Random();

        AsyncHttpClientConfig asyncHttpClientConfig = new DefaultAsyncHttpClientConfig.Builder().setKeepAlive(true).build();
        this.asyncHttpClient = new DefaultAsyncHttpClient(asyncHttpClientConfig);
    }

    public void refreshHostAddresses() {
        try {
            for (InetAddress addr : InetAddress.getAllByName(host)) {
                hostAddresses.add(addr.getHostAddress());
            }
        } catch (UnknownHostException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setScheme(String scheme) {
        builder.setScheme(scheme);
    }

    public void setPath(String path) {
        builder.setPath(path);
    }

    public void execute(Request request) {
        if (request.getAttempts() >= 3) {
            System.out.println("Max attempts reached");
            return;
        }

        request.addAttempt();

        try {
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

            builder.clearParameters();

            // Round-robin
            builder.setHost(this.hostAddresses.get(random.nextInt(this.hostAddresses.size())));

            request.set(Dimensions.EXTERNAL_DATA, new Gson().toJson(request.getCustomData()));
            request.getDefaults().entrySet().stream().forEach((entry) -> {
                builder.addParameter(entry.getKey(), entry.getValue().toString());
            });

            System.out.println(builder.toString());

            asyncHttpClient.prepareGet(builder.build().toString()).execute(new AsyncCompletionHandler<Response>() {
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
                    // Retry
                    execute(request);

                    System.out.println("onThrowable: " + t.getMessage());
                }

            });
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void close() {
        asyncHttpClient.close();
    }

}
