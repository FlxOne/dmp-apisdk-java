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
    private final HashMap<String, Object> customData = new HashMap<>();
    private int attempts = 0;

    public int getAttempts() {
        return attempts;
    }

    public void addAttempt() {
        attempts++;
    }

    public void set(String key, String value) {
        defaults.put(key, value);
    }

    public void remove(String key) {
        defaults.remove(key);
    }
    
    public void removeCustomData(String key) {
        customData.remove(key);
    }

    public void setCustomData(String key, JsonObject jsonObject) {
        customData.put(key, jsonObject);
    }

    public void setCustomData(String key, JsonArray jsonArray) {
        customData.put(key, jsonArray);
    }

    public void setCustomData(String key, JsonElement jsonElement) {
        customData.put(key, jsonElement);
    }

    public void setCustomData(String key, int integer) {
        customData.put(key, integer);
    }

    public void setCustomData(String key, String string) {
        customData.put(key, string);
    }

    public void setCustomData(String key, boolean bool) {
        customData.put(key, bool);
    }

    public void setCustomData(String key, long lon) {
        customData.put(key, lon);
    }

    public HashMap<String, Object> getCustomData() {
        return customData;
    }

    public void clearCustomData() {
        customData.clear();
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
