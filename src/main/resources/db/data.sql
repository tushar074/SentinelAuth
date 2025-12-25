-- ============================================
-- SAMPLE SEED DATA FOR SENTINELAUTH
-- Generated: 2025-12-25 06:54:17 UTC
-- ============================================

-- ============================================
-- PERMISSIONS TABLE
-- ============================================
INSERT INTO permissions (id, name, description, created_at, updated_at) VALUES
(1, 'CREATE_USER', 'Permission to create new users', NOW(), NOW()),
(2, 'READ_USER', 'Permission to view user details', NOW(), NOW()),
(3, 'UPDATE_USER', 'Permission to update user information', NOW(), NOW()),
(4, 'DELETE_USER', 'Permission to delete users', NOW(), NOW()),
(5, 'CREATE_ROLE', 'Permission to create new roles', NOW(), NOW()),
(6, 'READ_ROLE', 'Permission to view role details', NOW(), NOW()),
(7, 'UPDATE_ROLE', 'Permission to update role information', NOW(), NOW()),
(8, 'DELETE_ROLE', 'Permission to delete roles', NOW(), NOW()),
(9, 'MANAGE_PERMISSIONS', 'Permission to manage permissions', NOW(), NOW()),
(10, 'VIEW_AUDIT_LOG', 'Permission to view audit logs', NOW(), NOW()),
(11, 'VIEW_DASHBOARD', 'Permission to access dashboard', NOW(), NOW()),
(12, 'MANAGE_TOKENS', 'Permission to manage authentication tokens', NOW(), NOW()),
(13, 'RESET_PASSWORD', 'Permission to reset user passwords', NOW(), NOW()),
(14, 'VIEW_REPORTS', 'Permission to view reports', NOW(), NOW()),
(15, 'EXPORT_DATA', 'Permission to export system data', NOW(), NOW());

-- ============================================
-- ROLES TABLE
-- ============================================
INSERT INTO roles (id, name, description, created_at, updated_at) VALUES
(1, 'SUPER_ADMIN', 'Super administrator with full system access', NOW(), NOW()),
(2, 'ADMIN', 'Administrator with management capabilities', NOW(), NOW()),
(3, 'MANAGER', 'Manager with limited administrative rights', NOW(), NOW()),
(4, 'USER', 'Regular user with basic permissions', NOW(), NOW()),
(5, 'GUEST', 'Guest user with minimal permissions', NOW(), NOW());

-- ============================================
-- ROLE_PERMISSIONS MAPPING
-- ============================================
-- SUPER_ADMIN: All permissions
INSERT INTO role_permissions (role_id, permission_id) VALUES
(1, 1), (1, 2), (1, 3), (1, 4), (1, 5), (1, 6), (1, 7), (1, 8), (1, 9), (1, 10), (1, 11), (1, 12), (1, 13), (1, 14), (1, 15);

-- ADMIN: Management and view permissions
INSERT INTO role_permissions (role_id, permission_id) VALUES
(2, 2), (2, 3), (2, 5), (2, 6), (2, 7), (2, 10), (2, 11), (2, 12), (2, 13), (2, 14), (2, 15);

-- MANAGER: Limited administrative rights
INSERT INTO role_permissions (role_id, permission_id) VALUES
(3, 2), (3, 3), (3, 6), (3, 10), (3, 11), (3, 13), (3, 14);

-- USER: Basic permissions
INSERT INTO role_permissions (role_id, permission_id) VALUES
(4, 2), (4, 3), (4, 11), (4, 14);

-- GUEST: Minimal permissions
INSERT INTO role_permissions (role_id, permission_id) VALUES
(5, 2), (5, 11);

-- ============================================
-- USERS TABLE
-- Note: Passwords should be hashed in production
-- These are example bcrypt hashes for 'password123'
-- ============================================
INSERT INTO users (id, username, email, password_hash, first_name, last_name, is_active, created_at, updated_at) VALUES
(1, 'superadmin', 'superadmin@sentinelauth.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcg7b3XeKeUxWDeS8CM7qqBstPm', 'Super', 'Admin', true, NOW(), NOW()),
(2, 'admin', 'admin@sentinelauth.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcg7b3XeKeUxWDeS8CM7qqBstPm', 'Admin', 'User', true, NOW(), NOW()),
(3, 'manager', 'manager@sentinelauth.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcg7b3XeKeUxWDeS8CM7qqBstPm', 'Manager', 'User', true, NOW(), NOW()),
(4, 'john_doe', 'john.doe@sentinelauth.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcg7b3XeKeUxWDeS8CM7qqBstPm', 'John', 'Doe', true, NOW(), NOW()),
(5, 'jane_smith', 'jane.smith@sentinelauth.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcg7b3XeKeUxWDeS8CM7qqBstPm', 'Jane', 'Smith', true, NOW(), NOW()),
(6, 'guest_user', 'guest@sentinelauth.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcg7b3XeKeUxWDeS8CM7qqBstPm', 'Guest', 'User', true, NOW(), NOW()),
(7, 'inactive_user', 'inactive@sentinelauth.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcg7b3XeKeUxWDeS8CM7qqBstPm', 'Inactive', 'Account', false, NOW(), NOW());

-- ============================================
-- USER_ROLES MAPPING
-- ============================================
INSERT INTO user_roles (user_id, role_id) VALUES
(1, 1),  -- superadmin -> SUPER_ADMIN
(2, 2),  -- admin -> ADMIN
(3, 3),  -- manager -> MANAGER
(4, 4),  -- john_doe -> USER
(5, 4),  -- jane_smith -> USER
(6, 5),  -- guest_user -> GUEST
(7, 4);  -- inactive_user -> USER

-- ============================================
-- AUDIT LOG SAMPLE ENTRIES
-- ============================================
INSERT INTO audit_logs (id, user_id, action, entity_type, entity_id, details, created_at) VALUES
(1, 1, 'LOGIN', 'USER', 1, 'User superadmin logged in successfully', DATE_SUB(NOW(), INTERVAL 2 DAY)),
(2, 2, 'CREATE', 'USER', 4, 'User john_doe created by admin', DATE_SUB(NOW(), INTERVAL 1 DAY)),
(3, 2, 'UPDATE', 'USER', 5, 'User jane_smith updated by admin', DATE_SUB(NOW(), INTERVAL 1 DAY)),
(4, 1, 'LOGIN', 'USER', 1, 'User superadmin logged in successfully', DATE_SUB(NOW(), INTERVAL 12 HOUR)),
(5, 3, 'VIEW', 'AUDIT_LOG', NULL, 'Manager viewed audit logs', NOW());

-- ============================================
-- SET AUTO_INCREMENT VALUES
-- ============================================
ALTER TABLE permissions AUTO_INCREMENT = 16;
ALTER TABLE roles AUTO_INCREMENT = 6;
ALTER TABLE users AUTO_INCREMENT = 8;
ALTER TABLE audit_logs AUTO_INCREMENT = 6;
