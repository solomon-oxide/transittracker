/* Generated for time */

package com.phoenixai.transittracker.model;

import java.util.ArrayList;

public class Company {
    private ArrayList<Bus> buses;
    private String companyId;
    private String companyName;
    private String description;
    private String website;
    private String phoneNumber;
    private String email;
    private String address;
    private String city;
    private String state;
    private String zipCode;
    private String country;

    // Constructors
    public Company() {
        this.buses = new ArrayList<Bus>();
    }

    public Company(String companyId, String companyName) {
        this.companyId = companyId;
        this.companyName = companyName;
    }

    public Company(String companyId, String companyName, String description, 
                   String website, String phoneNumber, String email,
                   String address, String city, String state, 
                   String zipCode, String country) {
        this.companyId = companyId;
        this.companyName = companyName;
        this.description = description;
        this.website = website;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
        this.country = country;
    }


    // Accessors
    public String getCompanyId() {
        return companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getDescription() {
        return description;
    }

    public String getWebsite() {
        return website;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getZipCode() {
        return zipCode;
    }

    public String getCountry() {
        return country;
    }

    public ArrayList<Bus> getBuses(){
        return buses;
    }



    // Mutators
    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public void setCountry(String country) {
        this.country = country;
    }



    // Utility method to get full address
    public String getFullAddress() {
        StringBuilder fullAddress = new StringBuilder();
        if (address != null && !address.isEmpty()) {
            fullAddress.append(address);
        }
        if (city != null && !city.isEmpty()) {
            if (fullAddress.length() > 0) fullAddress.append(", ");
            fullAddress.append(city);
        }
        if (state != null && !state.isEmpty()) {
            if (fullAddress.length() > 0) fullAddress.append(", ");
            fullAddress.append(state);
        }
        if (zipCode != null && !zipCode.isEmpty()) {
            if (fullAddress.length() > 0) fullAddress.append(" ");
            fullAddress.append(zipCode);
        }
        if (country != null && !country.isEmpty()) {
            if (fullAddress.length() > 0) fullAddress.append(", ");
            fullAddress.append(country);
        }
        return fullAddress.toString();
    }

    @Override
    public String toString() {
        return "Company{" +
                "companyId='" + companyId + '\'' +
                ", companyName='" + companyName + '}';
    }
}
