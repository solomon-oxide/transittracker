package com.phoenixai.transittracker.model;


public class Destination extends Stop {

    // Default constructor
    public Destination() {
        super();
        setStopType(StopType.DESTINATION);
    }

    // Constructors
    public Destination(String destinationId, String destinationName, Location location) {
        super(destinationId, destinationName, location, StopType.DESTINATION);
    }

    public Destination(String destinationId, String destinationName, Location location, String description) {
        this(destinationId, destinationName, location);
    }


    
    // Accessors
    public String getDestinationId() {
        return getStopId();
    }

    public String getDestinationName() {
        return getStopName();
    }



    // Mutators
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