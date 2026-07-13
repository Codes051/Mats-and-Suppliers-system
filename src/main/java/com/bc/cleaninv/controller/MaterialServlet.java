package com.bc.cleaninv.controller;

import com.bc.cleaninv.model.Material;
import com.bc.cleaninv.service.MaterialService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * Owner: Member 3 (Materials & Suppliers Developer)
 *
 * URL pattern convention used across the app:
 *   GET  /materials              -> list (+ optional ?q=keyword search)
 *   GET  /materials?action=new   -> show add form
 *   GET  /materials?action=edit&id=5 -> show edit form
 *   POST /materials               -> create or update (action=create|update)
 *   GET  /materials/delete?id=5  -> delete (Supervisor only, see RoleFilter)
 *
 * Copy this pattern for SupplierServlet, CleanerServlet, IssuanceServlet.
 */
@WebServlet({"/materials", "/materials/delete"})
public class MaterialServlet extends HttpServlet {

    private final MaterialService materialService = new MaterialService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String path = req.getServletPath();

        try {
            if (path.endsWith("/delete")) {
                int id = Integer.parseInt(req.getParameter("id"));
                materialService.delete(id);
                resp.sendRedirect(req.getContextPath() + "/materials");
                return;
            }

            String action = req.getParameter("action");

            if ("new".equals(action)) {
                req.getRequestDispatcher("/WEB-INF/views/material-form.jsp").forward(req, resp);
                return;
            }

            if ("edit".equals(action)) {
                int id = Integer.parseInt(req.getParameter("id"));
                Material material = materialService.getById(id);
                req.setAttribute("material", material);
                req.getRequestDispatcher("/WEB-INF/views/material-form.jsp").forward(req, resp);
                return;
            }

            // default: list, with optional search
            String keyword = req.getParameter("q");
            List<Material> materials = materialService.search(keyword);
            req.setAttribute("materials", materials);
            req.setAttribute("keyword", keyword);
            req.getRequestDispatcher("/WEB-INF/views/materials.jsp").forward(req, resp);

        } catch (SQLException e) {
            req.setAttribute("error", "A database error occurred while loading materials.");
            req.getRequestDispatcher("/WEB-INF/views/materials.jsp").forward(req, resp);
        } catch (NumberFormatException e) {
            resp.sendRedirect(req.getContextPath() + "/materials");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        Material m = new Material();
        m.setName(req.getParameter("name"));
        m.setDescription(req.getParameter("description"));
        m.setUnit(req.getParameter("unit"));

        try {
            m.setQuantity(Integer.parseInt(req.getParameter("quantity")));
            m.setReorderLevel(Integer.parseInt(req.getParameter("reorderLevel")));
        } catch (NumberFormatException e) {
            req.setAttribute("error", "Quantity and reorder level must be whole numbers.");
            req.setAttribute("material", m);
            req.getRequestDispatcher("/WEB-INF/views/material-form.jsp").forward(req, resp);
            return;
        }

        String supplierIdParam = req.getParameter("supplierId");
        if (supplierIdParam != null && !supplierIdParam.isBlank()) {
            m.setSupplierId(Integer.parseInt(supplierIdParam));
        }

        String validationError = materialService.validate(m);
        if (validationError != null) {
            req.setAttribute("error", validationError);
            req.setAttribute("material", m);
            req.getRequestDispatcher("/WEB-INF/views/material-form.jsp").forward(req, resp);
            return;
        }

        try {
            String idParam = req.getParameter("materialId");
            if (idParam != null && !idParam.isBlank()) {
                m.setMaterialId(Integer.parseInt(idParam));
                materialService.update(m);
            } else {
                materialService.create(m);
            }
            resp.sendRedirect(req.getContextPath() + "/materials");

        } catch (SQLException e) {
            req.setAttribute("error", "A database error occurred while saving the material.");
            req.setAttribute("material", m);
            req.getRequestDispatcher("/WEB-INF/views/material-form.jsp").forward(req, resp);
        }
    }
}
