package com.teradata.dmp.apisdk.response;

import com.google.gson.*;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class JsonArray extends JsonElement implements Iterable<JsonElement> {
    private final com.google.gson.JsonArray a;

    public JsonArray(com.google.gson.JsonArray a) {
        this.a = a;
    }

    public int size() {
        return a.size();
    }

    public Iterator<JsonElement> iterator() {
        Iterator<com.google.gson.JsonElement> it = a.iterator();
        List<JsonElement> list = new ArrayList<JsonElement>();
        while (it.hasNext()) {
            com.google.gson.JsonElement elm = it.next();
            if (elm instanceof com.google.gson.JsonObject) {
                list.add(new JsonObject((com.google.gson.JsonObject)elm));
            } else if (elm instanceof com.google.gson.JsonPrimitive) {
                list.add(new JsonPrimitive((com.google.gson.JsonPrimitive)elm));
            } else if (elm instanceof com.google.gson.JsonArray) {
                list.add(new JsonArray((com.google.gson.JsonArray)elm));
            } else if (elm instanceof com.google.gson.JsonNull) {
                list.add(JsonNull.INSTANCE);
            } else {
                throw new RuntimeException("Type not supported: " + elm.getClass().getName());
            }
        }
        return list.iterator();
    }

    public Number getAsNumber() {
        return a.getAsNumber();
    }

    public String getAsString() {
        return a.getAsString();
    }

    public double getAsDouble() {
        return a.getAsDouble();
    }

    public BigDecimal getAsBigDecimal() {
        return a.getAsBigDecimal();
    }

    public BigInteger getAsBigInteger() {
        return a.getAsBigInteger();
    }

    public float getAsFloat() {
        return a.getAsFloat();
    }

    public long getAsLong() {
        return a.getAsLong();
    }

    public int getAsInt() {
        return a.getAsInt();
    }

    public byte getAsByte() {
        return a.getAsByte();
    }

    public char getAsCharacter() {
        return a.getAsCharacter();
    }

    public short getAsShort() {
        return a.getAsShort();
    }

    public boolean getAsBoolean() {
        return a.getAsBoolean();
    }

    @Override
    public boolean equals(Object o) {
        return a.equals(o);
    }

    @Override
    public int hashCode() {
        return a.hashCode();
    }

    public String toString() {
        return a.toString();
    }
}
