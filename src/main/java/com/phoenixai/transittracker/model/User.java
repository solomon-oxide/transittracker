package com.phoenixai.transittracker.model;

public abstract class User {
    protected String id;
    protected Location location;

    public User() {
    }

    public User(String id, Location location) {
        this.id = id;
        this.location = location;
    }

    // Accessors
    public String getId() {
        return id;
    }

    public Location getLocation() {
        return location;
    }

    // Mutators
    public void setId(String id) {
        this.id = id;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" +
                "id='" + id + '\'' +
                ", location=" + location +
                '}';
    }
}
