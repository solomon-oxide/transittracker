package com.phoenixai.transittracker.model;

/**
 * Represents the destination point of a route
 */
public class Destination {
    private String destinationId;
    private String destinationName;
    private Location location;

    // Default constructor
    public Destination() {
    }

    // Constructor with basic information
    public Destination(String destinationId, String destinationName, Location location) {
        this.destinationId = destinationId;
        this.destinationName = destinationName;
        this.location = location;
    }

    // Full constructor
    public Destination(String destinationId, String destinationName, Location location, String description) {
        this(destinationId, destinationName, location);
    }

    // Getters
    public String getDestinationId() {
        return destinationId;
    }

    public String getDestinationName() {
        return destinationName;
    }

    public Location getLocation() {
        return location;
    }



    // Setters
    public void setDestinationId(String destinationId) {
        this.destinationId = destinationId;
    }

    public void setDestinationName(String destinationName) {
        this.destinationName = destinationName;
    }

    public void setLocation(Location location) {
        this.location = location;
    }


    
    @Override
    public String toString() {
        return "Destination{" +
                "destinationId='" + destinationId + '\'' +
                ", destinationName='" + destinationName + '\'' +
                ", location=" + location +
                '}';
    }
}
