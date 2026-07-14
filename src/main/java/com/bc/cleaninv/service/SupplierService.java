package com.bc.cleaninv.service;

import com.bc.cleaninv.dao.SupplierDAO;
import com.bc.cleaninv.model.Supplier;
import com.bc.cleaninv.util.ValidationUtil;

import java.sql.SQLException;
import java.util.List;

public class SupplierService {

    private final SupplierDAO supplierDAO;

    public SupplierService() {
        this.supplierDAO = new SupplierDAO();
    }

    public List<Supplier> getAllSuppliers() throws SQLException {
        return supplierDAO.findAll();
    }

    public Supplier getSupplierById(int supplierId) throws SQLException {
        if (supplierId <= 0) {
            throw new IllegalArgumentException("Invalid supplier ID.");
        }

        return supplierDAO.findById(supplierId);
    }

    public List<Supplier> searchSuppliers(String searchTerm) throws SQLException {
        if (ValidationUtil.isBlank(searchTerm)) {
            return supplierDAO.findAll();
        }

        return supplierDAO.search(searchTerm);
    }

    public void createSupplier(Supplier supplier) throws SQLException {
        validateSupplier(supplier);
        supplierDAO.create(supplier);
    }

    public boolean updateSupplier(Supplier supplier) throws SQLException {
        if (supplier == null || supplier.getSupplierId() <= 0) {
            throw new IllegalArgumentException("Invalid supplier.");
        }

        validateSupplier(supplier);
        return supplierDAO.update(supplier);
    }

    public boolean deleteSupplier(int supplierId) throws SQLException {
        if (supplierId <= 0) {
            throw new IllegalArgumentException("Invalid supplier ID.");
        }

        return supplierDAO.delete(supplierId);
    }

    private void validateSupplier(Supplier supplier) {
        if (supplier == null) {
            throw new IllegalArgumentException("Supplier details are required.");
        }

        if (ValidationUtil.isBlank(supplier.getName())) {
            throw new IllegalArgumentException("Supplier name is required.");
        }

        if (!ValidationUtil.isBlank(supplier.getEmail())
                && !ValidationUtil.isValidEmail(supplier.getEmail())) {
            throw new IllegalArgumentException(
                    "Enter a valid supplier email address."
            );
        }

        supplier.setName(supplier.getName().trim());

        if (supplier.getContactPerson() != null) {
            supplier.setContactPerson(
                    supplier.getContactPerson().trim()
            );
        }

        if (supplier.getPhone() != null) {
            supplier.setPhone(supplier.getPhone().trim());
        }

        if (supplier.getEmail() != null) {
            supplier.setEmail(supplier.getEmail().trim());
        }

        if (supplier.getAddress() != null) {
            supplier.setAddress(supplier.getAddress().trim());
        }
    }
}
