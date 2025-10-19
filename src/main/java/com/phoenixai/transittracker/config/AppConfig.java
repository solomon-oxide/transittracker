package com.phoenixai.transittracker.config;

import com.phoenixai.transittracker.services.GPSService;
import com.phoenixai.transittracker.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.boot.CommandLineRunner;
import java.util.ArrayList;


/**
 * Application configuration for GPS tracking and scheduling
 */
@Configuration
@EnableScheduling
public class AppConfig implements CommandLineRunner {

    @Autowired
    private GPSService gpsService;

    /**
     * Initialize sample bus data for demonstration
     */
    @Override
    public void run(String... args) throws Exception {
        initializeSampleData();
    }

    private void initializeSampleData() {
        // Create sample company
        Company jutc = new Company(
            "JUTC", 
            "Jamaica Urban Transit Company", 
            "Public transit company in Kingston",
            "https://jutc.gov.jm",
            "+1(876)749-3192-9",
            "pr@jutc.com.jm",
            "Michael Manley Drive",
            "Spanish Town",
            "St Catherine",
            "JM01",
            "Jamaica"
        );

        // Create route for BUS-001: Half Way Tree to Downtown Kingston
        // Origin: Half Way Tree
        Location halfWayTreeLocation = new Location(18.0172, -76.7840, 0, 5.0);
        Origin halfWayTreeOrigin = new Origin("HWT-001", "Half Way Tree", halfWayTreeLocation);
        
        // Destination: Downtown Kingston
        Location downtownLocation = new Location(17.9714, -76.7932, 0, 5.0);
        Destination downtownDestination = new Destination("DT-001", "Downtown Kingston", downtownLocation);
        
        // Create the route
        Route route001 = new Route("R001", halfWayTreeOrigin, downtownDestination);
        route001.setDistance(8.5); // 8.5 kilometers
        route001.setEstimatedDuration(25); // 25 minutes
        
        // Create stops along the route
        ArrayList<Stop> routeStops = new ArrayList<>();
        
        // Stop 1: Hope Road & Trafalgar Road
        Location stop1Location = new Location(18.0156, -76.7856, 0, 5.0);
        Stop stop1 = new Stop("S001", "Hope Road & Trafalgar Road", stop1Location);
        routeStops.add(stop1);
        
        // Stop 2: New Kingston
        Location stop2Location = new Location(18.0089, -76.7890, 0, 5.0);
        Stop stop2 = new Stop("S002", "New Kingston", stop2Location);
        routeStops.add(stop2);
        
        // Stop 3: Cross Roads
        Location stop3Location = new Location(17.9956, -76.7923, 0, 5.0);
        Stop stop3 = new Stop("S003", "Cross Roads", stop3Location);
        routeStops.add(stop3);
        
        // Stop 4: Parade
        Location stop4Location = new Location(17.9789, -76.7945, 0, 5.0);
        Stop stop4 = new Stop("S004", "Parade", stop4Location);
        routeStops.add(stop4);
        
        // Add stops to route
        route001.setStops(routeStops);
        
        // Initialize BUS-001 with GPS service at starting location
        Location bus1StartLocation = new Location(18.0172, -76.7840, 0, 5.0); // Starting at Half Way Tree
        gpsService.initializeBus("BUS-001", bus1StartLocation);
        
        // Initialize other buses with basic locations
        Location bus2Location = new Location(18.0287, -76.8059, 0, 5.0); // Red Hills Road
        Location bus3Location = new Location(18.0699, -76.7899, 0, 5.0); // Stony Hill Road Bus Stop
        
        gpsService.initializeBus("BUS-002", bus2Location);
        gpsService.initializeBus("BUS-003", bus3Location);

        System.out.println("Sample bus data initialized:");
        System.out.println("- BUS-001: Route R001 (Half Way Tree to Downtown Kingston)");
        System.out.println("  Company: " + jutc.getCompanyName());
        System.out.println("  Distance: " + route001.getDistance() + " km");
        System.out.println("  Duration: " + route001.getEstimatedDuration() + " minutes");
        System.out.println("  Stops: " + route001.getStopCount());
        System.out.println("- BUS-002: Basic location tracking");
        System.out.println("- BUS-003: Basic location tracking");
    }
}
