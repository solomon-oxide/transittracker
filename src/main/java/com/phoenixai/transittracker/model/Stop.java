package com.phoenixai.transittracker.model;

/**
 * Represents a bus stop with location and route information
 */
public class Stop {
    private String stopId;
    private String stopName;
    private Location location;
    private StopType stopType;


    // Constructors

    public Stop() {
        this.stopType = StopType.REGULAR;
    }

    public Stop(String stopId, String stopName, Location location) {
        this.stopId = stopId;
        this.stopName = stopName;
        this.location = location;
        this.stopType = StopType.REGULAR;
    }

    public Stop(String stopId, String stopName, Location location, StopType stopType) {
        this.stopId = stopId;
        this.stopName = stopName;
        this.location = location;
        this.stopType = stopType;
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

    public StopType getStopType() {
        return stopType;
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

    public void setStopType(StopType stopType) {
        this.stopType = stopType;
    }

    @Override
    public String toString() {
        return "Stop{" +
                "stopId='" + stopId + '\'' +
                ", stopName='" + stopName + '\'' +
                ", location=" + location +
                ", stopType=" + stopType +
                '}';
    }
}