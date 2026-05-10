package com.farmtracker.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.farmtracker.dao.ReportDAO;

public class ReportServlet extends HttpServlet {
    private final ReportDAO reportDAO = new ReportDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            request.setAttribute("reports", reportDAO.getAllReports());
        } catch (SQLException exception) {
            request.setAttribute("databaseError", exception.getMessage());
        }

        request.getRequestDispatcher("/reports.jsp").forward(request, response);
    }
}
