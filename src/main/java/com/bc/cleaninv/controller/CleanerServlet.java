package com.bc.cleaninv.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

/**
 * Owner: Member 4 (Cleaners & Stock Issuance Developer)
 *
 * TODO: build this exactly like MaterialServlet.java —
 *   1. Create model/Cleaner.java
 *   2. Create dao/CleanerDAO.java (mirror MaterialDAO.java)
 *   3. Create service/CleanerService.java
 *   4. Fill in doGet/doPost below
 *   5. Create views/cleaners.jsp and views/cleaner-form.jsp
 *
 * Route convention to keep:
 *   GET  /cleaners              -> list
 *   GET  /cleaners?action=new   -> add form
 *   GET  /cleaners?action=edit&id=5 -> edit form
 *   POST /cleaners               -> create/update
 *   GET  /cleaners/delete?id=5  -> delete
 */
@WebServlet({"/cleaners", "/cleaners/delete"})
public class CleanerServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setAttribute("error", "Cleaners module not yet implemented — see TODO in CleanerServlet.java");
        req.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        doGet(req, resp);
    }
}
