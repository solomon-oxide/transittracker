package com.phoenixai.transittracker.model;

public class Passenger extends User {

    public Passenger() {
        super();
    }

    public Passenger(String passengerId, Location location){
        super(passengerId, location);
    }

    // Accessors and Mutators

    public String getPassengerId(){
        return getId();
    }

    public void setPassengerId(String passengerId){
        setId(passengerId);
    }

    @Override
    public String toString() {
        return "Passenger{" + 
               "passengerId=' " + getId() + '\'' +
               ", location=" + getLocation() + '}';
    }
}
