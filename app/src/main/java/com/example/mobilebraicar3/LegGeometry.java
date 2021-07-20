package com.example.mobilebraicar3;

import com.fasterxml.jackson.annotation.*;
@JsonIgnoreProperties(ignoreUnknown = true)
public class LegGeometry {
    private String points;
    private Long length;

    @JsonProperty("points")
    public String getPoints() { return points; }
    @JsonProperty("points")
    public void setPoints(String value) { this.points = value; }

    @JsonProperty("length")
    public Long getLength() { return length; }
    @JsonProperty("length")
    public void setLength(Long value) { this.length = value; }
}
