package com.bc.cleaninv.dao;

import com.bc.cleaninv.model.Supplier;
import com.bc.cleaninv.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SupplierDAO {

    public List<Supplier> findAll() throws SQLException {
        String sql = """
                SELECT supplier_id,
                       name,
                       contact_person,
                       phone,
                       email,
                       address
                FROM suppliers
                ORDER BY name
                """;

        List<Supplier> suppliers = new ArrayList<>();

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery()
        ) {
            while (resultSet.next()) {
                suppliers.add(mapSupplier(resultSet));
            }
        }

        return suppliers;
    }

    public Supplier findById(int supplierId) throws SQLException {
        String sql = """
                SELECT supplier_id,
                       name,
                       contact_person,
                       phone,
                       email,
                       address
                FROM suppliers
                WHERE supplier_id = ?
                """;

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setInt(1, supplierId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapSupplier(resultSet);
                }
            }
        }

        return null;
    }

    public void create(Supplier supplier) throws SQLException {
        String sql = """
                INSERT INTO suppliers
                    (name, contact_person, phone, email, address)
                VALUES (?, ?, ?, ?, ?)
                """;

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            setSupplierParameters(statement, supplier);
            statement.executeUpdate();
        }
    }

    public boolean update(Supplier supplier) throws SQLException {
        String sql = """
                UPDATE suppliers
                SET name = ?,
                    contact_person = ?,
                    phone = ?,
                    email = ?,
                    address = ?
                WHERE supplier_id = ?
                """;

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            setSupplierParameters(statement, supplier);
            statement.setInt(6, supplier.getSupplierId());

            return statement.executeUpdate() > 0;
        }
    }

    public boolean delete(int supplierId) throws SQLException {
        String sql = """
                DELETE FROM suppliers
                WHERE supplier_id = ?
                """;

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setInt(1, supplierId);
            return statement.executeUpdate() > 0;
        }
    }

    public List<Supplier> search(String searchTerm) throws SQLException {
        String sql = """
                SELECT supplier_id,
                       name,
                       contact_person,
                       phone,
                       email,
                       address
                FROM suppliers
                WHERE LOWER(name) LIKE LOWER(?)
                   OR LOWER(contact_person) LIKE LOWER(?)
                   OR LOWER(email) LIKE LOWER(?)
                ORDER BY name
                """;

        String pattern = "%" + searchTerm.trim() + "%";
        List<Supplier> suppliers = new ArrayList<>();

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setString(1, pattern);
            statement.setString(2, pattern);
            statement.setString(3, pattern);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    suppliers.add(mapSupplier(resultSet));
                }
            }
        }

        return suppliers;
    }

    private Supplier mapSupplier(ResultSet resultSet) throws SQLException {
        return new Supplier(
                resultSet.getInt("supplier_id"),
                resultSet.getString("name"),
                resultSet.getString("contact_person"),
                resultSet.getString("phone"),
                resultSet.getString("email"),
                resultSet.getString("address")
        );
    }

    private void setSupplierParameters(
            PreparedStatement statement,
            Supplier supplier
    ) throws SQLException {
        statement.setString(1, supplier.getName());
        statement.setString(2, supplier.getContactPerson());
        statement.setString(3, supplier.getPhone());
        statement.setString(4, supplier.getEmail());
        statement.setString(5, supplier.getAddress());
    }
}
