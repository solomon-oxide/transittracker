
package com.phoenixai.transittracker.model;

public class Bus extends User {
    private Route route;
    private Company company;

    public Bus() {
        super();
    }

    public Bus(String busId, Route route, Company company, Location currentlocation) {
        super(busId, currentlocation);
        this.route = route;
        this.company = company;
    }

    /*
     * Accessors
     */
    public String getBusId() {
        return getId();
    }

    public Route getRoute() {
        return route;
    }

    public Company getCompany(){
        return company;
    }

    public Location getCurrentLocation() {
        return getLocation();
    } 

    /*
     * Mutators
     */
    public void setBusId(String busId) {
        setId(busId);
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public void setCurrentLocation(Location currentlocation) {
        setLocation(currentlocation);
    }

    @Override
    public String toString() {
        return "Bus{" +
                "busId='" + getId() + '\'' +
                ", route=" + route + '\'' +
                ", company=" + company + '\'' +
                ", currentlocation=" + getLocation() +
                '}';
    }
}