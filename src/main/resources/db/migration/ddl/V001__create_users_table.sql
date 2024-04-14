CREATE TABLE IF NOT EXISTS users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50),
    email VARCHAR(50),
    gender VARCHAR(20),
    phone VARCHAR(50),
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- only for H2, for Mysql/PostgreSQL/Oracle, remove it and will create automatically
CREATE SEQUENCE IF NOT EXISTS users_seq START WITH 1 INCREMENT BY 50;
