package com.example.mobilebraicar3;

import java.io.IOException;
import com.fasterxml.jackson.annotation.*;
@JsonIgnoreProperties(ignoreUnknown = true)
public enum Mode {
    BUS, WALK;

    @JsonValue
    public String toValue() {
        switch (this) {
            case BUS: return "BUS";
            case WALK: return "WALK";
        }
        return null;
    }

    @JsonCreator
    public static Mode forValue(String value) throws IOException {
        if (value.equals("BUS")) return BUS;
        if (value.equals("WALK")) return WALK;
        throw new IOException("Cannot deserialize Mode");
    }
}
