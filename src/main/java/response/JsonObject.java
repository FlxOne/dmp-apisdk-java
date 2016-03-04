package response;

import java.util.AbstractMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by rv186026 on 3/4/16.
 */
public class JsonObject {
    private final com.google.gson.JsonObject o;

    public JsonObject(com.google.gson.JsonObject o) {
        this.o = o;
    }

    public Set<Map.Entry<String, JsonElement>> entrySet() {
        Set<Map.Entry<String, JsonElement>> es = new HashSet<Map.Entry<String, JsonElement>>();
        for (Map.Entry<String, com.google.gson.JsonElement> kv : this.o.entrySet()) {
            es.add(new AbstractMap.SimpleImmutableEntry<String, JsonElement>(kv.getKey(), (JsonElement)kv.getValue()));
        }
        return es;
    }

    public boolean has(String memberName) {
        return o.has(memberName);
    }

    public JsonPrimitive getAsJsonPrimitive(String memberName) {
        return new JsonPrimitive(o.getAsJsonPrimitive(memberName));
    }

    public JsonArray getAsJsonArray(String memberName) {
        return new JsonArray(o.getAsJsonArray(memberName));
    }

    public JsonObject getAsJsonObject(String memberName) {
        return new JsonObject(o.getAsJsonObject(memberName));
    }

    public boolean equals(Object o) {
        return o.equals(o);
    }

    public int hashCode() {
        return o.hashCode();
    }
}
