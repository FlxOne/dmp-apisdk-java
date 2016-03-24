package com.teradata.dmp.apisdk.response;

import java.util.Map;
import java.util.Set;

public interface IResponse {
    Set<Map.Entry<String, JsonElement>> entrySet();

    boolean has(String memberName);

    JsonElement get(String memberName);

    JsonPrimitive getAsJsonPrimitive(String memberName);

    JsonArray getAsJsonArray(String memberName);

    JsonObject getAsJsonObject(String memberName);

    @Override
    boolean equals(Object o);

    @Override
    int hashCode();

    public String getCsrfToken();
}
