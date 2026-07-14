package com.bc.cleaninv.controller;

import com.bc.cleaninv.model.Material;
import com.bc.cleaninv.service.MaterialService;
import com.bc.cleaninv.service.SupplierService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet({"/materials", "/materials/delete"})
public class MaterialServlet extends HttpServlet {

    private final MaterialService materialService = new MaterialService();
    private final SupplierService supplierService = new SupplierService();

    @Override
    protected void doGet(
            HttpServletRequest req,
            HttpServletResponse resp
    ) throws ServletException, IOException {

        String path = req.getServletPath();

        try {
            if (path.endsWith("/delete")) {
                int materialId = Integer.parseInt(req.getParameter("id"));

                materialService.delete(materialId);

                resp.sendRedirect(req.getContextPath() + "/materials");
                return;
            }

            String action = req.getParameter("action");

            if ("new".equals(action)) {
                loadSuppliers(req);

                req.getRequestDispatcher(
                        "/WEB-INF/views/material-form.jsp"
                ).forward(req, resp);

                return;
            }

            if ("edit".equals(action)) {
                int materialId = Integer.parseInt(req.getParameter("id"));

                Material material = materialService.getById(materialId);

                if (material == null) {
                    resp.sendRedirect(req.getContextPath() + "/materials");
                    return;
                }

                req.setAttribute("material", material);
                loadSuppliers(req);

                req.getRequestDispatcher(
                        "/WEB-INF/views/material-form.jsp"
                ).forward(req, resp);

                return;
            }

            String keyword = req.getParameter("q");

            List<Material> materials =
                    materialService.search(keyword);

            req.setAttribute("materials", materials);
            req.setAttribute("keyword", keyword);

            req.getRequestDispatcher(
                    "/WEB-INF/views/materials.jsp"
            ).forward(req, resp);

        } catch (NumberFormatException e) {
            resp.sendRedirect(req.getContextPath() + "/materials");

        } catch (IllegalArgumentException e) {
            req.setAttribute("error", e.getMessage());
            loadMaterialList(req, resp);

        } catch (SQLException e) {
            e.printStackTrace();

            req.setAttribute(
                    "error",
                    "A database error occurred while loading materials."
            );

            loadMaterialList(req, resp);
        }
    }

    @Override
    protected void doPost(
            HttpServletRequest req,
            HttpServletResponse resp
    ) throws ServletException, IOException {

        Material material = new Material();

        material.setName(req.getParameter("name"));
        material.setDescription(req.getParameter("description"));
        material.setUnit(req.getParameter("unit"));

        try {
            material.setQuantity(
                    Integer.parseInt(req.getParameter("quantity"))
            );

            material.setReorderLevel(
                    Integer.parseInt(req.getParameter("reorderLevel"))
            );

            String supplierIdParameter =
                    req.getParameter("supplierId");

            if (supplierIdParameter != null
                    && !supplierIdParameter.isBlank()) {

                material.setSupplierId(
                        Integer.parseInt(supplierIdParameter)
                );
            }

        } catch (NumberFormatException e) {
            showFormError(
                    req,
                    resp,
                    material,
                    "Quantity, reorder level and supplier must contain valid numbers."
            );

            return;
        }

        String validationError = materialService.validate(material);

        if (validationError != null) {
            showFormError(
                    req,
                    resp,
                    material,
                    validationError
            );

            return;
        }

        try {
            String materialIdParameter =
                    req.getParameter("materialId");

            if (materialIdParameter != null
                    && !materialIdParameter.isBlank()) {

                material.setMaterialId(
                        Integer.parseInt(materialIdParameter)
                );

                materialService.update(material);

            } else {
                materialService.create(material);
            }

            resp.sendRedirect(req.getContextPath() + "/materials");

        } catch (NumberFormatException e) {
            showFormError(
                    req,
                    resp,
                    material,
                    "Invalid material ID."
            );

        } catch (SQLException e) {
            e.printStackTrace();

            showFormError(
                    req,
                    resp,
                    material,
                    "A database error occurred while saving the material."
            );
        }
    }

    private void loadSuppliers(HttpServletRequest req)
            throws SQLException {

        req.setAttribute(
                "suppliers",
                supplierService.getAllSuppliers()
        );
    }

    private void showFormError(
            HttpServletRequest req,
            HttpServletResponse resp,
            Material material,
            String errorMessage
    ) throws ServletException, IOException {

        req.setAttribute("error", errorMessage);
        req.setAttribute("material", material);

        try {
            loadSuppliers(req);
        } catch (SQLException e) {
            req.setAttribute("suppliers", List.of());
        }

        req.getRequestDispatcher(
                "/WEB-INF/views/material-form.jsp"
        ).forward(req, resp);
    }

    private void loadMaterialList(
            HttpServletRequest req,
            HttpServletResponse resp
    ) throws ServletException, IOException {

        try {
            req.setAttribute(
                    "materials",
                    materialService.search(null)
            );
        } catch (SQLException e) {
            req.setAttribute("materials", List.of());
        }

        req.getRequestDispatcher(
                "/WEB-INF/views/materials.jsp"
        ).forward(req, resp);
    }
}