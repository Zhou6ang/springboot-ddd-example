CREATE TABLE IF NOT EXISTS albums (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255),
    price DECIMAL(10, 2),
    user_id VARCHAR(100),
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
-- only for H2, for Mysql/PostgreSQL/Oracle, remove it and will create automatically
CREATE SEQUENCE IF NOT EXISTS albums_seq START WITH 1 INCREMENT BY 50;