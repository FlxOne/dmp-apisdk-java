package response;

import com.google.gson.JsonParser;

import java.util.AbstractMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public abstract class AbstractResponse implements IResponse {
    protected static final JsonParser jsonParser = new JsonParser();
    private final com.google.gson.JsonObject jsonOuterResponseObject;

    public AbstractResponse(String json) {
        this.jsonOuterResponseObject = jsonParser.parse(json).getAsJsonObject();
    }

    public ResponseStatus getStatus() {
        try {
            if (getResponseObject().getAsJsonPrimitive("status").getAsString().equals("OK")) {
                return ResponseStatus.OK;
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
            // Do nothing
        }
        return ResponseStatus.ERROR;
    }

    protected com.google.gson.JsonObject getResponseObject() {
        return jsonOuterResponseObject.getAsJsonObject("response");
    }

    public String getCsrfToken() {
        return jsonOuterResponseObject.has("csrf") ? jsonOuterResponseObject.get("csrf").getAsString() : null;
    }

    public Set<Map.Entry<String, JsonElement>> entrySet() {
        Set<Map.Entry<String, JsonElement>> es = new HashSet<Map.Entry<String, JsonElement>>();
        for (Map.Entry<String, com.google.gson.JsonElement> kv : this.getResponseObject().entrySet()) {
            es.add(new AbstractMap.SimpleImmutableEntry<String, JsonElement>(kv.getKey(), (JsonElement)kv.getValue()));
        }
        return es;
    }

    public boolean has(String memberName) {
        return getResponseObject().has(memberName);
    }

    public JsonElement get(String memberName) {
        return (JsonElement)getResponseObject().get(memberName);
    }

    public JsonPrimitive getAsJsonPrimitive(String memberName) {
        return new JsonPrimitive(getResponseObject().getAsJsonPrimitive(memberName));
    }

    public JsonArray getAsJsonArray(String memberName) {
        return new JsonArray(getResponseObject().getAsJsonArray(memberName));
    }

    public JsonObject getAsJsonObject(String memberName) {
        return new JsonObject(getResponseObject().getAsJsonObject(memberName));
    }

    public boolean equals(Object o) {
        return this.getResponseObject().equals(o);
    }

    public int hashCode() {
        return this.getResponseObject().hashCode();
    }

    public String toString() {
        return getResponseObject().toString();
    }
}
