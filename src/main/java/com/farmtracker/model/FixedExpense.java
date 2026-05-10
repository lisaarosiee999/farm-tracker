package com.farmtracker.model;

import java.time.LocalDate;

import com.farmtracker.exception.NegativeCostException;

public class FixedExpense extends Expense {
    public FixedExpense(int cropCycleId, String expenseName, double cost, LocalDate expenseDate)
            throws NegativeCostException {
        super(cropCycleId, expenseName, cost, expenseDate);
    }

    @Override
    public String getExpenseType() {
        return "FIXED";
    }
}
