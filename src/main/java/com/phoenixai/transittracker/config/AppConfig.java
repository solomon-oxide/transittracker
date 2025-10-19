package com.phoenixai.transittracker.config;

import com.phoenixai.transittracker.services.GPSService;
import com.phoenixai.transittracker.model.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.boot.CommandLineRunner;


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
        // Sample bus locations in Kingston, Jamaica
        Location bus1Location = new Location(18.0287, -76.8059, 0, 5.0); // Red Hills Road
        Location bus2Location = new Location(18.0172, -76.7840, 0, 5.0); // Hope Road
        Location bus3Location = new Location(18.0699, -76.7899, 0, 5.0); // Stony Hill Road Bus Stop

        // Initialize buses with starting locations
        gpsService.initializeBus("BUS-001", bus1Location);
        gpsService.initializeBus("BUS-002", bus2Location);
        gpsService.initializeBus("BUS-003", bus3Location);

        System.out.println("Sample bus data initialized with 3 buses");
    }
}
