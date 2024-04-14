CREATE TABLE IF NOT EXISTS wish_items (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50),
    singer VARCHAR(50),
    release_time VARCHAR(50),
    website VARCHAR(255),
    user_id BIGINT,
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- only for H2, for Mysql/PostgreSQL/Oracle, remove it and will create automatically
CREATE SEQUENCE IF NOT EXISTS wish_items_seq START WITH 1 INCREMENT BY 50;