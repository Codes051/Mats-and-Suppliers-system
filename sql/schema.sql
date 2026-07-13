-- =====================================================================
-- University Cleaning Inventory & Issuance System
-- Schema: PostgreSQL
-- Run this once against a fresh database, e.g.:
--   createdb cleaninv
--   psql -d cleaninv -f schema.sql
-- =====================================================================

DROP TABLE IF EXISTS issuances CASCADE;
DROP TABLE IF EXISTS materials CASCADE;
DROP TABLE IF EXISTS cleaners CASCADE;
DROP TABLE IF EXISTS suppliers CASCADE;
DROP TABLE IF EXISTS users CASCADE;

-- ---------------------------------------------------------------------
-- users: staff accounts, role-based access
-- ---------------------------------------------------------------------
CREATE TABLE users (
    user_id        SERIAL PRIMARY KEY,
    username       VARCHAR(50)  NOT NULL UNIQUE,
    email          VARCHAR(100) NOT NULL UNIQUE,
    password_hash  VARCHAR(100) NOT NULL,
    role           VARCHAR(20)  NOT NULL CHECK (role IN ('STOREKEEPER', 'SUPERVISOR')),
    created_at     TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- ---------------------------------------------------------------------
-- suppliers
-- ---------------------------------------------------------------------
CREATE TABLE suppliers (
    supplier_id     SERIAL PRIMARY KEY,
    name            VARCHAR(100) NOT NULL,
    contact_person  VARCHAR(100),
    phone           VARCHAR(30),
    email           VARCHAR(100),
    address         VARCHAR(255)
);

-- ---------------------------------------------------------------------
-- materials: stock items, linked to a supplier
-- ---------------------------------------------------------------------
CREATE TABLE materials (
    material_id     SERIAL PRIMARY KEY,
    name            VARCHAR(100) NOT NULL,
    description     VARCHAR(255),
    unit            VARCHAR(20)  NOT NULL DEFAULT 'unit',
    quantity        INT NOT NULL DEFAULT 0 CHECK (quantity >= 0),
    reorder_level   INT NOT NULL DEFAULT 0 CHECK (reorder_level >= 0),
    supplier_id     INT REFERENCES suppliers(supplier_id) ON DELETE SET NULL
);

-- ---------------------------------------------------------------------
-- cleaners: staff who receive issued materials
-- ---------------------------------------------------------------------
CREATE TABLE cleaners (
    cleaner_id      SERIAL PRIMARY KEY,
    full_name       VARCHAR(100) NOT NULL,
    employee_no     VARCHAR(30)  NOT NULL UNIQUE,
    department      VARCHAR(100),
    phone           VARCHAR(30)
);

-- ---------------------------------------------------------------------
-- issuances: one record per stock-out event
-- ---------------------------------------------------------------------
CREATE TABLE issuances (
    issuance_id      SERIAL PRIMARY KEY,
    material_id      INT NOT NULL REFERENCES materials(material_id) ON DELETE RESTRICT,
    cleaner_id       INT NOT NULL REFERENCES cleaners(cleaner_id)   ON DELETE RESTRICT,
    quantity_issued  INT NOT NULL CHECK (quantity_issued > 0),
    issued_by        INT REFERENCES users(user_id) ON DELETE SET NULL,
    issued_at        TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- ---------------------------------------------------------------------
-- Helpful indexes for reports and search/filter
-- ---------------------------------------------------------------------
CREATE INDEX idx_materials_supplier   ON materials(supplier_id);
CREATE INDEX idx_issuances_material   ON issuances(material_id);
CREATE INDEX idx_issuances_cleaner    ON issuances(cleaner_id);
CREATE INDEX idx_issuances_issued_at  ON issuances(issued_at);
