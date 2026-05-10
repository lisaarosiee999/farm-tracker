package com.farmtracker.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.farmtracker.exception.NegativeCostException;
import com.farmtracker.model.Expense;
import com.farmtracker.model.ExpenseRecord;
import com.farmtracker.model.FixedExpense;
import com.farmtracker.model.VariableExpense;
import com.farmtracker.util.DatabaseConnection;

public class ExpenseDAO {
    public void addExpense(FixedExpense expense) throws SQLException {
        addExpense((Expense) expense);
    }

    public void addExpense(VariableExpense expense) throws SQLException {
        addExpense((Expense) expense);
    }

    // Method overloading: this version builds the expense object from basic values.
    public void addExpense(int cropCycleId, String expenseName, double cost, String expenseType)
            throws SQLException, NegativeCostException {
        if ("FIXED".equalsIgnoreCase(expenseType)) {
            addExpense(new FixedExpense(cropCycleId, expenseName, cost, java.time.LocalDate.now()));
        } else if ("VARIABLE".equalsIgnoreCase(expenseType)) {
            addExpense(new VariableExpense(cropCycleId, expenseName, cost, java.time.LocalDate.now()));
        } else {
            throw new SQLException("Expense type must be FIXED or VARIABLE.");
        }
    }

    private void addExpense(Expense expense) throws SQLException {
        validateExpense(expense);

        String sql = "INSERT INTO expense (crop_id, expense_name, expense_type, cost, expense_date) "
                + "VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, expense.getCropCycleId());
            statement.setString(2, expense.getExpenseName().trim());
            statement.setString(3, expense.getExpenseType());
            statement.setDouble(4, expense.getCost());
            statement.setDate(5, Date.valueOf(expense.getExpenseDate()));
            statement.executeUpdate();
        }
    }

    public List<ExpenseRecord> getAllExpenses() throws SQLException {
        List<ExpenseRecord> expenses = new ArrayList<>();
        String sql = "SELECT e.id, e.crop_id, f.farm_name, c.crop_name, e.expense_name, "
                + "e.expense_type, e.cost, e.expense_date "
                + "FROM expense e "
                + "JOIN crop c ON e.crop_id = c.id "
                + "JOIN farm f ON c.farm_id = f.id "
                + "ORDER BY e.id DESC";

        try (Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                expenses.add(new ExpenseRecord(
                        resultSet.getInt("id"),
                        resultSet.getInt("crop_id"),
                        resultSet.getString("farm_name"),
                        resultSet.getString("crop_name"),
                        resultSet.getString("expense_name"),
                        resultSet.getString("expense_type"),
                        resultSet.getDouble("cost"),
                        resultSet.getDate("expense_date").toLocalDate()));
            }
        }

        return expenses;
    }

    public void updateExpense(int id, Expense expense) throws SQLException {
        validateExpense(expense);
        if (id <= 0) {
            throw new SQLException("Valid expense id is required for update.");
        }

        String sql = "UPDATE expense SET crop_id = ?, expense_name = ?, expense_type = ?, "
                + "cost = ?, expense_date = ? WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, expense.getCropCycleId());
            statement.setString(2, expense.getExpenseName().trim());
            statement.setString(3, expense.getExpenseType());
            statement.setDouble(4, expense.getCost());
            statement.setDate(5, Date.valueOf(expense.getExpenseDate()));
            statement.setInt(6, id);
            statement.executeUpdate();
        }
    }

    public void deleteExpense(int id) throws SQLException {
        if (id <= 0) {
            throw new SQLException("Valid expense id is required for delete.");
        }

        String sql = "DELETE FROM expense WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }

    private void validateExpense(Expense expense) throws SQLException {
        if (expense == null) {
            throw new SQLException("Expense details are required.");
        }
        if (expense.getCropCycleId() <= 0) {
            throw new SQLException("Please select a valid crop cycle.");
        }
        if (isBlank(expense.getExpenseName())) {
            throw new SQLException("Expense name is required.");
        }
        if (!"FIXED".equals(expense.getExpenseType()) && !"VARIABLE".equals(expense.getExpenseType())) {
            throw new SQLException("Expense type must be FIXED or VARIABLE.");
        }
        if (expense.getCost() < 0) {
            throw new SQLException("Expense cost cannot be negative.");
        }
        if (expense.getExpenseDate() == null) {
            throw new SQLException("Expense date is required.");
        }
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}
