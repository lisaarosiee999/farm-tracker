package com.farmtracker.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.farmtracker.model.CropCycle;
import com.farmtracker.util.DatabaseConnection;

public class CropCycleDAO {
    public void addCropCycle(CropCycle cropCycle) throws SQLException {
        validateCropCycle(cropCycle);

        String sql = "INSERT INTO crop "
                + "(farm_id, crop_name, season, start_date, end_date, expected_revenue, actual_revenue) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, cropCycle.getFarmId());
            statement.setString(2, cropCycle.getCropName().trim());
            statement.setString(3, cropCycle.getSeason().trim());
            statement.setDate(4, Date.valueOf(cropCycle.getStartDate()));
            statement.setDate(5, Date.valueOf(cropCycle.getEndDate()));
            statement.setDouble(6, cropCycle.getExpectedRevenue());
            statement.setDouble(7, cropCycle.getActualRevenue());
            statement.executeUpdate();
        }
    }

    public List<CropCycle> getAllCropCycles() throws SQLException {
        List<CropCycle> cropCycles = new ArrayList<>();
        String sql = "SELECT c.id, c.farm_id, f.farm_name, c.crop_name, c.season, "
                + "c.start_date, c.end_date, c.expected_revenue, c.actual_revenue "
                + "FROM crop c "
                + "JOIN farm f ON c.farm_id = f.id "
                + "ORDER BY c.id DESC";

        try (Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                CropCycle cropCycle = new CropCycle();
                cropCycle.setId(resultSet.getInt("id"));
                cropCycle.setFarmId(resultSet.getInt("farm_id"));
                cropCycle.setFarmName(resultSet.getString("farm_name"));
                cropCycle.setCropName(resultSet.getString("crop_name"));
                cropCycle.setSeason(resultSet.getString("season"));
                cropCycle.setStartDate(resultSet.getDate("start_date").toLocalDate());
                cropCycle.setEndDate(resultSet.getDate("end_date").toLocalDate());
                cropCycle.setExpectedRevenue(resultSet.getDouble("expected_revenue"));
                cropCycle.setActualRevenue(resultSet.getDouble("actual_revenue"));
                cropCycles.add(cropCycle);
            }
        }

        return cropCycles;
    }

    public void updateCropCycle(CropCycle cropCycle) throws SQLException {
        validateCropCycle(cropCycle);
        if (cropCycle.getId() <= 0) {
            throw new SQLException("Valid crop id is required for update.");
        }

        String sql = "UPDATE crop SET farm_id = ?, crop_name = ?, season = ?, start_date = ?, "
                + "end_date = ?, expected_revenue = ?, actual_revenue = ? WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, cropCycle.getFarmId());
            statement.setString(2, cropCycle.getCropName().trim());
            statement.setString(3, cropCycle.getSeason().trim());
            statement.setDate(4, Date.valueOf(cropCycle.getStartDate()));
            statement.setDate(5, Date.valueOf(cropCycle.getEndDate()));
            statement.setDouble(6, cropCycle.getExpectedRevenue());
            statement.setDouble(7, cropCycle.getActualRevenue());
            statement.setInt(8, cropCycle.getId());
            statement.executeUpdate();
        }
    }

    public void deleteCropCycle(int id) throws SQLException {
        if (id <= 0) {
            throw new SQLException("Valid crop id is required for delete.");
        }

        String sql = "DELETE FROM crop WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }

    private void validateCropCycle(CropCycle cropCycle) throws SQLException {
        if (cropCycle == null) {
            throw new SQLException("Crop cycle details are required.");
        }
        if (cropCycle.getFarmId() <= 0) {
            throw new SQLException("Please select a valid farm.");
        }
        if (isBlank(cropCycle.getCropName())) {
            throw new SQLException("Crop name is required.");
        }
        if (isBlank(cropCycle.getSeason())) {
            throw new SQLException("Season is required.");
        }
        if (cropCycle.getStartDate() == null) {
            throw new SQLException("Start date is required.");
        }
        if (cropCycle.getEndDate() == null) {
            throw new SQLException("End date is required.");
        }
        if (cropCycle.getEndDate().isBefore(cropCycle.getStartDate())) {
            throw new SQLException("End date cannot be before start date.");
        }
        if (cropCycle.getExpectedRevenue() < 0) {
            throw new SQLException("Expected revenue cannot be negative.");
        }
        if (cropCycle.getActualRevenue() < 0) {
            throw new SQLException("Actual revenue cannot be negative.");
        }
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}
