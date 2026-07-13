package com.bc.cleaninv.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

/**
 * Owner: Member 3 (Materials & Suppliers Developer)
 *
 * TODO: build this exactly like MaterialServlet.java —
 *   1. Create model/Supplier.java (mirror Material.java's shape)
 *   2. Create dao/SupplierDAO.java (mirror MaterialDAO.java's findAll/insert/update/delete)
 *   3. Create service/SupplierService.java (mirror MaterialService.java's validate())
 *   4. Fill in doGet/doPost below the same way MaterialServlet does it
 *   5. Create views/suppliers.jsp and views/supplier-form.jsp (copy materials.jsp / material-form.jsp and rename fields)
 *
 * Route convention to keep:
 *   GET  /suppliers              -> list
 *   GET  /suppliers?action=new   -> add form
 *   GET  /suppliers?action=edit&id=5 -> edit form
 *   POST /suppliers               -> create/update
 *   GET  /suppliers/delete?id=5  -> delete
 */
@WebServlet({"/suppliers", "/suppliers/delete"})
public class SupplierServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setAttribute("error", "Suppliers module not yet implemented — see TODO in SupplierServlet.java");
        req.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        doGet(req, resp);
    }
}
