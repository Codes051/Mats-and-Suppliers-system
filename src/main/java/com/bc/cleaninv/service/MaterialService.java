package com.bc.cleaninv.service;

import com.bc.cleaninv.dao.MaterialDAO;
import com.bc.cleaninv.model.Material;
import com.bc.cleaninv.util.ValidationUtil;

import java.sql.SQLException;
import java.util.List;

/**
 * Owner: Member 3 (Materials & Suppliers Developer)
 *
 * Business-rule layer sits between the Servlet and the DAO.
 * Keep raw SQL out of here — only validation and orchestration.
 */
public class MaterialService {

    private final MaterialDAO materialDAO = new MaterialDAO();

    public List<Material> getAll() throws SQLException {
        return materialDAO.findAll();
    }

    public List<Material> search(String keyword) throws SQLException {
        if (ValidationUtil.isBlank(keyword)) {
            return materialDAO.findAll();
        }
        return materialDAO.search(keyword);
    }

    public List<Material> getLowStock() throws SQLException {
        return materialDAO.findLowStock();
    }

    public Material getById(int id) throws SQLException {
        return materialDAO.findById(id);
    }

    /**
     * @return null if valid, otherwise a human-readable error message
     */
    public String validate(Material m) {
        if (ValidationUtil.isBlank(m.getName())) {
            return "Material name is required.";
        }
        if (m.getQuantity() < 0) {
            return "Quantity cannot be negative.";
        }
        if (m.getReorderLevel() < 0) {
            return "Reorder level cannot be negative.";
        }
        return null;
    }

    public void create(Material m) throws SQLException {
        materialDAO.insert(m);
    }

    public void update(Material m) throws SQLException {
        materialDAO.update(m);
    }

    public void delete(int id) throws SQLException {
        materialDAO.delete(id);
    }

    public int countAll() throws SQLException {
        return materialDAO.countAll();
    }
}
