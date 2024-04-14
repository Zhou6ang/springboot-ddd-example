CREATE TABLE IF NOT EXISTS artists (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    biography VARCHAR(255),
    birthDay VARCHAR(255),
    album_id BIGINT,
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
-- only for H2, for Mysql/PostgreSQL/Oracle, remove it and will create automatically
CREATE SEQUENCE IF NOT EXISTS artists_seq START WITH 1 INCREMENT BY 50;