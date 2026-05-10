package com.farmtracker.model;

import java.time.LocalDate;

import com.farmtracker.exception.NegativeCostException;

public abstract class Expense {
    private int cropCycleId;
    private String expenseName;
    private double cost;
    private LocalDate expenseDate;

    protected Expense(int cropCycleId, String expenseName, double cost, LocalDate expenseDate)
            throws NegativeCostException {
        if (cost < 0) {
            throw new NegativeCostException("Expense cost cannot be negative.");
        }
        this.cropCycleId = cropCycleId;
        this.expenseName = expenseName;
        this.cost = cost;
        this.expenseDate = expenseDate;
    }

    public abstract String getExpenseType();

    public int getCropCycleId() {
        return cropCycleId;
    }

    public String getExpenseName() {
        return expenseName;
    }

    public double getCost() {
        return cost;
    }

    public LocalDate getExpenseDate() {
        return expenseDate;
    }
}
