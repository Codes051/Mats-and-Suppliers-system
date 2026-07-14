package com.bc.cleaninv.controller;

import com.bc.cleaninv.model.Supplier;
import com.bc.cleaninv.service.SupplierService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * Owner: Member 3 (Materials & Suppliers Developer)
 *
 * Routes:
 *   GET  /suppliers
 *   GET  /suppliers?action=new
 *   GET  /suppliers?action=edit&id=5
 *   POST /suppliers
 *   GET  /suppliers/delete?id=5
 */
@WebServlet({"/suppliers", "/suppliers/delete"})
public class SupplierServlet extends HttpServlet {

    private final SupplierService supplierService = new SupplierService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String path = req.getServletPath();

        try {
            if (path.endsWith("/delete")) {
                int supplierId = Integer.parseInt(req.getParameter("id"));
                supplierService.deleteSupplier(supplierId);

                resp.sendRedirect(req.getContextPath() + "/suppliers");
                return;
            }

            String action = req.getParameter("action");

            if ("new".equals(action)) {
                req.getRequestDispatcher("/WEB-INF/views/supplier-form.jsp")
                        .forward(req, resp);
                return;
            }

            if ("edit".equals(action)) {
                int supplierId = Integer.parseInt(req.getParameter("id"));
                Supplier supplier =
                        supplierService.getSupplierById(supplierId);

                if (supplier == null) {
                    resp.sendRedirect(req.getContextPath() + "/suppliers");
                    return;
                }

                req.setAttribute("supplier", supplier);
                req.getRequestDispatcher("/WEB-INF/views/supplier-form.jsp")
                        .forward(req, resp);
                return;
            }

            String keyword = req.getParameter("q");

            List<Supplier> suppliers =
                    supplierService.searchSuppliers(keyword);

            req.setAttribute("suppliers", suppliers);
            req.setAttribute("keyword", keyword);

            req.getRequestDispatcher("/WEB-INF/views/suppliers.jsp")
                    .forward(req, resp);

        } catch (NumberFormatException e) {
            resp.sendRedirect(req.getContextPath() + "/suppliers");

        } catch (IllegalArgumentException e) {
            req.setAttribute("error", e.getMessage());
            loadSupplierList(req, resp);

        } catch (SQLException e) {
            req.setAttribute(
                    "error",
                    "A database error occurred while loading suppliers."
            );
            loadSupplierList(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        Supplier supplier = new Supplier();

        supplier.setName(req.getParameter("name"));
        supplier.setContactPerson(req.getParameter("contactPerson"));
        supplier.setPhone(req.getParameter("phone"));
        supplier.setEmail(req.getParameter("email"));
        supplier.setAddress(req.getParameter("address"));

        String supplierIdParameter = req.getParameter("supplierId");

        try {
            if (supplierIdParameter != null
                    && !supplierIdParameter.isBlank()) {

                supplier.setSupplierId(
                        Integer.parseInt(supplierIdParameter)
                );

                supplierService.updateSupplier(supplier);
            } else {
                supplierService.createSupplier(supplier);
            }

            resp.sendRedirect(req.getContextPath() + "/suppliers");

        } catch (NumberFormatException e) {
            req.setAttribute("error", "Invalid supplier ID.");
            req.setAttribute("supplier", supplier);

            req.getRequestDispatcher("/WEB-INF/views/supplier-form.jsp")
                    .forward(req, resp);

        } catch (IllegalArgumentException e) {
            req.setAttribute("error", e.getMessage());
            req.setAttribute("supplier", supplier);

            req.getRequestDispatcher("/WEB-INF/views/supplier-form.jsp")
                    .forward(req, resp);

        } catch (SQLException e) {
            req.setAttribute(
                    "error",
                    "A database error occurred while saving the supplier."
            );
            req.setAttribute("supplier", supplier);

            req.getRequestDispatcher("/WEB-INF/views/supplier-form.jsp")
                    .forward(req, resp);
        }
    }

    private void loadSupplierList(
            HttpServletRequest req,
            HttpServletResponse resp
    ) throws ServletException, IOException {

        try {
            req.setAttribute(
                    "suppliers",
                    supplierService.getAllSuppliers()
            );
        } catch (SQLException secondaryException) {
            req.setAttribute("suppliers", List.of());
        }

        req.getRequestDispatcher("/WEB-INF/views/suppliers.jsp")
                .forward(req, resp);
    }
}
