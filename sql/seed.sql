-- =====================================================================
-- Demo / test seed data. Run AFTER schema.sql.
--   psql -d cleaninv -f seed.sql
-- Password for both demo users below is: Password123!
-- (hash generated with jBCrypt, gensalt default rounds)
-- =====================================================================

INSERT INTO users (username, email, password_hash, role) VALUES
('admin',   'admin@cleaninv.local',   '$2a$10$Dow1p8lRj6EoT0e0j0j0OeYzL8b1p3T4qk2r7u9m1r0aQe0Q3d9M6', 'SUPERVISOR'),
('storekeeper1', 'store1@cleaninv.local', '$2a$10$Dow1p8lRj6EoT0e0j0j0OeYzL8b1p3T4qk2r7u9m1r0aQe0Q3d9M6', 'STOREKEEPER');
-- NOTE: replace these hashes by actually registering through the app once
-- RegisterServlet is working — the hash above is a placeholder format
-- example, not guaranteed to validate. Safer to register fresh accounts.

INSERT INTO suppliers (name, contact_person, phone, email, address) VALUES
('CleanCo Supplies',      'Thabo Mokoena',  '011 555 0101', 'sales@cleanco.co.za',   '12 Industrial Rd, Johannesburg'),
('Sparkle Distributors',  'Anna van Wyk',   '021 555 0202', 'orders@sparkle.co.za',  '45 Main St, Cape Town'),
('HygienePlus',           'Sipho Dlamini',  '031 555 0303', 'info@hygieneplus.co.za','8 Harbour Ave, Durban');

INSERT INTO materials (name, description, unit, quantity, reorder_level, supplier_id) VALUES
('All-Purpose Cleaner',   'General surface cleaner, 5L',        'bottle', 40, 10, 1),
('Mop Head',              'Replacement microfiber mop head',    'unit',   15, 5,  2),
('Bin Liners (Large)',    'Heavy duty black bin liners, roll of 50', 'roll', 60, 15, 1),
('Toilet Paper (Case)',   'Case of 40 rolls, 2-ply',             'case',  8,  10, 3),
('Disinfectant Spray',    'Multi-surface disinfectant, 750ml',   'bottle', 25, 8,  3),
('Rubber Gloves (Pair)',  'Heavy duty cleaning gloves, size M',  'pair',  50, 20, 2);

INSERT INTO cleaners (full_name, employee_no, department, phone) VALUES
('Nomvula Khumalo', 'EMP-1001', 'Residence A', '082 111 2222'),
('Johan Botha',     'EMP-1002', 'Main Campus', '083 222 3333'),
('Precious Nkosi',  'EMP-1003', 'Library',     '084 333 4444');

-- Sample issuance history
INSERT INTO issuances (material_id, cleaner_id, quantity_issued, issued_by, issued_at) VALUES
(1, 1, 5, 1, CURRENT_TIMESTAMP - INTERVAL '5 days'),
(3, 2, 10, 1, CURRENT_TIMESTAMP - INTERVAL '3 days'),
(5, 3, 3, 2, CURRENT_TIMESTAMP - INTERVAL '1 days');

-- Reflect those issuances in stock on hand
UPDATE materials SET quantity = quantity - 5  WHERE material_id = 1;
UPDATE materials SET quantity = quantity - 10 WHERE material_id = 3;
UPDATE materials SET quantity = quantity - 3  WHERE material_id = 5;
