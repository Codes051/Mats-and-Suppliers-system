package com.bc.cleaninv.controller;

import com.bc.cleaninv.dao.MaterialDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Owner: Member 1 (Team Lead / Integration & Database Architect)
 *
 * Pulls summary numbers from every module's DAO. As M4 and M5 finish
 * CleanerDAO and IssuanceDAO, wire their counts in here too
 * (totalCleaners, recentIssuances) following the same pattern as
 * totalMaterials / lowStockCount below.
 */
@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {

    private final MaterialDAO materialDAO = new MaterialDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            int totalMaterials = materialDAO.countAll();
            int lowStockCount = materialDAO.findLowStock().size();

            req.setAttribute("totalMaterials", totalMaterials);
            req.setAttribute("lowStockCount", lowStockCount);
            // TODO (M4): req.setAttribute("totalCleaners", cleanerDAO.countAll());
            // TODO (M4): req.setAttribute("recentIssuances", issuanceDAO.findRecent(5));

            req.getRequestDispatcher("/WEB-INF/views/dashboard.jsp").forward(req, resp);

        } catch (SQLException e) {
            req.setAttribute("error", "Could not load dashboard statistics.");
            req.getRequestDispatcher("/WEB-INF/views/dashboard.jsp").forward(req, resp);
        }
    }
}
