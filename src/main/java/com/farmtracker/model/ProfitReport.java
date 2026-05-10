package com.farmtracker.model;

public class ProfitReport {
    private final String farmName;
    private final String cropName;
    private final String season;
    private final double revenue;
    private final double totalExpenses;

    public ProfitReport(String farmName, String cropName, String season, double revenue, double totalExpenses) {
        this.farmName = farmName;
        this.cropName = cropName;
        this.season = season;
        this.revenue = revenue;
        this.totalExpenses = totalExpenses;
    }

    public String getFarmName() {
        return farmName;
    }

    public String getCropName() {
        return cropName;
    }

    public String getSeason() {
        return season;
    }

    public double getRevenue() {
        return revenue;
    }

    public double getTotalExpenses() {
        return totalExpenses;
    }

    public double getProfit() {
        return revenue - totalExpenses;
    }
}
