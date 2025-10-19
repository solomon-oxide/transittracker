package com.phoenixai.transittracker.controller;

import com.phoenixai.transittracker.model.Route;
import com.phoenixai.transittracker.model.Stop;
import com.phoenixai.transittracker.model.Origin;
import com.phoenixai.transittracker.model.Destination;

import com.phoenixai.transittracker.services.GPSService;
import com.phoenixai.transittracker.model.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

/**
 * REST Controller for handling map and GPS related operations
 */
@RestController
@RequestMapping("/api/map")
public class MapController {

    @Autowired
    private GPSService gpsService;

    @GetMapping("/routes")
    public ResponseEntity<Map<String, Object>> getRoutes(){
        List<Route> routes = getSampleRoutes();

        Map<String, Object> response = new HashMap<>();
        response.put("routes", routes);
        response.put("count", routes.size());
        response.put("timestamp", System.currentTimeMillis());
        
        return ResponseEntity.ok(response);
    
    }

    /**
 * Get all stops for a specific route
 * @param routeNo The route number
 * @return JSON response with route stops
 */
    @GetMapping("/routes/{routeNo}/stops")
    public ResponseEntity<Map<String, Object>> getRouteStops(@PathVariable String routeNo) {
        Route route = findRouteByNumber(routeNo);
    
        if (route == null) {
            return ResponseEntity.notFound().build();
        }
    
        Map<String, Object> response = new HashMap<>();
        response.put("routeNo", routeNo);
        response.put("stops", route.getStops());
        response.put("count", route.getStopCount());
    
        return ResponseEntity.ok(response);
    }


    /**
     * Get all current bus locations
     * @return JSON response with all bus locations
     */
    @GetMapping("/buses")
    public ResponseEntity<Map<String, Object>> getAllBusLocations() {
        Map<String, Location> busLocations = gpsService.getAllBusLocations();
        
        Map<String, Object> response = new HashMap<>();
        response.put("buses", busLocations);
        response.put("count", busLocations.size());
        response.put("timestamp", System.currentTimeMillis());
        
        return ResponseEntity.ok(response);
    }

    /**
     * Get location of a specific bus
     * @param busId The bus identifier
     * @return JSON response with bus location
     */
    @GetMapping("/buses/{busId}")
    public ResponseEntity<Map<String, Object>> getBusLocation(@PathVariable String busId) {
        Location location = gpsService.getBusLocation(busId);
        
        if (location == null) {
            return ResponseEntity.notFound().build();
        }
        
        Map<String, Object> response = new HashMap<>();
        response.put("busId", busId);
        response.put("location", location);
        response.put("coordinates", location.getCoordinatesString());
        
        return ResponseEntity.ok(response);
    }

    /**
     * Get buses within a certain radius of a location
     * @param lat Latitude of center point
     * @param lng Longitude of center point
     * @param radius Radius in kilometers (default: 5km)
     * @return JSON response with nearby buses
     */
    @GetMapping("/buses/nearby")
    public ResponseEntity<Map<String, Object>> getNearbyBuses(
            @RequestParam double lat,
            @RequestParam double lng,
            @RequestParam(defaultValue = "5.0") double radius) {
        
        Location centerLocation = new Location(lat, lng);
        Map<String, Location> nearbyBuses = gpsService.getBusesWithinRadius(centerLocation, radius);
        
        Map<String, Object> response = new HashMap<>();
        response.put("center", centerLocation);
        response.put("radius", radius);
        response.put("nearbyBuses", nearbyBuses);
        response.put("count", nearbyBuses.size());
        
        return ResponseEntity.ok(response);
    }

    /**
     * Get location history for a specific bus
     * @param busId The bus identifier
     * @return JSON response with location history
     */
    @GetMapping("/buses/{busId}/history")
    public ResponseEntity<Map<String, Object>> getBusLocationHistory(@PathVariable String busId) {
        List<Location> history = gpsService.getBusLocationHistory(busId);
        
        Map<String, Object> response = new HashMap<>();
        response.put("busId", busId);
        response.put("history", history);
        response.put("count", history.size());
        
        return ResponseEntity.ok(response);
    }

    /**
     * Update bus location (for GPS devices or simulation)
     * @param busId The bus identifier
     * @param location The new location
     * @return JSON response confirming update
     */
    @PostMapping("/buses/{busId}/location")
    public ResponseEntity<Map<String, Object>> updateBusLocation(
            @PathVariable String busId,
            @RequestBody Location location) {
        
        gpsService.updateBusLocation(busId, location);
        
        Map<String, Object> response = new HashMap<>();
        response.put("busId", busId);
        response.put("location", location);
        response.put("status", "updated");
        response.put("timestamp", System.currentTimeMillis());
        
        return ResponseEntity.ok(response);
    }

    /**
     * Initialize a new bus with starting location
     * @param busId The bus identifier
     * @param location The starting location
     * @return JSON response confirming initialization
     */
    @PostMapping("/buses/{busId}/initialize")
    public ResponseEntity<Map<String, Object>> initializeBus(
            @PathVariable String busId,
            @RequestBody Location location) {
        
        gpsService.initializeBus(busId, location);
        
        Map<String, Object> response = new HashMap<>();
        response.put("busId", busId);
        response.put("location", location);
        response.put("status", "initialized");
        response.put("timestamp", System.currentTimeMillis());
        
        return ResponseEntity.ok(response);
    }

