
package com.phoenixai.transittracker.model;


public class Bus {
    private String busId;
    private Route route;
    private Company company;
    private Location currentlocation;

    public Bus() {
    
    }


    public Bus(String busId, Route route, Company company, Location currentlocation) {
        this.busId = busId;
        this.route = route;
        this.company = company;
        this.currentlocation = currentlocation;
    }


    /*
     * Accessors
     */
    public String getBusId() {
        return busId;
    }

    public Route getRoute() {
        return route;
    }

    public Company getCompany(){
        return company;
    }

    public Location getCurrentLocation() {
        return currentlocation;
    } 

    /*
     * Mutators
     */
    public void setBusId(String busId) {
        this.busId = busId;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public void setCurrentLocation(Location currentlocation) {
        this.currentlocation = currentlocation;
    }


    @Override
    public String toString() {
        return "Bus{" +
                "busId='" + busId + '\'' +
                ", route=" + route + '\'' +
                ", company=" + company + '\'' +
                ", currentlocation=" + currentlocation +
                '}';
    }
}