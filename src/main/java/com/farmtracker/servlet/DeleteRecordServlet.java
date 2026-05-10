package com.farmtracker.servlet;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.farmtracker.dao.CropCycleDAO;
import com.farmtracker.dao.ExpenseDAO;
import com.farmtracker.dao.FarmDAO;

public class DeleteRecordServlet extends HttpServlet {
    private final FarmDAO farmDAO = new FarmDAO();
    private final CropCycleDAO cropCycleDAO = new CropCycleDAO();
    private final ExpenseDAO expenseDAO = new ExpenseDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String type = request.getParameter("type");
            int id = Integer.parseInt(request.getParameter("id"));

            if ("farm".equals(type)) {
                farmDAO.deleteFarm(id);
            } else if ("crop".equals(type)) {
                cropCycleDAO.deleteCropCycle(id);
            } else if ("expense".equals(type)) {
                expenseDAO.deleteExpense(id);
            } else {
                throw new IllegalArgumentException("Unknown record type.");
            }

            redirect(response, request, "success", "Record deleted successfully.");
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
