package com.example.mobilebraicar3;

import com.fasterxml.jackson.annotation.*;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Step {
    private Double distance;
    private String relativeDirection;
    private String streetName;
    private String absoluteDirection;
    private Boolean stayOn;
    private Boolean area;
    private Boolean bogusName;
    private Coordinate coordinate;
    private Double lon;
    private Double lat;
    private Object[] elevation;

    @JsonProperty("distance")
    public Double getDistance() { return distance; }
    @JsonProperty("distance")
    public void setDistance(Double value) { this.distance = value; }

    @JsonProperty("relativeDirection")
    public String getRelativeDirection() { return relativeDirection; }
    @JsonProperty("relativeDirection")
    public void setRelativeDirection(String value) { this.relativeDirection = value; }

    @JsonProperty("streetName")
    public String getStreetName() { return streetName; }
    @JsonProperty("streetName")
    public void setStreetName(String value) { this.streetName = value; }

    @JsonProperty("absoluteDirection")
    public String getAbsoluteDirection() { return absoluteDirection; }
    @JsonProperty("absoluteDirection")
    public void setAbsoluteDirection(String value) { this.absoluteDirection = value; }

    @JsonProperty("stayOn")
    public Boolean getStayOn() { return stayOn; }
    @JsonProperty("stayOn")
    public void setStayOn(Boolean value) { this.stayOn = value; }

    @JsonProperty("area")
    public Boolean getArea() { return area; }
    @JsonProperty("area")
    public void setArea(Boolean value) { this.area = value; }

    @JsonProperty("bogusName")
    public Boolean getBogusName() { return bogusName; }
    @JsonProperty("bogusName")
    public void setBogusName(Boolean value) { this.bogusName = value; }

    @JsonProperty("coordinate")
    public Coordinate getCoordinate() { return coordinate; }
    @JsonProperty("coordinate")
    public void setCoordinate(Coordinate value) { this.coordinate = value; }

    @JsonProperty("lon")
    public Double getLon() { return lon; }
    @JsonProperty("lon")
    public void setLon(Double value) { this.lon = value; }

    @JsonProperty("lat")
    public Double getLat() { return lat; }
    @JsonProperty("lat")
    public void setLat(Double value) { this.lat = value; }

    @JsonProperty("elevation")
    public Object[] getElevation() { return elevation; }
    @JsonProperty("elevation")
    public void setElevation(Object[] value) { this.elevation = value; }
}
