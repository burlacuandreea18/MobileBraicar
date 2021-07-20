package com.example.mobilebraicar3;

import com.fasterxml.jackson.annotation.*;
@JsonIgnoreProperties(ignoreUnknown = true)
public class Welcome {
    private Plan plan;

    @JsonProperty("plan")
    public Plan getPlan() { return plan; }
    @JsonProperty("plan")
    public void setPlan(Plan value) { this.plan = value; }
}
