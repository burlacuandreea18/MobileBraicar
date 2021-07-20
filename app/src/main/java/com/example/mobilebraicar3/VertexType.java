package com.example.mobilebraicar3;

import java.io.IOException;
import com.fasterxml.jackson.annotation.*;

public enum VertexType {
    NORMAL, TRANSIT;

    @JsonValue
    public String toValue() {
        switch (this) {
            case NORMAL: return "NORMAL";
            case TRANSIT: return "TRANSIT";
        }
        return null;
    }

    @JsonCreator
    public static VertexType forValue(String value) throws IOException {
        if (value.equals("NORMAL")) return NORMAL;
        if (value.equals("TRANSIT")) return TRANSIT;
        throw new IOException("Cannot deserialize VertexType");
    }
}
