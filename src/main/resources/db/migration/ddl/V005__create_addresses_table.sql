CREATE TABLE IF NOT EXISTS addresses (
    id INT AUTO_INCREMENT PRIMARY KEY,
    street VARCHAR(50),
    city VARCHAR(50),
    county VARCHAR(20),
    user_id BIGINT,
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- only for H2, for Mysql/PostgreSQL/Oracle, remove it and will create automatically
CREATE SEQUENCE IF NOT EXISTS ADDRESSES_SEQ START WITH 1 INCREMENT BY 50;
