INSERT INTO users (user_id, first_name, last_name, email, phone, user_name, password, gender, dob, created_at, update_at, user_role)
VALUES
    (UUID(), 'John', 'Doe', 'john.doe@example.com', '1234567890', 'johndoe', 'password123', 'male', '1990-01-01', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'Trainee'),
    (UUID(), 'Jane', 'Doe', 'jane.doe@example.com', '0987654321', 'janedoe', 'password456', 'female', '1995-02-02', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'Trainer'),
    (UUID(), 'Bob', 'Smith', 'bob.smith@example.com', '1122334455', 'bobsmith', 'password789', 'male','1985-03-03', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'Admin');


-- Yoga Programs
INSERT INTO program (program_id, title, description, duration, content, address, fees, program_type, position)
VALUES
    (UUID(), 'Beginner Yoga', 'An introduction to yoga basics.', 60, 'Yoga basics content', '123 Yoga Street', 50.00, 'COURSE', 1),
    (UUID(), 'Intermediate Yoga', 'Yoga for those with some experience.', 75, 'Intermediate yoga content', '123 Yoga Street', 60.00, 'COURSE', 2),
    (UUID(), 'Advanced Yoga', 'Advanced yoga techniques.', 90, 'Advanced yoga content', '123 Yoga Street', 70.00, 'COURSE', 3),
    (UUID(), 'Yoga for Relaxation', 'Yoga to help you relax and de-stress.', 60, 'Relaxation yoga content', '123 Yoga Street', 55.00, 'COURSE', 4),
    (UUID(), 'Yoga for Flexibility', 'Improve your flexibility with yoga.', 60, 'Flexibility yoga content', '123 Yoga Street', 50.00, 'COURSE', 5);

-- Spiritual Training Programs
INSERT INTO program (program_id, title, description, duration, content, address, fees, program_type, position)
VALUES
    (UUID(), 'Meditation Basics', 'Learn the basics of meditation.', 60, 'Meditation basics content', '456 Meditation Lane', 40.00, 'TRAINING', 1),
    (UUID(), 'Advanced Meditation', 'Advanced techniques in meditation.', 90, 'Advanced meditation content', '456 Meditation Lane', 70.00, 'TRAINING', 2),
    (UUID(), 'Mindfulness Training', 'Become more mindful and present.', 60, 'Mindfulness content', '789 Mindfulness Blvd', 50.00, 'TRAINING', 1),
    (UUID(), 'Spiritual Healing', 'Techniques for spiritual healing.', 90, 'Spiritual healing content', '101 Healing Road', 80.00, 'TRAINING', 1),
    (UUID(), 'Chakra Balancing', 'Balance your chakras.', 75, 'Chakra balancing content', '102 Chakra Street', 65.00, 'TRAINING', 1);



INSERT INTO review (program_id, rating, content, creation_date) VALUES
                                                                    (1, 4.7, 'Excellent content and well structured.', CURRENT_TIMESTAMP),
                                                                    (1, 4.5, 'Great instructor support and comprehensive material.', CURRENT_TIMESTAMP),
                                                                    (2, 4.9, 'The depth of Java covered was perfect for my needs.', CURRENT_TIMESTAMP),
                                                                    (2, 4.3, 'Good course but needed more practical examples.', CURRENT_TIMESTAMP),
                                                                    (3, 5.0, 'Best course for beginners in frontend development!', CURRENT_TIMESTAMP),
                                                                    (3, 4.8, 'Very engaging content and hands-on projects.', CURRENT_TIMESTAMP),
                                                                    (4, 4.2, 'Detailed backend concepts were well explained.', CURRENT_TIMESTAMP),
                                                                    (4, 4.0, 'Good, but could include more on modern frameworks.', CURRENT_TIMESTAMP),
                                                                    (5, 4.9, 'This full stack course has everything you need.', CURRENT_TIMESTAMP),
                                                                    (5, 4.7, 'Very comprehensive from start to finish.', CURRENT_TIMESTAMP),
                                                                    (6, 4.5, 'Data science concepts were clear and applicable.', CURRENT_TIMESTAMP),
                                                                    (6, 4.3, 'Great data sets to work with but needs more tools coverage.', CURRENT_TIMESTAMP),
                                                                    (7, 4.6, 'I now feel confident to build my own apps.', CURRENT_TIMESTAMP),
                                                                    (7, 4.4, 'Well-paced and very informative.', CURRENT_TIMESTAMP),
                                                                    (8, 4.8, 'Cybersecurity made interesting and practical.', CURRENT_TIMESTAMP),
                                                                    (8, 4.6, 'Essential for anyone starting in security.', CURRENT_TIMESTAMP),
                                                                    (9, 4.5, 'Cloud computing basics covered thoroughly.', CURRENT_TIMESTAMP),
                                                                    (9, 4.3, 'Perfect starter kit for cloud enthusiasts.', CURRENT_TIMESTAMP),
                                                                    (10, 4.7, 'Advanced databases were demystified.', CURRENT_TIMESTAMP),
                                                                    (10, 4.5, 'Highly recommend for database professionals.', CURRENT_TIMESTAMP);

-- Initialize the database with 10 training programs

