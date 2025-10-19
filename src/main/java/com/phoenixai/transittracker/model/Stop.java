package com.phoenixai.transittracker.model;

/**
 * Represents a bus stop with location and route information
 */
public class Stop {
    private String stopId;
    private String stopName;
    private Location location;


    // Constructors

    public Stop() {
    }


    public Stop(String stopId, String stopName, Location location) {
        this.stopId = stopId;
        this.stopName = stopName;
        this.location = location;
    }


    // Accessors
    public String getStopId() {
        return stopId;
    }

    public String getStopName() {
        return stopName;
    }

    public Location getLocation() {
        return location;
    }


    // Mutators
    public void setStopId(String stopId) {
        this.stopId = stopId;
    }

    public void setStopName(String stopName) {
        this.stopName = stopName;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "Stop{" +
                "stopId='" + stopId + '\'' +
                ", stopName='" + stopName + '\'' +
                ", location=" + location +
                '}';
    }
}