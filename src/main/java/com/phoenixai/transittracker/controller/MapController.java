package com.phoenixai.transittracker.controller;

import com.phoenixai.transittracker.services.GPSService;
import com.phoenixai.transittracker.model.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * REST Controller for handling map and GPS related operations
 */
@RestController
@RequestMapping("/api/map")
public class MapController {

    @Autowired
    private GPSService gpsService;


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
}
