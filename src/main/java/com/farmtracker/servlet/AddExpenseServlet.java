package com.farmtracker.servlet;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.farmtracker.dao.ExpenseDAO;
import com.farmtracker.model.FixedExpense;
import com.farmtracker.model.VariableExpense;

public class AddExpenseServlet extends HttpServlet {
    private final ExpenseDAO expenseDAO = new ExpenseDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int cropCycleId = Integer.parseInt(request.getParameter("cropCycleId"));
            String expenseName = request.getParameter("expenseName");
            String expenseType = request.getParameter("expenseType");
            double cost = Double.parseDouble(request.getParameter("cost"));
            LocalDate expenseDate = LocalDate.parse(request.getParameter("expenseDate"));

            if ("FIXED".equalsIgnoreCase(expenseType)) {
                expenseDAO.addExpense(new FixedExpense(cropCycleId, expenseName, cost, expenseDate));
            } else {
                expenseDAO.addExpense(new VariableExpense(cropCycleId, expenseName, cost, expenseDate));
            }

            redirect(response, request, "success", "Expense saved successfully.");
        } catch (Exception exception) {
            redirect(response, request, "error", exception.getMessage());
        }
    }

    private void redirect(HttpServletResponse response, HttpServletRequest request, String key, String value)
            throws IOException {
        response.sendRedirect(request.getContextPath() + "/dashboard?" + key + "="
                + URLEncoder.encode(value, StandardCharsets.UTF_8.name()));
    }
}
