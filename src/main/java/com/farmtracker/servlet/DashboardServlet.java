package com.farmtracker.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.farmtracker.dao.CropCycleDAO;
import com.farmtracker.dao.ExpenseDAO;
import com.farmtracker.dao.FarmDAO;
import com.farmtracker.dao.ReportDAO;

public class DashboardServlet extends HttpServlet {
    private final FarmDAO farmDAO = new FarmDAO();
    private final CropCycleDAO cropCycleDAO = new CropCycleDAO();
    private final ExpenseDAO expenseDAO = new ExpenseDAO();
    private final ReportDAO reportDAO = new ReportDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            request.setAttribute("farms", farmDAO.getAllFarms());
            request.setAttribute("cropCycles", cropCycleDAO.getAllCropCycles());
            request.setAttribute("expenses", expenseDAO.getAllExpenses());
            request.setAttribute("reports", reportDAO.getAllReports());
        } catch (SQLException exception) {
            request.setAttribute("databaseError", exception.getMessage());
        }

        request.getRequestDispatcher("/dashboard.jsp").forward(request, response);
    }
}
