package com.farmtracker.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.farmtracker.model.ProfitReport;
import com.farmtracker.util.DatabaseConnection;

public class ReportDAO {
    public double getProfitByCropCycle(int cropCycleId) throws SQLException {
        String sql = "SELECT c.actual_revenue, COALESCE(SUM(e.cost), 0) AS total_expenses "
                + "FROM crop c "
                + "LEFT JOIN expense e ON e.crop_id = c.id "
                + "WHERE c.id = ? "
                + "GROUP BY c.id, c.actual_revenue";

        try (Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, cropCycleId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    double actualRevenue = resultSet.getDouble("actual_revenue");
                    double totalExpenses = resultSet.getDouble("total_expenses");
                    return actualRevenue - totalExpenses;
                }
            }
        }

        throw new SQLException("No crop cycle was found for id " + cropCycleId + ".");
    }

    public List<Integer> getFarmIds() throws SQLException {
        List<Integer> farmIds = new ArrayList<>();
        String sql = "SELECT id FROM farm ORDER BY id";

        try (Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                farmIds.add(resultSet.getInt("id"));
            }
        }

        return farmIds;
    }

    public List<ProfitReport> getReportsByFarm(int farmId) throws SQLException {
        List<ProfitReport> reports = new ArrayList<>();
        String sql = "SELECT f.farm_name, c.crop_name, c.season, c.actual_revenue, "
                + "COALESCE(SUM(e.cost), 0) AS total_expenses "
                + "FROM crop c "
                + "JOIN farm f ON c.farm_id = f.id "
                + "LEFT JOIN expense e ON e.crop_id = c.id "
                + "WHERE f.id = ? "
                + "GROUP BY c.id, f.farm_name, c.crop_name, c.season, c.actual_revenue "
                + "ORDER BY f.farm_name, c.crop_name";

        try (Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, farmId);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    ProfitReport report = new ProfitReport(
                            resultSet.getString("farm_name"),
                            resultSet.getString("crop_name"),
                            resultSet.getString("season"),
                            resultSet.getDouble("actual_revenue"),
                            resultSet.getDouble("total_expenses"));
                    reports.add(report);
                }
            }
        }

        return reports;
    }

    public List<ProfitReport> getAllReports() throws SQLException {
        List<ProfitReport> reports = new ArrayList<>();
        String sql = "SELECT f.farm_name, c.crop_name, c.season, c.actual_revenue, "
                + "COALESCE(SUM(e.cost), 0) AS total_expenses "
                + "FROM crop c "
                + "JOIN farm f ON c.farm_id = f.id "
                + "LEFT JOIN expense e ON e.crop_id = c.id "
                + "GROUP BY c.id, f.farm_name, c.crop_name, c.season, c.actual_revenue "
                + "ORDER BY f.farm_name, c.crop_name";

        try (Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                reports.add(new ProfitReport(
                        resultSet.getString("farm_name"),
                        resultSet.getString("crop_name"),
                        resultSet.getString("season"),
                        resultSet.getDouble("actual_revenue"),
                        resultSet.getDouble("total_expenses")));
            }
        }

        return reports;
    }
}
