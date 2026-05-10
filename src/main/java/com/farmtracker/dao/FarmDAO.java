package com.farmtracker.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.farmtracker.model.Farm;
import com.farmtracker.util.DatabaseConnection;

public class FarmDAO {
    public void addFarm(Farm farm) throws SQLException {
        validateFarm(farm);

        String sql = "INSERT INTO farm (farm_name, owner_name, location, size_acres) VALUES (?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, farm.getFarmName().trim());
            statement.setString(2, farm.getOwnerName().trim());
            statement.setString(3, farm.getLocation().trim());
            statement.setDouble(4, farm.getSizeInAcres());
            statement.executeUpdate();
        }
    }

    public List<Farm> getAllFarms() throws SQLException {
        List<Farm> farms = new ArrayList<>();
        String sql = "SELECT id, farm_name, owner_name, location, size_acres FROM farm ORDER BY id DESC";

        try (Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Farm farm = new Farm(
                        resultSet.getInt("id"),
                        resultSet.getString("farm_name"),
                        resultSet.getString("owner_name"),
                        resultSet.getString("location"),
                        resultSet.getDouble("size_acres"));
                farms.add(farm);
            }
        }

        return farms;
    }

    public void updateFarm(Farm farm) throws SQLException {
        validateFarm(farm);
        if (farm.getId() <= 0) {
            throw new SQLException("Valid farm id is required for update.");
        }

        String sql = "UPDATE farm SET farm_name = ?, owner_name = ?, location = ?, size_acres = ? WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, farm.getFarmName().trim());
            statement.setString(2, farm.getOwnerName().trim());
            statement.setString(3, farm.getLocation().trim());
            statement.setDouble(4, farm.getSizeInAcres());
            statement.setInt(5, farm.getId());
            statement.executeUpdate();
        }
    }

    public void deleteFarm(int id) throws SQLException {
        if (id <= 0) {
            throw new SQLException("Valid farm id is required for delete.");
        }

        String sql = "DELETE FROM farm WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }

    private void validateFarm(Farm farm) throws SQLException {
        if (farm == null) {
            throw new SQLException("Farm details are required.");
        }
        if (isBlank(farm.getFarmName())) {
            throw new SQLException("Farm name is required.");
        }
        if (isBlank(farm.getOwnerName())) {
            throw new SQLException("Owner name is required.");
        }
        if (isBlank(farm.getLocation())) {
            throw new SQLException("Location is required.");
        }
        if (farm.getSizeInAcres() <= 0) {
            throw new SQLException("Farm size must be greater than zero.");
        }
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}
