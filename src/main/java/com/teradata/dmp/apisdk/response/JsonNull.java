package com.teradata.dmp.apisdk.response;

public class JsonNull extends JsonElement {
    public static final JsonNull INSTANCE = new JsonNull();

    /** @deprecated */
    @Deprecated
    public JsonNull() {
    }

    JsonNull deepCopy() {
        return INSTANCE;
    }

    public int hashCode() {
        return JsonNull.class.hashCode();
    }

    public boolean equals(Object other) {
        return this == other || other instanceof JsonNull;
    }

    public String toString() {
        return com.google.gson.JsonNull.INSTANCE.toString();
    }
}
