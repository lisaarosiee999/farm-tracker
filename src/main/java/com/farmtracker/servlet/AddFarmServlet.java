package com.farmtracker.servlet;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.farmtracker.dao.FarmDAO;
import com.farmtracker.model.Farm;

public class AddFarmServlet extends HttpServlet {
    private final FarmDAO farmDAO = new FarmDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Farm farm = new Farm(
                    request.getParameter("farmName"),
                    request.getParameter("ownerName"),
                    request.getParameter("location"),
                    Double.parseDouble(request.getParameter("sizeInAcres")));
            farmDAO.addFarm(farm);
            redirect(response, request, "success", "Farm saved successfully.");
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
