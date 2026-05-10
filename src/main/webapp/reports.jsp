<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page import="com.farmtracker.model.ProfitReport" %>
<%!
    private String h(Object value) {
        if (value == null) {
            return "";
        }
        return value.toString()
                .replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;");
    }
%>
<%
    List<ProfitReport> reports = (List<ProfitReport>) request.getAttribute("reports");

    if (reports == null) {
        reports = new ArrayList<ProfitReport>();
    }

    String databaseError = (String) request.getAttribute("databaseError");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Profit Reports</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
</head>
<body>
    <header class="topbar">
        <div>
            <h1>Profit Reports</h1>
            <p>Revenue minus total expenses for each crop.</p>
        </div>
        <nav>
            <a href="<%= request.getContextPath() %>/dashboard">Dashboard</a>
            <a href="<%= request.getContextPath() %>/reports">Reports</a>
        </nav>
    </header>

    <main class="page">
        <% if (databaseError != null) { %>
            <div class="message error">Database error: <%= h(databaseError) %></div>
        <% } %>

        <section class="panel wide">
            <h2>Crop Profit Report</h2>
            <div class="table-wrap">
                <table>
                    <thead>
                        <tr>
                            <th>Farm</th>
                            <th>Crop</th>
                            <th>Season</th>
                            <th>Revenue</th>
                            <th>Total Expenses</th>
                            <th>Profit</th>
                        </tr>
                    </thead>
                    <tbody>
                        <% if (reports.isEmpty()) { %>
                            <tr><td colspan="6">No reports available yet.</td></tr>
                        <% } %>

                        <% for (ProfitReport report : reports) { %>
                            <tr>
                                <td><%= h(report.getFarmName()) %></td>
                                <td><%= h(report.getCropName()) %></td>
                                <td><%= h(report.getSeason()) %></td>
                                <td><%= report.getRevenue() %></td>
                                <td><%= report.getTotalExpenses() %></td>
                                <td class="<%= report.getProfit() >= 0 ? "profit" : "loss" %>">
                                    <%= report.getProfit() %>
                                </td>
                            </tr>
                        <% } %>
                    </tbody>
                </table>
            </div>
        </section>
    </main>
</body>
</html>
