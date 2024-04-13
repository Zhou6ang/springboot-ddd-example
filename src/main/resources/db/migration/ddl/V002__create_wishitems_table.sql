IF NOT EXISTS (SELECT * FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME = 'wish_items')
BEGIN
    CREATE TABLE wish_items (
        id BIGINT IDENTITY(1,1) PRIMARY KEY,
        name VARCHAR(50),
        singer VARCHAR(50),
        release_time VARCHAR(50),
        website VARCHAR(255),
        user_id BIGINT,
        created_date DATETIME DEFAULT CURRENT_TIMESTAMP,
        updated_date DATETIME DEFAULT CURRENT_TIMESTAMP
    );
END;