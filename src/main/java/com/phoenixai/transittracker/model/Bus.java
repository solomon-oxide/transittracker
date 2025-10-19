
package com.phoenixai.transittracker.model;

public class Bus extends User {
    private Route activeroute;
    private Company company;

    public Bus() {
        super();
    }

    public Bus(String busId, Route activeroute, Company company, Location location) {
        super(busId, location);
        this.activeroute = activeroute;
        this.company = company;
    }

    /*
     * Accessors
     */
    public String getBusId() {
        return getId();
    }

    public Route getActiveRoute() {
        return activeroute;
    }

    public Company getCompany(){
        return company;
    }

    public Location getLocation() {
        return getLocation();
    } 

    /*
     * Mutators
     */
    public void setBusId(String busId) {
        setId(busId);
    }

    public void setActiveRoute(Route activeroute) {
        this.activeroute = activeroute;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public void setLocation(Location location) {
        setLocation(location);
    }

    @Override
    public String toString() {
        return "Bus{" +
                "busId='" + getId() + '\'' +
                ", activeroute=" + activeroute + '\'' +
                ", company=" + company + '\'' +
                ", location=" + getLocation() +
                '}';
    }
}