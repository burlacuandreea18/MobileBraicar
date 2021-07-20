package com.example.mobilebraicar3;

import com.fasterxml.jackson.annotation.*;
@JsonIgnoreProperties(ignoreUnknown = true)
public class Coordinate {
    private Double lat;
    private Double lon;

    @JsonProperty("lat")
    public Double getLat() { return lat; }
    @JsonProperty("lat")
    public void setLat(Double value) { this.lat = value; }

    @JsonProperty("lon")
    public Double getLon() { return lon; }
    @JsonProperty("lon")
    public void setLon(Double value) { this.lon = value; }
}
