package com.phoenixai.transittracker.model;

import java.util.ArrayList;

/**
 * Represents a route map with stops and route information
 */
public class RouteMap {
    private String mapId;
    private String mapName;
    private ArrayList<Route> routes;

    // Default constructor
    public RouteMap() {
        this.routes = new ArrayList<>();
    }

    // Constructor with basic information
    public RouteMap(String mapId, String mapName) {
        this.mapId = mapId;
        this.mapName = mapName;
    }

    // Getters
    public String getMapId() {
        return mapId;
    }

    public String getMapName() {
        return mapName;
    }

    public ArrayList<Route> getRoutes() {
        return routes;
    }




    // Setters
    public void setMapId(String mapId) {
        this.mapId = mapId;
    }

    public void setMapName(String mapName) {
        this.mapName = mapName;
    }

    public void setRoutes(ArrayList<Route> routes) {
        this.routes = routes;
    }


    // Utility methods
    public void addRoute(Route route) {
        if (!routes.contains(route)) {
            routes.add(route);
        }
    }

    public Route findRouteByNumber(String routeNumber) {
        return routes.stream()
                .filter(route -> route.getRouteNo().equals(routeNumber))
                .findFirst()
                .orElse(null);
    }

    @Override
    public String toString() {
        return "RouteMap{" +
                "mapId='" + mapId + '\'' +
                ", mapName='" + mapName + '\'' +
                ", routes=" + routes.size() +
                '}';
    }
}
