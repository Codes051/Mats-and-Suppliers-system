package com.bc.cleaninv.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

/**
 * Owner: Member 5 (Reports & UI/UX Developer)
 *
 * TODO: build the 4 required reports, each as its own case in doGet
 * (or split into 4 servlets if you prefer — either is fine):
 *   ?type=inventory        -> full stock list (reuse MaterialDAO.findAll())
 *   ?type=low-stock        -> reuse MaterialDAO.findLowStock()
 *   ?type=issuance-history -> needs IssuanceDAO.findAll() with joins to
 *                              material name + cleaner name (once M4 builds it)
 *   ?type=material-usage   -> aggregate SUM(quantity_issued) GROUP BY material
 *                              from the issuances table
 *
 * Route convention:
 *   GET /reports?type=inventory
 */
@WebServlet("/reports")
public class ReportServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setAttribute("error", "Reports module not yet implemented — see TODO in ReportServlet.java");
        req.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(req, resp);
    }
}
