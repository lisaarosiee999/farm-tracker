<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.time.LocalDate" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page import="com.farmtracker.model.Farm" %>
<%@ page import="com.farmtracker.model.CropCycle" %>
<%@ page import="com.farmtracker.model.ExpenseRecord" %>
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
    List<Farm> farms = (List<Farm>) request.getAttribute("farms");
    List<CropCycle> cropCycles = (List<CropCycle>) request.getAttribute("cropCycles");
    List<ExpenseRecord> expenses = (List<ExpenseRecord>) request.getAttribute("expenses");
    List<ProfitReport> reports = (List<ProfitReport>) request.getAttribute("reports");

    if (farms == null) {
        farms = new ArrayList<Farm>();
    }

    if (cropCycles == null) {
        cropCycles = new ArrayList<CropCycle>();
    }

    if (expenses == null) {
        expenses = new ArrayList<ExpenseRecord>();
    }

    if (reports == null) {
        reports = new ArrayList<ProfitReport>();
    }

    String success = request.getParameter("success");
    String error = request.getParameter("error");
    String databaseError = (String) request.getAttribute("databaseError");
    String today = LocalDate.now().toString();
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Farm Expense and Profitability Tracker</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
</head>
<body>
    <header class="topbar">
        <div>
            <h1>Farm Expense and Profitability Tracker</h1>
            <p>Simple farm cost, crop revenue, and profit tracking.</p>
        </div>
        <nav>
            <a href="<%= request.getContextPath() %>/dashboard">Dashboard</a>
            <a href="<%= request.getContextPath() %>/reports">Reports</a>
        </nav>
    </header>

    <main class="page">
        <% if (success != null) { %>
            <div class="message success"><%= h(success) %></div>
        <% } %>

        <% if (error != null) { %>
            <div class="message error"><%= h(error) %></div>
        <% } %>

        <% if (databaseError != null) { %>
            <div class="message error">Database error: <%= h(databaseError) %></div>
        <% } %>

        <section class="grid">
            <form class="panel" method="post" action="<%= request.getContextPath() %>/add-farm">
                <h2>Add Farm Details</h2>

                <label for="farmName">Farm Name</label>
                <input id="farmName" name="farmName" type="text" required>

                <label for="ownerName">Owner Name</label>
                <input id="ownerName" name="ownerName" type="text" required>

                <label for="location">Location</label>
                <input id="location" name="location" type="text" required>

                <label for="sizeInAcres">Size in Acres</label>
                <input id="sizeInAcres" name="sizeInAcres" type="number" min="0.01" step="0.01" required>

                <button type="submit">Save Farm</button>
            </form>

            <form class="panel" method="post" action="<%= request.getContextPath() %>/add-crop-cycle">
                <h2>Add Crop Cycle</h2>

                <label for="farmId">Farm</label>
                <select id="farmId" name="farmId" required>
                    <option value="">Select farm</option>
                    <% for (Farm farm : farms) { %>
                        <option value="<%= farm.getId() %>"><%= h(farm.getFarmName()) %></option>
                    <% } %>
                </select>

                <label for="cropName">Crop Name</label>
                <input id="cropName" name="cropName" type="text" required>

                <label for="season">Season</label>
                <input id="season" name="season" type="text" placeholder="Kharif / Rabi / Summer" required>

                <div class="two-column">
                    <div>
                        <label for="startDate">Start Date</label>
                        <input id="startDate" name="startDate" type="date" required>
                    </div>
                    <div>
                        <label for="endDate">End Date</label>
                        <input id="endDate" name="endDate" type="date" required>
                    </div>
                </div>

                <label for="expectedRevenue">Expected Revenue</label>
                <input id="expectedRevenue" name="expectedRevenue" type="number" min="0" step="0.01" required>

                <label for="actualRevenue">Actual Revenue</label>
                <input id="actualRevenue" name="actualRevenue" type="number" min="0" step="0.01" required>

                <button type="submit">Save Crop Cycle</button>
            </form>

            <form class="panel" method="post" action="<%= request.getContextPath() %>/add-expense">
                <h2>Add Expense</h2>

                <label for="cropCycleId">Crop Cycle</label>
                <select id="cropCycleId" name="cropCycleId" required>
                    <option value="">Select crop cycle</option>
                    <% for (CropCycle cropCycle : cropCycles) { %>
                        <option value="<%= cropCycle.getId() %>">
                            <%= h(cropCycle.getFarmName()) %> - <%= h(cropCycle.getCropName()) %>
                        </option>
                    <% } %>
                </select>

                <label for="expenseName">Expense Name</label>
                <input id="expenseName" name="expenseName" type="text" placeholder="Labor, seeds, fertilizer" required>

                <label for="expenseType">Expense Type</label>
                <select id="expenseType" name="expenseType" required>
                    <option value="FIXED">Fixed Expense</option>
                    <option value="VARIABLE">Variable Expense</option>
                </select>

                <label for="cost">Cost</label>
                <input id="cost" name="cost" type="number" min="0" step="0.01" required>

                <label for="expenseDate">Expense Date</label>
                <input id="expenseDate" name="expenseDate" type="date" value="<%= today %>" required>

                <button type="submit">Save Expense</button>
            </form>
        </section>

        <section class="panel wide">
            <h2>Saved Farms</h2>
            <div class="table-wrap">
                <table>
                    <thead>
                        <tr>
                            <th>Farm</th>
                            <th>Owner</th>
                            <th>Location</th>
                            <th>Acres</th>
                            <th>Action</th>
                        </tr>
                    </thead>
                    <tbody>
                        <% if (farms.isEmpty()) { %>
                            <tr><td colspan="5">No farms saved yet.</td></tr>
                        <% } %>

                        <% for (Farm farm : farms) { %>
                            <tr>
                                <td><%= h(farm.getFarmName()) %></td>
                                <td><%= h(farm.getOwnerName()) %></td>
                                <td><%= h(farm.getLocation()) %></td>
                                <td><%= farm.getSizeInAcres() %></td>
                                <td>
                                    <form class="inline-form" method="post" action="<%= request.getContextPath() %>/delete-record">
                                        <input type="hidden" name="type" value="farm">
                                        <input type="hidden" name="id" value="<%= farm.getId() %>">
                                        <button class="small danger" type="submit">Delete</button>
                                    </form>
                                </td>
                            </tr>
                        <% } %>
                    </tbody>
                </table>
            </div>
        </section>

        <section class="panel wide">
            <h2>Saved Crop Cycles</h2>
            <div class="table-wrap">
                <table>
                    <thead>
                        <tr>
                            <th>Farm</th>
                            <th>Crop</th>
                            <th>Season</th>
                            <th>Revenue</th>
                            <th>Action</th>
                        </tr>
                    </thead>
                    <tbody>
                        <% if (cropCycles.isEmpty()) { %>
                            <tr><td colspan="5">No crop cycles saved yet.</td></tr>
                        <% } %>

                        <% for (CropCycle cropCycle : cropCycles) { %>
                            <tr>
                                <td><%= h(cropCycle.getFarmName()) %></td>
                                <td><%= h(cropCycle.getCropName()) %></td>
                                <td><%= h(cropCycle.getSeason()) %></td>
                                <td><%= cropCycle.getActualRevenue() %></td>
                                <td>
                                    <form class="inline-form" method="post" action="<%= request.getContextPath() %>/delete-record">
                                        <input type="hidden" name="type" value="crop">
                                        <input type="hidden" name="id" value="<%= cropCycle.getId() %>">
                                        <button class="small danger" type="submit">Delete</button>
                                    </form>
                                </td>
                            </tr>
                        <% } %>
                    </tbody>
                </table>
            </div>
        </section>

        <section class="panel wide">
            <h2>Saved Expenses</h2>
            <div class="table-wrap">
                <table>
                    <thead>
                        <tr>
                            <th>Farm</th>
                            <th>Crop</th>
                            <th>Expense</th>
                            <th>Type</th>
                            <th>Cost</th>
                            <th>Date</th>
                            <th>Action</th>
                        </tr>
                    </thead>
                    <tbody>
                        <% if (expenses.isEmpty()) { %>
                            <tr><td colspan="7">No expenses saved yet.</td></tr>
                        <% } %>

                        <% for (ExpenseRecord expense : expenses) { %>
                            <tr>
                                <td><%= h(expense.getFarmName()) %></td>
                                <td><%= h(expense.getCropName()) %></td>
                                <td><%= h(expense.getExpenseName()) %></td>
                                <td><%= h(expense.getExpenseType()) %></td>
                                <td><%= expense.getCost() %></td>
                                <td><%= expense.getExpenseDate() %></td>
                                <td>
                                    <form class="inline-form" method="post" action="<%= request.getContextPath() %>/delete-record">
                                        <input type="hidden" name="type" value="expense">
                                        <input type="hidden" name="id" value="<%= expense.getId() %>">
                                        <button class="small danger" type="submit">Delete</button>
                                    </form>
                                </td>
                            </tr>
                        <% } %>
                    </tbody>
                </table>
            </div>
        </section>

        <section class="panel wide">
            <h2>Profit / Loss</h2>
            <div class="table-wrap">
                <table>
                    <thead>
                        <tr>
                            <th>Farm</th>
                            <th>Crop</th>
                            <th>Season</th>
                            <th>Actual Revenue</th>
                            <th>Total Expense</th>
                            <th>Profit / Loss</th>
                        </tr>
                    </thead>
                    <tbody>
                        <% if (reports.isEmpty()) { %>
                            <tr><td colspan="6">No profit data available yet.</td></tr>
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
