package com.phoenixai.transittracker.model;

public class Passenger {
    
    private Location location;
    private String passengerId;

    public Passenger(String passengerId, Location location){
        this.location = location;
        this.passengerId = passengerId;
    }

    // Accessors and Mutators

    public String getPassengerId(){
        return passengerId;
    }

    public Location getLocation() {
        return location;
    }

    public void setPassengerId(String passengerId){
        this.passengerId = passengerId;
    }

    public void setLocation(Location location){
        this.location = location;
    }


    @Override
    public String toString() {
        return "Passenger{" + 
               "passengerId=' " + passengerId + '\'' +
               ", location=" + location + '}';
    }
}
