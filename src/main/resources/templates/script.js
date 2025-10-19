class TransitTracker {
    constructor() {
        this.map = null;
        this.busMarkers = new Map();
        this.stopMarkers = new Map();
        this.userLocationMarker = null;
        this.isTracking = false;
        this.updateInterval = null;
        this.apiBase = '/api/map';
        this.routeLayers = new Map(); // Store route polylines
        this.routeStopMarkers = new Map(); // Store route stop markers
        this.currentRouteBusId = null; // Track which bus route is currently shown
        this.routeHideTimeout = null; // Timeout for hiding route
        
        this.initMap();
        this.bindEvents();
        this.loadInitialData();
    }
    
    initMap() {
        // Initialize the map centered on Kingston
        this.map = L.map('map').setView([18.7128, -76.0060], 12);
        
        // Add OpenStreetMap tiles
        L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
            attribution: '© OpenStreetMap contributors',
            maxZoom: 19
        }).addTo(this.map);
        
        // Add click handler for map
        this.map.on('click', (e) => {
            this.onMapClick(e.latlng);
        });
    }
    
    bindEvents() {
        document.getElementById('toggleTracking').addEventListener('click', () => {
            this.toggleTracking();
        });
        
        document.getElementById('centerMap').addEventListener('click', () => {
            this.centerMap();
        });
        
        document.getElementById('getMyLocation').addEventListener('click', () => {
            this.getUserLocation();
        });
        
        document.getElementById('updateInterval').addEventListener('change', (e) => {
            this.setUpdateInterval(parseInt(e.target.value));
        });
    }
    
    async loadInitialData() {
        try {
            this.updateStatus('Loading initial data...', 'info');
            await this.updateBusLocations();
            this.updateStatus('Data loaded successfully', 'success');
        } catch (error) {
            this.updateStatus('Error loading data: ' + error.message, 'error');
        }
    }
    
    toggleTracking() {
        const button = document.getElementById('toggleTracking');
        
        if (this.isTracking) {
            this.stopTracking();
            button.textContent = 'Start Tracking';
            button.classList.remove('btn-danger');
        } else {
            this.startTracking();
            button.textContent = 'Stop Tracking';
            button.classList.add('btn-danger');
        }
    }
    
    startTracking() {
        this.isTracking = true;
        const interval = parseInt(document.getElementById('updateInterval').value);
        this.updateInterval = setInterval(() => {
            this.updateBusLocations();
        }, interval);
        this.updateStatus('Tracking started', 'success');
    }
    
    stopTracking() {
        this.isTracking = false;
        if (this.updateInterval) {
            clearInterval(this.updateInterval);
            this.updateInterval = null;
        }
        this.updateStatus('Tracking stopped', 'info');
    }
    
    setUpdateInterval(interval) {
        if (this.isTracking) {
            this.stopTracking();
            this.startTracking();
        }
    }
    
    async updateBusLocations() {
        try {
            const response = await fetch(`${this.apiBase}/buses`);
            const data = await response.json();
            
            if (data.buses) {
                this.updateBusMarkers(data.buses);
                this.updateStatus(`Tracking ${data.count} buses`, 'success');
            }
        } catch (error) {
            this.updateStatus('Error updating locations: ' + error.message, 'error');
        }
    }
    
    updateBusMarkers(buses) {
        // Clear existing bus markers
        this.busMarkers.forEach(marker => this.map.removeLayer(marker));
        this.busMarkers.clear();
        
        // Add new bus markers
        Object.entries(buses).forEach(([busId, location]) => {
            const marker = L.circleMarker([location.latitude, location.longitude], {
                radius: 8,
                fillColor: '#667eea',
                color: '#fff',
                weight: 2,
                opacity: 1,
                fillOpacity: 0.8
            });
            
            // Add popup with bus information
            const popupContent = `
                <div class="bus-popup">
                    <h3>Bus ${busId}</h3>
                    <p><strong>Location:</strong> ${location.latitude.toFixed(6)}, ${location.longitude.toFixed(6)}</p>
                    <p><strong>Last Update:</strong> ${new Date(location.timestamp).toLocaleString()}</p>
                    <p><strong>Accuracy:</strong> ${location.accuracy ? location.accuracy.toFixed(1) + 'm' : 'N/A'}</p>
                    <p><strong>Route:</strong> <span id="route-info-${busId}">Loading...</span></p>
                </div>
            `;
            
            marker.bindPopup(popupContent);
            
            // Add hover events for route display
            marker.on('mouseover', () => {
                this.showBusRoute(busId);
            });
            
            marker.on('mouseout', () => {
                this.hideBusRoute(busId);
            });
            
            marker.addTo(this.map);
            this.busMarkers.set(busId, marker);
        });
    }
    
    onMapClick(latlng) {
        const radius = parseInt(document.getElementById('radius').value);
        this.findNearbyBuses(latlng.lat, latlng.lng, radius);
    }
    
    async findNearbyBuses(lat, lng, radius) {
        try {
            const response = await fetch(`${this.apiBase}/buses/nearby?lat=${lat}&lng=${lng}&radius=${radius}`);
            const data = await response.json();
            
            if (data.nearbyBuses) {
                this.highlightNearbyBuses(data.nearbyBuses);
                this.updateStatus(`Found ${data.count} buses within ${radius}km`, 'success');
            }
        } catch (error) {
            this.updateStatus('Error finding nearby buses: ' + error.message, 'error');
        }
    }
    
    highlightNearbyBuses(nearbyBuses) {
        // Reset all markers to default style
        this.busMarkers.forEach(marker => {
            marker.setStyle({
                fillColor: '#667eea',
                color: '#fff'
            });
        });
        
        // Highlight nearby buses
        Object.keys(nearbyBuses).forEach(busId => {
            const marker = this.busMarkers.get(busId);
            if (marker) {
                marker.setStyle({
                    fillColor: '#ff6b6b',
                    color: '#fff',
                    weight: 3
                });
            }
        });
    }
    
    getUserLocation() {
        if (!navigator.geolocation) {
            this.updateStatus('Geolocation is not supported by this browser', 'error');
            return;
        }
        
        this.updateStatus('Getting your location...', 'info');
        
        navigator.geolocation.getCurrentPosition(
            (position) => {
                const lat = position.coords.latitude;
                const lng = position.coords.longitude;
                const accuracy = position.coords.accuracy;
                
                this.showUserLocation(lat, lng, accuracy);
                this.updateStatus(`Location found! Accuracy: ${Math.round(accuracy)}m`, 'success');
            },
            (error) => {
                let errorMessage = 'Unable to get your location';
                switch(error.code) {
                    case error.PERMISSION_DENIED:
                        errorMessage = 'Location access denied by user';
                        break;
                    case error.POSITION_UNAVAILABLE:
                        errorMessage = 'Location information unavailable';
                        break;
                    case error.TIMEOUT:
                        errorMessage = 'Location request timed out';
                        break;
                }
                this.updateStatus(errorMessage, 'error');
            },
            {
                enableHighAccuracy: true,
                timeout: 10000,
                maximumAge: 300000 // 5 minutes
            }
        );
    }
    
    showUserLocation(lat, lng, accuracy) {
        // Remove existing user location marker
        if (this.userLocationMarker) {
            this.map.removeLayer(this.userLocationMarker);
        }
        
        // Create user location marker
        this.userLocationMarker = L.circleMarker([lat, lng], {
            radius: 10,
            fillColor: '#ff6b6b',
            color: '#fff',
            weight: 3,
            opacity: 1,
            fillOpacity: 0.8
        });
        
        // Create accuracy circle
        const accuracyCircle = L.circle([lat, lng], {
            radius: accuracy,
            color: '#ff6b6b',
            weight: 1,
            opacity: 0.3,
            fillColor: '#ff6b6b',
            fillOpacity: 0.1
        });
        
        // Add popup with user location info
        const popupContent = `
            <div class="bus-popup">
                <h3>📍 Your Location</h3>
                <p><strong>Coordinates:</strong> ${lat.toFixed(6)}, ${lng.toFixed(6)}</p>
                <p><strong>Accuracy:</strong> ${Math.round(accuracy)}m</p>
                <p><strong>Time:</strong> ${new Date().toLocaleString()}</p>
            </div>
        `;
        
        this.userLocationMarker.bindPopup(popupContent);
        this.userLocationMarker.addTo(this.map);
        accuracyCircle.addTo(this.map);
        
        // Center map on user location
        this.map.setView([lat, lng], 15);
        
        // Store accuracy circle for potential removal
        this.userLocationMarker.accuracyCircle = accuracyCircle;
    }
    
    centerMap() {
        if (this.userLocationMarker) {
            // If user location is available, center on it
            const latlng = this.userLocationMarker.getLatLng();
            this.map.setView(latlng, 15);
        } else if (this.busMarkers.size > 0) {
            const group = new L.featureGroup(Array.from(this.busMarkers.values()));
            this.map.fitBounds(group.getBounds().pad(0.1));
        } else {
            this.map.setView([18.7128, -76.0060], 12);
        }
    }
    
    updateStatus(message, type = 'info') {
        const statusEl = document.getElementById('status');
        statusEl.textContent = message;
        statusEl.className = `status ${type}`;
    }

    /**
     * Show route for a specific bus
     * @param {string} busId - The bus identifier
     */
    async showBusRoute(busId) {
        try {
            // Clear any pending hide timeout
            if (this.routeHideTimeout) {
                clearTimeout(this.routeHideTimeout);
                this.routeHideTimeout = null;
            }
            
            // Hide any currently displayed route
            this.hideCurrentRoute();
            
            // Fetch route data for the bus
            const response = await fetch(`${this.apiBase}/buses/${busId}/route`);
            if (!response.ok) {
                console.log(`No route found for bus ${busId}`);
                return;
            }
            
            const data = await response.json();
            this.displayRoute(data, busId);
            this.currentRouteBusId = busId;
            
        } catch (error) {
            console.error('Error fetching bus route:', error);
        }
    }

    /**
     * Hide the currently displayed route with a small delay
     */
    hideBusRoute(busId) {
        if (this.currentRouteBusId === busId) {
            // Clear any existing timeout
            if (this.routeHideTimeout) {
                clearTimeout(this.routeHideTimeout);
            }
            
            // Add a small delay before hiding to prevent flickering
            this.routeHideTimeout = setTimeout(() => {
                this.hideCurrentRoute();
            }, 500);
        }
    }

    /**
     * Hide the current route display
     */
    hideCurrentRoute() {
        // Remove route polylines
        this.routeLayers.forEach(layer => this.map.removeLayer(layer));
        this.routeLayers.clear();
        
        // Remove route stop markers
        this.routeStopMarkers.forEach(marker => this.map.removeLayer(marker));
        this.routeStopMarkers.clear();
        
        this.currentRouteBusId = null;
    }

    /**
     * Display route on the map
     * @param {Object} routeData - Route data from API
     * @param {string} busId - Bus identifier
     */
    displayRoute(routeData, busId) {
        const allStops = routeData.allStops;
        if (!allStops || allStops.length === 0) return;

        // Create route polyline
        const routeCoordinates = allStops.map(stop => [stop.location.latitude, stop.location.longitude]);
        const routePolyline = L.polyline(routeCoordinates, {
            color: '#ff6b6b',
            weight: 4,
            opacity: 0.8,
            dashArray: '10, 5'
        });
        
        routePolyline.addTo(this.map);
        this.routeLayers.set('route', routePolyline);

        // Add stop markers
        allStops.forEach((stop, index) => {
            let markerColor = '#4caf50'; // Default green for regular stops
            let markerSize = 6;
            
            if (stop.stopType === 'ORIGIN') {
                markerColor = '#2196f3'; // Blue for origin
                markerSize = 8;
            } else if (stop.stopType === 'DESTINATION') {
                markerColor = '#f44336'; // Red for destination
                markerSize = 8;
            }
            
            const stopMarker = L.circleMarker([stop.location.latitude, stop.location.longitude], {
                radius: markerSize,
                fillColor: markerColor,
                color: '#fff',
                weight: 2,
                opacity: 1,
                fillOpacity: 0.8
            });
            
            // Add popup for stop
            const stopPopupContent = `
                <div class="stop-popup">
                    <h4>${stop.stopName}</h4>
                    <p><strong>Type:</strong> ${stop.stopType}</p>
                    <p><strong>Stop ID:</strong> ${stop.stopId}</p>
                    <p><strong>Position:</strong> ${index + 1} of ${allStops.length}</p>
                </div>
            `;
            
            stopMarker.bindPopup(stopPopupContent);
            stopMarker.addTo(this.map);
            this.routeStopMarkers.set(stop.stopId, stopMarker);
        });

        // Update bus popup with route info
        const routeInfoElement = document.getElementById(`route-info-${busId}`);
        if (routeInfoElement) {
            routeInfoElement.textContent = `${routeData.route.routeNo} (${routeData.totalStops} stops)`;
        }

        // Fit map to show the entire route
        const group = new L.featureGroup([routePolyline, ...this.routeStopMarkers.values()]);
        this.map.fitBounds(group.getBounds().pad(0.1));
    }
}

// Initialize the transit tracker when the page loads
document.addEventListener('DOMContentLoaded', () => {
    new TransitTracker();
});