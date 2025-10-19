package com.phoenixai.transittracker.model;

import java.util.ArrayList;

/**
 * Represents a bus route with origin, destination, and stops
 */
public class Route {
    private String routeNo;
    private Origin origin;
    private Destination destination;
    private ArrayList<Stop> stops;
    private double distance; // in kilometers
    private int estimatedDuration; // in minutes

    // Default constructor
    public Route() {
        this.stops = new ArrayList<Stop>();
    }

    // Constructor with basic information
    public Route(String routeNo, Origin origin, Destination destination) {
        this.routeNo = routeNo;
        this.origin = origin;
        this.destination = destination;
    }

    // Full constructor
    public Route(String routeNo, Origin origin, Destination destination, 
                 String description, double distance, int estimatedDuration) {
        this(routeNo, origin, destination);
        this.distance = distance;
        this.estimatedDuration = estimatedDuration;
    }

    // Accessors
    public String getRouteNo() {
        return routeNo;
    }

    public Origin getOrigin() {
        return origin;
    }

    public Destination getDestination() {
        return destination;
    }

    public ArrayList<Stop> getStops() {
        return stops;
    }

    public double getDistance() {
        return distance;
    }

    public int getEstimatedDuration() {
        return estimatedDuration;
    }



    // Mutators
    public void setRouteNo(String routeNo) {
        this.routeNo = routeNo;
    }

    public void setOrigin(Origin origin) {
        this.origin = origin;
    }

    public void setDestination(Destination destination) {
        this.destination = destination;
    }

    public void setStops(ArrayList<Stop> stops) {
        this.stops = stops;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public void setEstimatedDuration(int estimatedDuration) {
        this.estimatedDuration = estimatedDuration;
    }

    // Utility methods
    public void addStop(Stop stop) {
        if (!stops.contains(stop)) {
            stops.add(stop);
        }
    }

    public void removeStop(Stop stop) {
        stops.remove(stop);
    }

    public void addStopAtPosition(Stop stop, int position) {
        if (position >= 0 && position <= stops.size()) {
            stops.add(position, stop);
        }
    }

    public Stop getStopAt(int index) {
        if (index >= 0 && index < stops.size()) {
            return stops.get(index);
        }
        return null;
    }

    public int getStopCount() {
        return stops.size();
    }

    public boolean containsStop(Stop stop) {
        return stops.contains(stop);
    }

    /**
     * Get all stops including origin and destination in order
     * @return ArrayList of all stops in route order
     */
    public ArrayList<Stop> getAllStopsInOrder() {
        ArrayList<Stop> allStops = new ArrayList<>();
        if (origin != null) {
            allStops.add(origin);
        }
        allStops.addAll(stops);
        if (destination != null) {
            allStops.add(destination);
        }
        return allStops;
    }

    /**
     * Get total number of stops including origin and destination
     * @return total stop count
     */
    public int getTotalStopCount() {
        int count = stops.size();
        if (origin != null) count++;
        if (destination != null) count++;
        return count;
    }

    /**
     * Check if a stop is the origin
     * @param stop the stop to check
     * @return true if the stop is the origin
     */
    public boolean isOrigin(Stop stop) {
        return origin != null && origin.equals(stop);
    }

    /**
     * Check if a stop is the destination
     * @param stop the stop to check
     * @return true if the stop is the destination
     */
    public boolean isDestination(Stop stop) {
        return destination != null && destination.equals(stop);
    }

    /**
     * Get the origin as a Stop
     * @return origin as Stop
     */
    public Stop getOriginAsStop() {
        return origin;
    }

    /**
     * Get the destination as a Stop
     * @return destination as Stop
     */
    public Stop getDestinationAsStop() {
        return destination;
    }

    @Override
    public String toString() {
        return "Route{" +
                "routeNo='" + routeNo + '\'' +
                ", origin=" + origin +
                ", destination=" + destination +
                ", stops=" + stops.size() +
                ", totalStops=" + getTotalStopCount() +
                '}';
    }
}