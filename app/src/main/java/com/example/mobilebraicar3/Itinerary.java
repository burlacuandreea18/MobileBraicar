package com.example.mobilebraicar3;

import com.fasterxml.jackson.annotation.*;
@JsonIgnoreProperties(ignoreUnknown = true)
public class Itinerary {
    private Long duration;
    private Long startTime;
    private Long endTime;
    private Long walkTime;
    private Long transitTime;
    private Long waitingTime;
    private Double walkDistance;
    private Boolean walkLimitExceeded;
    private Long elevationLost;
    private Long elevationGained;
    private Long transfers;
    private Leg[] legs;
    private Boolean tooSloped;

    @JsonProperty("duration")
    public Long getDuration() { return duration; }
    @JsonProperty("duration")
    public void setDuration(Long value) { this.duration = value; }

    @JsonProperty("startTime")
    public Long getStartTime() { return startTime; }
    @JsonProperty("startTime")
    public void setStartTime(Long value) { this.startTime = value; }

    @JsonProperty("endTime")
    public Long getEndTime() { return endTime; }
    @JsonProperty("endTime")
    public void setEndTime(Long value) { this.endTime = value; }

    @JsonProperty("walkTime")
    public Long getWalkTime() { return walkTime; }
    @JsonProperty("walkTime")
    public void setWalkTime(Long value) { this.walkTime = value; }

    @JsonProperty("transitTime")
    public Long getTransitTime() { return transitTime; }
    @JsonProperty("transitTime")
    public void setTransitTime(Long value) { this.transitTime = value; }

    @JsonProperty("waitingTime")
    public Long getWaitingTime() { return waitingTime; }
    @JsonProperty("waitingTime")
    public void setWaitingTime(Long value) { this.waitingTime = value; }

    @JsonProperty("walkDistance")
    public Double getWalkDistance() { return walkDistance; }
    @JsonProperty("walkDistance")
    public void setWalkDistance(Double value) { this.walkDistance = value; }

    @JsonProperty("walkLimitExceeded")
    public Boolean getWalkLimitExceeded() { return walkLimitExceeded; }
    @JsonProperty("walkLimitExceeded")
    public void setWalkLimitExceeded(Boolean value) { this.walkLimitExceeded = value; }

    @JsonProperty("elevationLost")
    public Long getElevationLost() { return elevationLost; }
    @JsonProperty("elevationLost")
    public void setElevationLost(Long value) { this.elevationLost = value; }

    @JsonProperty("elevationGained")
    public Long getElevationGained() { return elevationGained; }
    @JsonProperty("elevationGained")
    public void setElevationGained(Long value) { this.elevationGained = value; }

    @JsonProperty("transfers")
    public Long getTransfers() { return transfers; }
    @JsonProperty("transfers")
    public void setTransfers(Long value) { this.transfers = value; }

    @JsonProperty("legs")
    public Leg[] getLegs() { return legs; }
    @JsonProperty("legs")
    public void setLegs(Leg[] value) { this.legs = value; }

    @JsonProperty("tooSloped")
    public Boolean getTooSloped() { return tooSloped; }
    @JsonProperty("tooSloped")
    public void setTooSloped(Boolean value) { this.tooSloped = value; }
}