    /**
     * Remove a bus from tracking
     * @param busId The bus identifier
     * @return JSON response confirming removal
     */
    @DeleteMapping("/buses/{busId}")
    public ResponseEntity<Map<String, Object>> removeBus(@PathVariable String busId) {
        gpsService.removeBus(busId);
        
        Map<String, Object> response = new HashMap<>();
        response.put("busId", busId);
        response.put("status", "removed");
        response.put("timestamp", System.currentTimeMillis());
        
        return ResponseEntity.ok(response);
    }

    /**
     * Get map configuration and settings
     * @return JSON response with map configuration
     */
    @GetMapping("/config")
    public ResponseEntity<Map<String, Object>> getMapConfig() {
        Map<String, Object> config = new HashMap<>();
        config.put("defaultCenter", new Location(40.7128, -74.0060)); // New York City
        config.put("defaultZoom", 12);
        config.put("updateInterval", 5000); // 5 seconds
        config.put("maxHistoryPoints", 100);
        
        return ResponseEntity.ok(config);
    }

    // Helper methods for route management
    private List<Route> getSampleRoutes() {
        List<Route> routes = new ArrayList<>();
        
        // Create sample route data (similar to AppConfig)
        Location halfWayTreeLocation = new Location(18.0172, -76.7840, 0, 5.0);
        Origin halfWayTreeOrigin = new Origin("HWT-001", "Half Way Tree", halfWayTreeLocation);
        
        Location downtownLocation = new Location(17.9714, -76.7932, 0, 5.0);
        Destination downtownDestination = new Destination("DT-001", "Downtown Kingston", downtownLocation);
        
        Route route001 = new Route("R001", halfWayTreeOrigin, downtownDestination);
        route001.setDistance(8.5);
        route001.setEstimatedDuration(25);
        
        // Add sample stops
        ArrayList<Stop> routeStops = new ArrayList<>();
        routeStops.add(new Stop("S001", "Hope Road & Trafalgar Road", new Location(18.0156, -76.7856, 0, 5.0)));
        routeStops.add(new Stop("S002", "New Kingston", new Location(18.0089, -76.7890, 0, 5.0)));
        routeStops.add(new Stop("S003", "Cross Roads", new Location(17.9956, -76.7923, 0, 5.0)));
        routeStops.add(new Stop("S004", "Parade", new Location(17.9789, -76.7945, 0, 5.0)));
        
        route001.setStops(routeStops);
        routes.add(route001);
        
        return routes;
    }

    private Route findRouteByNumber(String routeNo) {
        List<Route> routes = getSampleRoutes();
        return routes.stream()
                .filter(route -> route.getRouteNo().equals(routeNo))
                .findFirst()
                .orElse(null);
    }

    /**
     * Get route information for a specific bus
     * @param busId The bus identifier
     * @return JSON response with route details
     */
    @GetMapping("/buses/{busId}/route")
    public ResponseEntity<Map<String, Object>> getBusRoute(@PathVariable String busId) {
        // For now, we'll map buses to routes based on the sample data
        // In a real application, this would come from a database
        Map<String, String> busRouteMapping = getBusRouteMapping();
        String routeNo = busRouteMapping.get(busId);
        
        if (routeNo == null) {
            return ResponseEntity.notFound().build();
        }
        
        Route route = findRouteByNumber(routeNo);
        if (route == null) {
            return ResponseEntity.notFound().build();
        }
        
        Map<String, Object> response = new HashMap<>();
        response.put("busId", busId);
        response.put("route", route);
        response.put("allStops", route.getAllStopsInOrder());
        response.put("origin", route.getOrigin());
        response.put("destination", route.getDestination());
        response.put("regularStops", route.getStops());
        response.put("totalStops", route.getTotalStopCount());
        
        return ResponseEntity.ok(response);
    }

    /**
     * Get all routes with their associated buses
     * @return JSON response with routes and buses
     */
    @GetMapping("/routes/buses")
    public ResponseEntity<Map<String, Object>> getRoutesWithBuses() {
        List<Route> routes = getSampleRoutes();
        Map<String, String> busRouteMapping = getBusRouteMapping();
        
        Map<String, Object> response = new HashMap<>();
        response.put("routes", routes);
        response.put("busRouteMapping", busRouteMapping);
        response.put("count", routes.size());
        
        return ResponseEntity.ok(response);
    }

    /**
     * Get bus-route mapping for sample data
     * @return Map of busId to routeNo
     */
    private Map<String, String> getBusRouteMapping() {
        Map<String, String> mapping = new HashMap<>();
        mapping.put("BUS-001", "R001");
        mapping.put("BUS-002", "R001"); // BUS-002 also follows R001 for demo
        mapping.put("BUS-003", "R001"); // BUS-003 also follows R001 for demo
        return mapping;
    }
}
