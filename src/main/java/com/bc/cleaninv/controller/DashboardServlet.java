package com.bc.cleaninv.controller;

import com.bc.cleaninv.dao.MaterialDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * Owner: Member 1 (Team Lead / Integration & Database Architect)
 *
 * Pulls summary numbers from the Materials module.
 *
 * This version includes temporary detailed error handling so that
 * deployment or database problems can be diagnosed more easily.
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

            // These will be added by the relevant team members later.
            // req.setAttribute(
            //         "totalCleaners",
            //         cleanerDAO.countAll()
            // );
            //
            // req.setAttribute(
            //         "recentIssuances",
            //         issuanceDAO.findRecent(5)
            // );

            req.getRequestDispatcher(
                    "/WEB-INF/views/dashboard.jsp"
            ).forward(req, resp);

        } catch (Throwable error) {
            error.printStackTrace();

            String errorMessage =
                    "Could not load dashboard statistics: "
                            + error.getClass().getName()
                            + " - "
                            + error.getMessage();

            req.setAttribute("error", errorMessage);

            req.getRequestDispatcher(
                    "/WEB-INF/views/dashboard.jsp"
            ).forward(req, resp);
        }
    }
}