package com.phoenixai.transittracker.model;

import java.time.LocalDateTime;

/**
 * Represents a GPS location with latitude, longitude, and timestamp
 */
public class Location {
    private double latitude;
    private double longitude;
    private double altitude;
    private double accuracy;
    private LocalDateTime timestamp;
    private String address;

    // Constructors
    public Location() {
        this.timestamp = LocalDateTime.now();
    }

    public Location(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Location(double latitude, double longitude, double altitude, double accuracy) {
        this(latitude, longitude);
        this.altitude = altitude;
        this.accuracy = accuracy;
    }


    // Accessors
    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getAltitude() {
        return altitude;
    }

    public double getAccuracy() {
        return accuracy;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getAddress() {
        return address;
    }


    // Mutators
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setAltitude(double altitude) {
        this.altitude = altitude;
    }

    public void setAccuracy(double accuracy) {
        this.accuracy = accuracy;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    /**
     * Calculate distance between two locations using Haversine formula
     * @param other Another location
     * @return Distance in kilometers
     */
    public double distanceTo(Location other) {
        final int R = 6371; // Earth's radius in kilometers
        
        double latDistance = Math.toRadians(other.latitude - this.latitude);
        double lonDistance = Math.toRadians(other.longitude - this.longitude);
        
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(this.latitude)) * Math.cos(Math.toRadians(other.latitude))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        
        return R * c;
    }

    /**
     * Check if this location is within a certain radius of another location
     * @param other Another location
     * @param radiusKm Radius in kilometers
     * @return true if within radius
     */
    public boolean isWithinRadius(Location other, double radiusKm) {
        return distanceTo(other) <= radiusKm;
    }

    /**
     * Get coordinates as a string for map APIs
     * @return "lat,lng" format
     */
    public String getCoordinatesString() {
        return latitude + "," + longitude;
    }

    @Override
    public String toString() {
        return String.format("Location{lat=%.6f, lng=%.6f, time=%s}", 
                           latitude, longitude, timestamp);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Location location = (Location) obj;
        return Double.compare(location.latitude, latitude) == 0 &&
               Double.compare(location.longitude, longitude) == 0;
    }

    @Override
    public int hashCode() {
        return Double.hashCode(latitude) + Double.hashCode(longitude);
    }
}
