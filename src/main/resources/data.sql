INSERT INTO users (user_id, first_name, last_name, email, phone, user_name, password, gender, dob, created_at, update_at, user_role)
VALUES
    (1, 'John', 'Doe', 'john.doe@example.com', '1234567890', 'johndoe', 'password123', 'male', '1990-01-01', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'Trainee'),
    (2, 'Jane', 'Doe', 'jane.doe@example.com', '0987654321', 'janedoe', 'password456', 'female', '1995-02-02', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'Trainer'),
    (3, 'Bob', 'Smith', 'bob.smith@example.com', '1122334455', 'bobsmith', 'password789', 'male','1985-03-03', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'Admin');
