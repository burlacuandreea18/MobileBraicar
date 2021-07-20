package com.example.mobilebraicar3;

import com.fasterxml.jackson.annotation.*;
@JsonIgnoreProperties(ignoreUnknown = true)
public class From {
    private String name;
    private Long departure;
    private Double lat;
    private Double lon;
    private VertexType vertexType;
    private Long arrival;
    private Long stopID;
    private Long stopIndex;

    @JsonProperty("name")
    public String getName() { return name; }
    @JsonProperty("name")
    public void setName(String value) { this.name = value; }

    @JsonProperty("departure")
    public Long getDeparture() { return departure; }
    @JsonProperty("departure")
    public void setDeparture(Long value) { this.departure = value; }

    @JsonProperty("lat")
    public Double getLat() { return lat; }
    @JsonProperty("lat")
    public void setLat(Double value) { this.lat = value; }

    @JsonProperty("lon")
    public Double getLon() { return lon; }
    @JsonProperty("lon")
    public void setLon(Double value) { this.lon = value; }

    @JsonProperty("vertexType")
    public VertexType getVertexType() { return vertexType; }
    @JsonProperty("vertexType")
    public void setVertexType(VertexType value) { this.vertexType = value; }

    @JsonProperty("arrival")
    public Long getArrival() { return arrival; }
    @JsonProperty("arrival")
    public void setArrival(Long value) { this.arrival = value; }

    @JsonProperty("stopId")
    public Long getStopID() { return stopID; }
    @JsonProperty("stopId")
    public void setStopID(Long value) { this.stopID = value; }

    @JsonProperty("stopIndex")
    public Long getStopIndex() { return stopIndex; }
    @JsonProperty("stopIndex")
    public void setStopIndex(Long value) { this.stopIndex = value; }
}
