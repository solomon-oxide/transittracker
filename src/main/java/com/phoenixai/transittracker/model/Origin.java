package com.phoenixai.transittracker.model;

/**
 * Represents the origin point of a route
 * Now extends Stop with StopType.ORIGIN
 */
public class Origin extends Stop {

    // Default constructor
    public Origin() {
        super();
        setStopType(StopType.ORIGIN);
    }

    // Constructor with basic information
    public Origin(String originId, String originName, Location location) {
        super(originId, originName, location, StopType.ORIGIN);
    }

    // Full constructor
    public Origin(String originId, String originName, Location location, String description) {
        this(originId, originName, location);
    }

    // Convenience getters that delegate to parent class
    public String getOriginId() {
        return getStopId();
    }

    public String getOriginName() {
        return getStopName();
    }

    // Convenience setters that delegate to parent class
    public void setOriginId(String originId) {
        setStopId(originId);
    }

    public void setOriginName(String originName) {
        setStopName(originName);
    }

    @Override
    public String toString() {
        return "Origin{" +
                "originId='" + getStopId() + '\'' +
                ", originName='" + getStopName() + '\'' +
                ", location=" + getLocation() +
                ", stopType=" + getStopType() +
                '}';
    }
}