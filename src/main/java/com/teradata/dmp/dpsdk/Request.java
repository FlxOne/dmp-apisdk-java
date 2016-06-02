package com.teradata.dmp.dpsdk;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.HashMap;
import org.apache.http.client.utils.URIBuilder;

/**
 * Request
 *
 * @author Teradata
 */
public class Request {

    private final HashMap<String, Object> defaults = new HashMap<>();
    private final HashMap<String, Object> data = new HashMap<>();
    private final URIBuilder builder = new URIBuilder();

    public void setData(String key, JsonObject jsonObject) {
        data.put(key, jsonObject);
    }

    public void setData(String key, JsonArray jsonArray) {
        data.put(key, jsonArray);
    }

    public void setData(String key, JsonElement jsonElement) {
        data.put(key, jsonElement);
    }

    public void setData(String key, int integer) {
        data.put(key, integer);
    }

    public void setData(String key, String string) {
        data.put(key, string);
    }

    public void setData(String key, boolean bool) {
        data.put(key, bool);
    }

    public void setData(String key, long lon) {
        data.put(key, lon);
    }

    public HashMap<String, Object> getData() {
        return data;
    }

    public HashMap<String, Object> getDefaults() {
        return defaults;
    }

    public void setCustomerId(int customerId) {
        defaults.put("m", customerId);
    }

    public void setPixelId(int pixelId) {
        defaults.put("id", pixelId);
    }

    public void send() {
        defaults.put("data", new Gson().toJson(this.getData()));

        this.getDefaults().entrySet().stream().forEach((entry) -> {
            builder.addParameter(entry.getKey(), entry.getValue().toString());
        });

        System.out.println(builder.toString());
    }

    public void setScheme(String scheme) {
        builder.setScheme(scheme);
    }

    public void setHost(String host) {
        builder.setHost(host);
    }

    public void setPath(String path) {
        builder.setPath(path);
    }
}
