package response;

import com.google.gson.JsonParser;

import java.util.AbstractMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public abstract class AbstractResponse implements IResponse {
    protected static final JsonParser jsonParser = new JsonParser();
    private final com.google.gson.JsonObject jsonResponseObject;

    public AbstractResponse(String json) {
        this.jsonResponseObject = jsonParser.parse(json).getAsJsonObject();
    }

    public Set<Map.Entry<String, JsonElement>> entrySet() {
        Set<Map.Entry<String, JsonElement>> es = new HashSet<Map.Entry<String, JsonElement>>();
        for (Map.Entry<String, com.google.gson.JsonElement> kv : this.jsonResponseObject.entrySet()) {
            es.add(new AbstractMap.SimpleImmutableEntry<String, JsonElement>(kv.getKey(), (JsonElement)kv.getValue()));
        }
        return es;
    }

    public boolean has(String memberName) {
        return jsonResponseObject.has(memberName);
    }

    public JsonElement get(String memberName) {
        return (JsonElement)jsonResponseObject.get(memberName);
    }

    public JsonPrimitive getAsJsonPrimitive(String memberName) {
        return new JsonPrimitive(jsonResponseObject.getAsJsonPrimitive(memberName));
    }

    public JsonArray getAsJsonArray(String memberName) {
        return new JsonArray(jsonResponseObject.getAsJsonArray(memberName));
    }

    public JsonObject getAsJsonObject(String memberName) {
        return new JsonObject(jsonResponseObject.getAsJsonObject(memberName));
    }

    public boolean equals(Object o) {
        return this.jsonResponseObject.equals(o);
    }

    public int hashCode() {
        return this.jsonResponseObject.hashCode();
    }
}
