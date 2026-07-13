package com.bc.cleaninv.dao;

import com.bc.cleaninv.model.Material;
import com.bc.cleaninv.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Owner: Member 3 (Materials & Suppliers Developer)
 *
 * This class is the reference pattern for the other DAOs
 * (SupplierDAO, CleanerDAO, IssuanceDAO) — same shape:
 * findAll / findById / insert / update / delete, each opening
 * and closing its own connection via try-with-resources.
 */
public class MaterialDAO {

    private static final String BASE_SELECT =
            "SELECT m.*, s.name AS supplier_name " +
            "FROM materials m LEFT JOIN suppliers s ON m.supplier_id = s.supplier_id";

    public List<Material> findAll() throws SQLException {
        List<Material> list = new ArrayList<>();
        String sql = BASE_SELECT + " ORDER BY m.name";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(mapRow(rs));
            }
        }
        return list;
    }

    /** Search/filter by name (case-insensitive partial match). */
    public List<Material> search(String keyword) throws SQLException {
        List<Material> list = new ArrayList<>();
        String sql = BASE_SELECT + " WHERE LOWER(m.name) LIKE LOWER(?) ORDER BY m.name";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + keyword + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRow(rs));
                }
            }
        }
        return list;
    }

    /** Used by the Dashboard and the Low-Stock report. */
    public List<Material> findLowStock() throws SQLException {
        List<Material> list = new ArrayList<>();
        String sql = BASE_SELECT + " WHERE m.quantity <= m.reorder_level ORDER BY m.name";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(mapRow(rs));
            }
        }
        return list;
    }

    public Material findById(int id) throws SQLException {
        String sql = BASE_SELECT + " WHERE m.material_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        }
        return null;
    }

    public void insert(Material m) throws SQLException {
        String sql = "INSERT INTO materials (name, description, unit, quantity, reorder_level, supplier_id) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, m.getName());
            ps.setString(2, m.getDescription());
            ps.setString(3, m.getUnit());
            ps.setInt(4, m.getQuantity());
            ps.setInt(5, m.getReorderLevel());
            if (m.getSupplierId() != null) {
                ps.setInt(6, m.getSupplierId());
            } else {
                ps.setNull(6, Types.INTEGER);
            }
            ps.executeUpdate();
        }
    }

    public void update(Material m) throws SQLException {
        String sql = "UPDATE materials SET name = ?, description = ?, unit = ?, " +
                "quantity = ?, reorder_level = ?, supplier_id = ? WHERE material_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, m.getName());
            ps.setString(2, m.getDescription());
            ps.setString(3, m.getUnit());
            ps.setInt(4, m.getQuantity());
            ps.setInt(5, m.getReorderLevel());
            if (m.getSupplierId() != null) {
                ps.setInt(6, m.getSupplierId());
            } else {
                ps.setNull(6, Types.INTEGER);
            }
            ps.setInt(7, m.getMaterialId());
            ps.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM materials WHERE material_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    /** Count used by the Dashboard. */
    public int countAll() throws SQLException {
        String sql = "SELECT COUNT(*) FROM materials";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            return rs.next() ? rs.getInt(1) : 0;
        }
    }

    private Material mapRow(ResultSet rs) throws SQLException {
        Material m = new Material(
                rs.getInt("material_id"),
                rs.getString("name"),
                rs.getString("description"),
                rs.getString("unit"),
                rs.getInt("quantity"),
                rs.getInt("reorder_level"),
                (Integer) rs.getObject("supplier_id")
        );
        m.setSupplierName(rs.getString("supplier_name"));
        return m;
    }
}
