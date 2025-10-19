package com.phoenixai.transittracker.model;

/**
 * Represents the destination point of a route
 * Now extends Stop with StopType.DESTINATION
 */
public class Destination extends Stop {

    // Default constructor
    public Destination() {
        super();
        setStopType(StopType.DESTINATION);
    }

    // Constructor with basic information
    public Destination(String destinationId, String destinationName, Location location) {
        super(destinationId, destinationName, location, StopType.DESTINATION);
    }

    // Full constructor
    public Destination(String destinationId, String destinationName, Location location, String description) {
        this(destinationId, destinationName, location);
    }

    // Convenience getters that delegate to parent class
    public String getDestinationId() {
        return getStopId();
    }

    public String getDestinationName() {
        return getStopName();
    }

    // Convenience setters that delegate to parent class
    public void setDestinationId(String destinationId) {
        setStopId(destinationId);
    }

    public void setDestinationName(String destinationName) {
        setStopName(destinationName);
    }

    @Override
    public String toString() {
        return "Destination{" +
                "destinationId='" + getStopId() + '\'' +
                ", destinationName='" + getStopName() + '\'' +
                ", location=" + getLocation() +
                ", stopType=" + getStopType() +
                '}';
    }
}