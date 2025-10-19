package com.phoenixai.transittracker.services;

import com.phoenixai.transittracker.model.Location;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Service for handling GPS location tracking and management
 */
@Service
public class GPSService {
    
    private final RestTemplate restTemplate;
    private final Map<String, Location> passengerLocation = new ConcurrentHashMap<>();
    private final Map<String, Location> busLocations = new ConcurrentHashMap<>();
    private final List<Location> locationHistory = new CopyOnWriteArrayList<>();
    private final Map<String, List<Location>> busLocationHistory = new ConcurrentHashMap<>();
    
    // Mock GPS data for demonstration (in real app, this would come from GPS devices)
    private final Random random = new Random();
    
    public GPSService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    /**
     * Get the current location of the user.
     * In a real application, this would be tied to authentication and device/user context.
     * @return Current user location, or null if unavailable
     */
    public Location getCurrentUserLocation(String passengerId) {
        return passengerLocation.get(passengerId);
    }



    /**
     * Get current location of a specific bus
     * @param busId The bus identifier
     * @return Current location or null if not found
     */
    public Location getBusLocation(String busId) {
        return busLocations.get(busId);
    }

    /**
     * Update bus location (called by GPS devices or simulation)
     * @param busId The bus identifier
     * @param location The new location
     */
    public void updateBusLocation(String busId, Location location) {
        // Store current location
        busLocations.put(busId, location);
        
        // Add to history
        busLocationHistory.computeIfAbsent(busId, k -> new ArrayList<>()).add(location);
        
        // Keep only last 100 locations per bus to manage memory
        List<Location> history = busLocationHistory.get(busId);
        if (history.size() > 100) {
            history.remove(0);
        }
        
        // Add to general location history
        locationHistory.add(location);
    }

    /**
     * Simulate GPS updates for buses (for demonstration purposes)
     * This would be replaced with actual GPS device integration
     */
    @Scheduled(fixedRate = 5000) // Update every 5 seconds
    public void simulateGPSUpdates() {
        // Simulate movement for buses
        for (String busId : busLocations.keySet()) {
            Location currentLocation = busLocations.get(busId);
            if (currentLocation != null) {
                // Simulate small random movement
                double latOffset = (random.nextDouble() - 0.5) * 0.001; // ~100m variation
                double lngOffset = (random.nextDouble() - 0.5) * 0.001;
                
                Location newLocation = new Location(
                    currentLocation.getLatitude() + latOffset,
                    currentLocation.getLongitude() + lngOffset,
                    currentLocation.getAltitude(),
                    currentLocation.getAccuracy()
                );
                
                updateBusLocation(busId, newLocation);
            }
        }
    }

    /**
     * Passenger location on startup
     * @param passengerId Passenger identifier
     * @param location starting location
     */
    public void initializePassenger(String passengerId, Location location) {
        passengerLocation.put(passengerId, location);
    }

    /**
     * Initialize a bus with a starting location
     * @param busId The bus identifier
     * @param location The starting location
     */
    public void initializeBus(String busId, Location location) {
        busLocations.put(busId, location);
        busLocationHistory.computeIfAbsent(busId, k -> new ArrayList<>()).add(location);
        locationHistory.add(location);
    }

    /**
     * Get all current bus locations
     * @return Map of bus IDs to their current locations
     */
    public Map<String, Location> getAllBusLocations() {
        return new HashMap<>(busLocations);
    }

    /**
     * Get location history for a specific bus
     * @param busId The bus identifier
     * @return List of historical locations
     */
    public List<Location> getBusLocationHistory(String busId) {
        return busLocationHistory.getOrDefault(busId, new ArrayList<>());
    }

    /**
     * Get buses within a certain radius of a location
     * @param centerLocation The center location
     * @param radiusKm The radius in kilometers
     * @return Map of bus IDs to their locations within the radius
     */
    public Map<String, Location> getBusesWithinRadius(Location centerLocation, double radiusKm) {
        Map<String, Location> nearbyBuses = new HashMap<>();
        
        for (Map.Entry<String, Location> entry : busLocations.entrySet()) {
            if (entry.getValue().isWithinRadius(centerLocation, radiusKm)) {
                nearbyBuses.put(entry.getKey(), entry.getValue());
            }
        }
        
        return nearbyBuses;
    }

    /**
     * Remove a bus from tracking
     * @param busId The bus identifier to remove
     */
    public void removeBus(String busId) {
        busLocations.remove(busId);
        busLocationHistory.remove(busId);
    }
}
