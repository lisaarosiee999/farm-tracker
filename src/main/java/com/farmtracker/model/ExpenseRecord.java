package com.farmtracker.model;

import java.time.LocalDate;

public class ExpenseRecord {
    private final int id;
    private final int cropCycleId;
    private final String farmName;
    private final String cropName;
    private final String expenseName;
    private final String expenseType;
    private final double cost;
    private final LocalDate expenseDate;

    public ExpenseRecord(int id, int cropCycleId, String farmName, String cropName,
            String expenseName, String expenseType, double cost, LocalDate expenseDate) {
        this.id = id;
        this.cropCycleId = cropCycleId;
        this.farmName = farmName;
        this.cropName = cropName;
        this.expenseName = expenseName;
        this.expenseType = expenseType;
        this.cost = cost;
        this.expenseDate = expenseDate;
    }

    public int getId() {
        return id;
    }

    public int getCropCycleId() {
        return cropCycleId;
    }

    public String getFarmName() {
        return farmName;
    }

    public String getCropName() {
        return cropName;
    }

    public String getExpenseName() {
        return expenseName;
    }

    public String getExpenseType() {
        return expenseType;
    }

    public double getCost() {
        return cost;
    }

    public LocalDate getExpenseDate() {
        return expenseDate;
    }
}
