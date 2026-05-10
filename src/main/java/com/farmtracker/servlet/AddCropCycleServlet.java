package com.farmtracker.servlet;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.farmtracker.dao.CropCycleDAO;
import com.farmtracker.model.CropCycle;

public class AddCropCycleServlet extends HttpServlet {
    private final CropCycleDAO cropCycleDAO = new CropCycleDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            CropCycle cropCycle = new CropCycle();
            cropCycle.setFarmId(Integer.parseInt(request.getParameter("farmId")));
            cropCycle.setCropName(request.getParameter("cropName"));
            cropCycle.setSeason(request.getParameter("season"));
            cropCycle.setStartDate(LocalDate.parse(request.getParameter("startDate")));
            cropCycle.setEndDate(LocalDate.parse(request.getParameter("endDate")));
            cropCycle.setExpectedRevenue(Double.parseDouble(request.getParameter("expectedRevenue")));
            cropCycle.setActualRevenue(Double.parseDouble(request.getParameter("actualRevenue")));

            cropCycleDAO.addCropCycle(cropCycle);
            redirect(response, request, "success", "Crop cycle saved successfully.");
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
