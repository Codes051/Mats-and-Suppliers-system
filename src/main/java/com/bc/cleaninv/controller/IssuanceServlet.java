package com.bc.cleaninv.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

/**
 * Owner: Member 4 (Cleaners & Stock Issuance Developer)
 *
 * This is the most important business-logic module in the whole system —
 * see Phase 4 of the SDLC document.
 *
 * TODO:
 *   1. Create model/Issuance.java
 *   2. Create dao/IssuanceDAO.java (insert issuance row, list history with joins)
 *   3. Create service/IssuanceService.java — THIS is where the transaction lives:
 *
 *        Connection conn = DBConnection.getConnection();
 *        try {
 *            conn.setAutoCommit(false);
 *            Material material = materialDAO.findById(materialId); // check stock
 *            if (material.getQuantity() < requestedQty) {
 *                throw new IllegalStateException("Not enough stock available.");
 *            }
 *            issuanceDAO.insert(issuance, conn);           // pass the same conn
 *            materialDAO.deductStock(materialId, requestedQty, conn); // same conn
 *            conn.commit();
 *        } catch (Exception e) {
 *            conn.rollback();
 *            throw e;
 *        } finally {
 *            conn.setAutoCommit(true);
 *            conn.close();
 *        }
 *
 *      Note: MaterialDAO currently opens its own connection per call — for
 *      this to work you'll need overloaded DAO methods that accept an
 *      existing Connection, so both operations share one transaction.
 *
 *   4. Fill in doGet/doPost below
 *   5. Create views/issuances.jsp (history) and views/issuance-form.jsp (issue form)
 */
@WebServlet({"/issuances"})
public class IssuanceServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setAttribute("error", "Stock Issuance module not yet implemented — see TODO in IssuanceServlet.java");
        req.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        doGet(req, resp);
    }
}
