package com.teradata.dmp.apisdk.response;

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
        return JsonObject.toEntrySet(getResponseObject());
    }

    public boolean has(String memberName) {
        return getResponseObject().has(memberName);
    }

    public JsonElement get(String memberName) {
        com.google.gson.JsonElement elm = getResponseObject().get(memberName);
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

    public JsonPrimitive getAsJsonPrimitive(String memberName) {
        return new JsonPrimitive(getResponseObject().getAsJsonPrimitive(memberName));
    }

    public JsonArray getAsJsonArray(String memberName) {
        return new JsonArray(getResponseObject().getAsJsonArray(memberName));
    }

    public JsonObject getAsJsonObject(String memberName) {
        return new JsonObject(getResponseObject().getAsJsonObject(memberName));
    }

    @Override
    public boolean equals(Object o) {
        return this.getResponseObject().equals(o);
    }

    @Override
    public int hashCode() {
        return this.getResponseObject().hashCode();
    }

    @Override
    public String toString() {
        return getResponseObject().toString();
    }
}
