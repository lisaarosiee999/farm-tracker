package com.farmtracker.model;

public class Farm {
    private int id;
    private String farmName;
    private String ownerName;
    private String location;
    private double sizeInAcres;

    public Farm() {
    }

    public Farm(String farmName, String ownerName, String location, double sizeInAcres) {
        this.farmName = farmName;
        this.ownerName = ownerName;
        this.location = location;
        this.sizeInAcres = sizeInAcres;
    }

    public Farm(int id, String farmName, String ownerName, String location, double sizeInAcres) {
        this(farmName, ownerName, location, sizeInAcres);
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFarmName() {
        return farmName;
    }

    public void setFarmName(String farmName) {
        this.farmName = farmName;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public double getSizeInAcres() {
        return sizeInAcres;
    }

    public void setSizeInAcres(double sizeInAcres) {
        this.sizeInAcres = sizeInAcres;
    }
}
