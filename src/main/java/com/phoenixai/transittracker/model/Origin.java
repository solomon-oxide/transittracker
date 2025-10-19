package com.phoenixai.transittracker.model;

/**
 * Represents the origin point of a route
 */
public class Origin {
    private String originId;
    private String originName;
    private Location location;

    // Default constructor
    public Origin() {
    }

    // Constructor with basic information
    public Origin(String originId, String originName, Location location) {
        this.originId = originId;
        this.originName = originName;
        this.location = location;
    }

    // Full constructor
    public Origin(String originId, String originName, Location location, String description) {
        this(originId, originName, location);
    }

    // Accessors
    public String getOriginId() {
        return originId;
    }

    public String getOriginName() {
        return originName;
    }

    public Location getLocation() {
        return location;
    }



    // Mutators
    public void setOriginId(String originId) {
        this.originId = originId;
    }

    public void setOriginName(String originName) {
        this.originName = originName;
    }

    public void setLocation(Location location) {
        this.location = location;
    }


    @Override
    public String toString() {
        return "Origin{" +
                "originId='" + originId + '\'' +
                ", originName='" + originName + '\'' +
                ", location=" + location +
                '}';
    }
}
