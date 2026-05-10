CREATE DATABASE IF NOT EXISTS farmtracker
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE farmtracker;

CREATE TABLE IF NOT EXISTS farm (
    id INT AUTO_INCREMENT PRIMARY KEY,
    farm_name VARCHAR(100) NOT NULL,
    owner_name VARCHAR(100) NOT NULL,
    location VARCHAR(150) NOT NULL,
    size_acres DECIMAL(10, 2) NOT NULL,
    CONSTRAINT chk_farm_size_positive CHECK (size_acres > 0)
);

CREATE TABLE IF NOT EXISTS crop (
    id INT AUTO_INCREMENT PRIMARY KEY,
    farm_id INT NOT NULL,
    crop_name VARCHAR(100) NOT NULL,
    season VARCHAR(50) NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    expected_revenue DECIMAL(12, 2) NOT NULL,
    actual_revenue DECIMAL(12, 2) NOT NULL,
    CONSTRAINT chk_crop_dates CHECK (end_date >= start_date),
    CONSTRAINT chk_crop_expected_revenue CHECK (expected_revenue >= 0),
    CONSTRAINT chk_crop_actual_revenue CHECK (actual_revenue >= 0),
    INDEX idx_crop_farm_id (farm_id),
    CONSTRAINT fk_crop_farm
        FOREIGN KEY (farm_id) REFERENCES farm(id)
        ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS expense (
    id INT AUTO_INCREMENT PRIMARY KEY,
    crop_id INT NOT NULL,
    expense_name VARCHAR(100) NOT NULL,
    expense_type VARCHAR(20) NOT NULL,
    cost DECIMAL(12, 2) NOT NULL,
    expense_date DATE NOT NULL,
    CONSTRAINT chk_expense_type CHECK (expense_type IN ('FIXED', 'VARIABLE')),
    CONSTRAINT chk_expense_cost CHECK (cost >= 0),
    INDEX idx_expense_crop_id (crop_id),
    CONSTRAINT fk_expense_crop
        FOREIGN KEY (crop_id) REFERENCES crop(id)
        ON DELETE CASCADE
);

INSERT INTO farm (farm_name, owner_name, location, size_acres)
SELECT 'Green Valley Farm', 'Student User', 'Local Village', 5.50
WHERE NOT EXISTS (
    SELECT 1 FROM farm WHERE farm_name = 'Green Valley Farm'
);

INSERT INTO crop (farm_id, crop_name, season, start_date, end_date, expected_revenue, actual_revenue)
SELECT f.id, 'Wheat', 'Rabi', '2026-01-01', '2026-04-15', 65000.00, 72000.00
FROM farm f
WHERE f.farm_name = 'Green Valley Farm'
AND NOT EXISTS (
    SELECT 1 FROM crop c WHERE c.farm_id = f.id AND c.crop_name = 'Wheat'
);

INSERT INTO expense (crop_id, expense_name, expense_type, cost, expense_date)
SELECT c.id, 'Seeds', 'VARIABLE', 8000.00, '2026-01-02'
FROM crop c
JOIN farm f ON c.farm_id = f.id
WHERE f.farm_name = 'Green Valley Farm'
AND c.crop_name = 'Wheat'
AND NOT EXISTS (
    SELECT 1 FROM expense e WHERE e.crop_id = c.id AND e.expense_name = 'Seeds'
);

INSERT INTO expense (crop_id, expense_name, expense_type, cost, expense_date)
SELECT c.id, 'Land Rent', 'FIXED', 12000.00, '2026-01-05'
FROM crop c
JOIN farm f ON c.farm_id = f.id
WHERE f.farm_name = 'Green Valley Farm'
AND c.crop_name = 'Wheat'
AND NOT EXISTS (
    SELECT 1 FROM expense e WHERE e.crop_id = c.id AND e.expense_name = 'Land Rent'
);
