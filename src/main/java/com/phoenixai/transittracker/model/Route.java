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

    @Override
    public String toString() {
        return "Route{" +
                "routeNo='" + routeNo + '\'' +
                ", origin=" + origin +
                ", destination=" + destination +
                ", stops=" + stops.size() +
                '}';
    }
}