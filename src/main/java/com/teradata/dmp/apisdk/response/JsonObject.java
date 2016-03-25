package com.teradata.dmp.apisdk.response;

import java.util.AbstractMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class JsonObject extends JsonElement {
    private final com.google.gson.JsonObject o;

    public JsonObject(com.google.gson.JsonObject o) {
        this.o = o;
    }

    public Set<Map.Entry<String, JsonElement>> entrySet() {
        return toEntrySet(this.o);
    }

    public static Set<Map.Entry<String, JsonElement>> toEntrySet(com.google.gson.JsonObject obj) {
        Set<Map.Entry<String, JsonElement>> es = new HashSet<Map.Entry<String, JsonElement>>();
        for (Map.Entry<String, com.google.gson.JsonElement> kv : obj.entrySet()) {
            com.google.gson.JsonElement elm = kv.getValue();
            JsonElement wrappedElm;

            if (elm instanceof com.google.gson.JsonObject) {
                wrappedElm = new JsonObject((com.google.gson.JsonObject)elm);
            } else if (elm instanceof com.google.gson.JsonPrimitive) {
                wrappedElm = new JsonPrimitive((com.google.gson.JsonPrimitive)elm);
            } else if (elm instanceof com.google.gson.JsonArray) {
                wrappedElm = new JsonArray((com.google.gson.JsonArray)elm);
            } else if (elm instanceof com.google.gson.JsonNull) {
                wrappedElm = JsonNull.INSTANCE;
            } else {
                throw new RuntimeException("Type not supported: " + elm.getClass().getName());
            }

            es.add(new AbstractMap.SimpleImmutableEntry<String, JsonElement>(kv.getKey(), wrappedElm));
        }
        return es;
    }

    public JsonElement get(String memberName) {
        com.google.gson.JsonElement elm = o.get(memberName);
        JsonElement wrappedElm;
        if (elm instanceof com.google.gson.JsonObject) {
            wrappedElm = new JsonObject((com.google.gson.JsonObject)elm);
        } else if (elm instanceof com.google.gson.JsonPrimitive) {
            wrappedElm = new JsonPrimitive((com.google.gson.JsonPrimitive)elm);
        } else if (elm instanceof com.google.gson.JsonArray) {
            wrappedElm = new JsonArray((com.google.gson.JsonArray)elm);
        } else if (elm instanceof com.google.gson.JsonNull) {
            wrappedElm = JsonNull.INSTANCE;
        } else {
            throw new RuntimeException("Type not supported: " + elm.getClass().getName());
        }
        return wrappedElm;
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

    @Override
    public boolean equals(Object o) {
        return o.equals(o);
    }

    @Override
    public int hashCode() {
        return o.hashCode();
    }

    @Override
    public String toString() {
        return o.toString();
    }
}
