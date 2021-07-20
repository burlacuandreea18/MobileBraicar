package com.example.mobilebraicar3;

import com.fasterxml.jackson.annotation.*;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;
@JsonIgnoreProperties(ignoreUnknown = true)
public class Leg {
    private Mode mode;
    private String route;
    private Long routeType;
    private Long startTime;
    private Long endTime;
    private Long departureDelay;
    private Long arrivalDelay;
    private Boolean realTime;
    private Boolean pathway;
    private Boolean isNonExactFrequency;
    private Boolean interlineWithPreviousLeg;
    private From from;
    private From to;
    private Boolean rentedBike;
    private Long duration;
    private Boolean transitLeg;
    private LegGeometry legGeometry;
    private Step[] steps;
    private String tripID;
    private Long routeID;

    @JsonProperty("mode")
    public Mode getMode() { return mode; }
    @JsonProperty("mode")
    public void setMode(Mode value) { this.mode = value; }

    @JsonProperty("route")
    public String getRoute() { return route; }
    @JsonProperty("route")
    public void setRoute(String value) { this.route = value; }

    @JsonProperty("routeType")
    public Long getRouteType() { return routeType; }
    @JsonProperty("routeType")
    public void setRouteType(Long value) { this.routeType = value; }

    @JsonProperty("startTime")
    public Long getStartTime() { return startTime; }
    @JsonProperty("startTime")
    public void setStartTime(Long value) { this.startTime = value; }

    @JsonProperty("endTime")
    public Long getEndTime() { return endTime; }
    @JsonProperty("endTime")
    public void setEndTime(Long value) { this.endTime = value; }

    @JsonProperty("departureDelay")
    public Long getDepartureDelay() { return departureDelay; }
    @JsonProperty("departureDelay")
    public void setDepartureDelay(Long value) { this.departureDelay = value; }

    @JsonProperty("arrivalDelay")
    public Long getArrivalDelay() { return arrivalDelay; }
    @JsonProperty("arrivalDelay")
    public void setArrivalDelay(Long value) { this.arrivalDelay = value; }

    @JsonProperty("realTime")
    public Boolean getRealTime() { return realTime; }
    @JsonProperty("realTime")
    public void setRealTime(Boolean value) { this.realTime = value; }

    @JsonProperty("pathway")
    public Boolean getPathway() { return pathway; }
    @JsonProperty("pathway")
    public void setPathway(Boolean value) { this.pathway = value; }

    @JsonProperty("isNonExactFrequency")
    public Boolean getIsNonExactFrequency() { return isNonExactFrequency; }
    @JsonProperty("isNonExactFrequency")
    public void setIsNonExactFrequency(Boolean value) { this.isNonExactFrequency = value; }

    @JsonProperty("interlineWithPreviousLeg")
    public Boolean getInterlineWithPreviousLeg() { return interlineWithPreviousLeg; }
    @JsonProperty("interlineWithPreviousLeg")
    public void setInterlineWithPreviousLeg(Boolean value) { this.interlineWithPreviousLeg = value; }

    @JsonProperty("from")
    public From getFrom() { return from; }
    @JsonProperty("from")
    public void setFrom(From value) { this.from = value; }

    @JsonProperty("to")
    public From getTo() { return to; }
    @JsonProperty("to")
    public void setTo(From value) { this.to = value; }

    @JsonProperty("rentedBike")
    public Boolean getRentedBike() { return rentedBike; }
    @JsonProperty("rentedBike")
    public void setRentedBike(Boolean value) { this.rentedBike = value; }

    @JsonProperty("duration")
    public Long getDuration() { return duration; }
    @JsonProperty("duration")
    public void setDuration(Long value) { this.duration = value; }

    @JsonProperty("transitLeg")
    public Boolean getTransitLeg() { return transitLeg; }
    @JsonProperty("transitLeg")
    public void setTransitLeg(Boolean value) { this.transitLeg = value; }

    @JsonProperty("legGeometry")
    public LegGeometry getLegGeometry() { return legGeometry; }
    @JsonProperty("legGeometry")
    public void setLegGeometry(LegGeometry value) { this.legGeometry = value; }

    @JsonProperty("steps")
    public Step[] getSteps() { return steps; }
    @JsonProperty("steps")
    public void setSteps(Step[] value) { this.steps = value; }

    @JsonProperty("tripId")
    public String getTripID() { return tripID; }
    @JsonProperty("tripId")
    public void setTripID(String value) { this.tripID = value; }

    @JsonProperty("routeId")
    public Long getRouteID() { return routeID; }
    @JsonProperty("routeId")
    public void setRouteID(Long value) { this.routeID = value; }

    public List decodePoly() {

        String encoded = legGeometry.getPoints();
        List poly = new ArrayList();
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;

        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            LatLng p = new LatLng((((double) lat / 1E5)),
                    (((double) lng / 1E5)));
            poly.add(p);
        }

        return poly;
    }
}
