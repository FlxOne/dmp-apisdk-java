package response;

import java.math.BigDecimal;
import java.math.BigInteger;

public class JsonPrimitive {
    private final com.google.gson.JsonPrimitive p;

    public JsonPrimitive(com.google.gson.JsonPrimitive p) {
        this.p = p;
    }

    public boolean isBoolean() {
        return p.isBoolean();
    }

    public boolean getAsBoolean() {
        return p.getAsBoolean();
    }

    public boolean isNumber() {
        return p.isNumber();
    }

    public Number getAsNumber() {
        return p.getAsNumber();
    }

    public boolean isString() {
        return p.isString();
    }

    public String getAsString() {
        return p.getAsString();
    }

    public double getAsDouble() {
        return p.getAsDouble();
    }

    public BigDecimal getAsBigDecimal() {
        return p.getAsBigDecimal();
    }

    public BigInteger getAsBigInteger() {
        return p.getAsBigInteger();
    }

    public float getAsFloat() {
        return p.getAsFloat();
    }

    public long getAsLong() {
        return p.getAsLong();
    }

    public short getAsShort() {
        return p.getAsShort();
    }

    public int getAsInt() {
        return p.getAsInt();
    }

    public byte getAsByte() {
        return p.getAsByte();
    }

    public char getAsCharacter() {
        return p.getAsCharacter();
    }

    @Override
    public int hashCode() {
        return p.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return p.equals(obj);
    }
}
