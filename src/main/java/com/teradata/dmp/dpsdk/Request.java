package com.teradata.dmp.dpsdk;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.HashMap;

/**
 * Request
 *
 * @author Teradata
 */
public class Request {

    private final HashMap<String, Object> defaults = new HashMap<>();
    private final HashMap<String, Object> data = new HashMap<>();

    public void set(String key, String value) {
        defaults.put(key, value);
    }

    public void remove(String key) {
        defaults.remove(key);
    }

    public void setData(String key, JsonObject jsonObject) {
        data.put(key, jsonObject);
    }

    public void removeData(String key) {
        data.remove(key);
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

    public void clearData() {
        data.clear();
    }

    public HashMap<String, Object> getDefaults() {
        return defaults;
    }

    public void clearDefaults() {
        defaults.clear();
    }

    public void setCustomerId(int customerId) {
        defaults.put(Dimensions.FLXONE_CUSTOMER_ID, customerId);
    }

    public void setPixelId(int pixelId) {
        defaults.put(Dimensions.PIXEL_ID, pixelId);
    }

}
