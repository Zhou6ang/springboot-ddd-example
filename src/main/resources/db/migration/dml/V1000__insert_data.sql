INSERT INTO users (id, name, email, gender, phone)
VALUES (NEXT VALUE FOR USERS_SEQ, 'John Doe', 'john@example.com', 'Male', '1234567890'),
       (NEXT VALUE FOR USERS_SEQ, 'Jane Doe', 'jane@example.com', 'Female', '0987654321');