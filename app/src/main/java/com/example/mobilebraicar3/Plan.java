package com.example.mobilebraicar3;

import com.fasterxml.jackson.annotation.*;
@JsonIgnoreProperties(ignoreUnknown = true)
public class Plan {
    private Long date;
    private Itinerary[] itineraries;

    @JsonProperty("date")
    public Long getDate() { return date; }
    @JsonProperty("date")
    public void setDate(Long value) { this.date = value; }

    @JsonProperty("itineraries")
    public Itinerary[] getItineraries() { return itineraries; }
    @JsonProperty("itineraries")
    public void setItineraries(Itinerary[] value) { this.itineraries = value; }
}
