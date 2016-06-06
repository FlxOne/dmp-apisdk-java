package com.teradata.dmp.dpsdk;

import com.google.gson.Gson;
import org.apache.http.client.utils.URIBuilder;

/**
 * Client
 *
 * @author Teradata
 */
public class Client {

    private final URIBuilder builder = new URIBuilder();

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
    }

}
