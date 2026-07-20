package com.bc.cleaninv.controller;

import com.bc.cleaninv.dao.MaterialDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Loads summary information for the dashboard.
 */
@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {

    private final MaterialDAO materialDAO = new MaterialDAO();

    @Override
    protected void doGet(
            HttpServletRequest req,
            HttpServletResponse resp
    ) throws ServletException, IOException {

        try {
            int totalMaterials = materialDAO.countAll();
            int lowStockCount = materialDAO.findLowStock().size();

            req.setAttribute("totalMaterials", totalMaterials);
            req.setAttribute("lowStockCount", lowStockCount);

            req.getRequestDispatcher(
                    "/WEB-INF/views/dashboard.jsp"
            ).forward(req, resp);

        } catch (SQLException e) {
            e.printStackTrace();

            req.setAttribute(
                    "error",
                    "Could not load dashboard statistics."
            );

            req.getRequestDispatcher(
                    "/WEB-INF/views/dashboard.jsp"
            ).forward(req, resp);
        }
    }
}