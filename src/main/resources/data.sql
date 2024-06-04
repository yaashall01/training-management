INSERT INTO users (user_id, first_name, last_name, email, phone, user_name, password, gender, dob, created_at, update_at, user_role)
VALUES
    (UUID(), 'John', 'Doe', 'john.doe@example.com', '1234567890', 'johndoe', 'password123', 'male', '1990-01-01', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'TRAINEE'),
    (UUID(), 'Jane', 'Doe', 'jane.doe@example.com', '0987654321', 'janedoe', 'password456', 'female', '1995-02-02', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'TRAINER'),
    (UUID(), 'Bob', 'Smith', 'bob.smith@example.com', '1122334455', 'bobsmith', 'password789', 'male', '1985-03-03', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'ADMIN');


INSERT INTO program (program_id, title, description, duration, content, address, fees, program_type, position) VALUES
                                                                                                                   (UUID(), 'Beginner Yoga', 'An introduction to yoga basics.', 60, 'Yoga basics content', '123 Yoga Street', 50.00, 'COURSE', 1),
                                                                                                                   (UUID(), 'Intermediate Yoga', 'Yoga for those with some experience.', 75, 'Intermediate yoga content', '123 Yoga Street', 60.00, 'COURSE', 2),
                                                                                                                   (UUID(), 'Advanced Yoga', 'Advanced yoga techniques.', 90, 'Advanced yoga content', '123 Yoga Street', 70.00, 'COURSE', 3),
                                                                                                                   (UUID(), 'Yoga for Relaxation', 'Yoga to help you relax and de-stress.', 60, 'Relaxation yoga content', '123 Yoga Street', 55.00, 'COURSE', 4),
                                                                                                                   (UUID(), 'Yoga for Flexibility', 'Improve your flexibility with yoga.', 60, 'Flexibility yoga content', '123 Yoga Street', 50.00, 'COURSE', 5),
                                                                                                                   (UUID(), 'Meditation Basics', 'Learn the basics of meditation.', 60, 'Meditation basics content', '456 Meditation Lane', 40.00, 'TRAINING', 1),
                                                                                                                   (UUID(), 'Advanced Meditation', 'Advanced techniques in meditation.', 90, 'Advanced meditation content', '456 Meditation Lane', 70.00, 'TRAINING', 2),
                                                                                                                   (UUID(), 'Mindfulness Training', 'Become more mindful and present.', 60, 'Mindfulness content', '789 Mindfulness Blvd', 50.00, 'TRAINING', 3),
                                                                                                                   (UUID(), 'Spiritual Healing', 'Techniques for spiritual healing.', 90, 'Spiritual healing content', '101 Healing Road', 80.00, 'TRAINING', 4),
                                                                                                                   (UUID(), 'Chakra Balancing', 'Balance your chakras.', 75, 'Chakra balancing content', '102 Chakra Street', 65.00, 'TRAINING', 5);



