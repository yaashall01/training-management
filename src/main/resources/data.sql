INSERT INTO users (user_id, first_name, last_name, email, phone, user_name, password, gender, dob, created_at, update_at, user_role)
VALUES
    (UUID(), 'John', 'Doe', 'john.doe@example.com', '1234567890', 'johndoe', 'password123', 'male', '1990-01-01', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'TRAINEE'),
    (UUID(), 'Jane', 'Doe', 'jane.doe@example.com', '0987654321', 'janedoe', 'password456', 'female', '1995-02-02', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'TRAINER'),
    (UUID(), 'Bob', 'Smith', 'bob.smith@example.com', '1122334455', 'bobsmith', 'password789', 'male', '1985-03-03', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'TRAINEE'),
    (UUID(), 'Yassine', 'CHALH', 'yassinechalh9@gmail.com', '0673151392', 'yaashall', '$2a$10$GNFcjBjNw9T1jfn1P4ALRe.vol9.u2wtB9aKpd.e8fdgeBW3jyo2e', 'male', '1985-03-03', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'ADMIN'),
    (UUID(), 'Rida', 'EL Ayadi', 'reelayadi@gmail.com', '1122334455', 'bobsmith', '$2a$10$5Q52FpSIE.RDx6vW9VnlceTp3ci3ojRVl.zas450gMKsSdOtkO.mi', 'male', '1985-03-03', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'ADMIN');


INSERT INTO program (program_id, title, description, duration, content, address, fees, program_type, position) VALUES
                                                                                                                   (UUID(), 'Beginner Yoga', 'An introduction to yoga basics.', 60, 'Yoga basics content', '123 Yoga Street', 50.00, 'COURSE', 1),
                                                                                                                   (UUID(), 'Intermediate Yoga', 'Yoga for those with some experience.', 75, 'Intermediate yoga content', '123 Yoga Street', 60.00, 'COURSE', 2),
                                                                                                                   (UUID(), 'Advanced Yoga', 'Advanced yoga techniques.', 90, 'Advanced yoga content', '123 Yoga Street', 70.00, 'COURSE', 3),
                                                                                                                   (UUID(), 'Yoga for Relaxation', 'Yoga to help you relax and de-stress.', 60, 'Relaxation yoga content', '123 Yoga Street', 55.00, 'COURSE', 4),
                                                                                                                   (UUID(), 'Yoga for Flexibility', 'Improve your flexibility with yoga.', 60, 'Flexibility yoga content', '123 Yoga Street', 50.00, 'COURSE', 5),
                                                                                                                   (UUID(), 'Meditation Basics', 'Learn the basics of meditation.', 60, 'Meditation basics content', '456 Meditation Lane', 40.00, 'TRAINING', 1),
                                                                                                                   (UUID(), 'Advanced Meditation', 'Advanced techniques in meditation.', 90, 'Advanced meditation content', '456 Meditation Lane', 70.00, 'TRAINING', 2),
                                                                                                                   (UUID(), 'Spiritual Healing', 'Techniques for spiritual healing.', 90, 'Spiritual healing content', '101 Healing Road', 80.00, 'TRAINING', 4),
                                                                                                                   (UUID(), 'Mindfulness Training', 'Become more mindful and present.', 60, 'Mindfulness content', '789 Mindfulness Blvd', 50.00, 'TRAINING', 3),
                                                                                                                   (UUID(), 'Chakra Balancing', 'Balance your chakras.', 75, 'Chakra balancing content', '102 Chakra Street', 65.00, 'TRAINING', 5);
INSERT INTO review (review_id, program_id, rating, content, creation_date, updated_at) VALUES
                                                                                           (UUID(), 1, 4.8, 'Perfect introduction to yoga basics!', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                                           (UUID(), 1, 4.6, 'Great for beginners. Very calming and easy to follow.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                                           (UUID(), 2, 4.9, 'Challenging but rewarding. Perfect for intermediates.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                                           (UUID(), 2, 4.7, 'Loved the flow and the intensity.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                                           (UUID(), 3, 5.0, 'Advanced techniques explained clearly. Fantastic!', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                                           (UUID(), 3, 4.8, 'A must for advanced yogis. Learned so much.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                                           (UUID(), 4, 4.5, 'Great way to unwind and relax. Highly recommend.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                                           (UUID(), 4, 4.4, 'Very relaxing and soothing. Perfect after a long day.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                                           (UUID(), 5, 4.7, 'Helped me improve my flexibility significantly.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                                           (UUID(), 5, 4.6, 'Great focus on stretching and flexibility.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                                           (UUID(), 6, 4.8, 'Wonderful introduction to meditation.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                                           (UUID(), 6, 4.7, 'Very calming and insightful for beginners.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                                           (UUID(), 7, 4.9, 'Deep and effective advanced meditation techniques.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                                           (UUID(), 7, 4.8, 'Perfect for those looking to deepen their practice.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                                           (UUID(), 8, 4.6, 'Mindfulness made simple and practical.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                                           (UUID(), 8, 4.5, 'Great session for learning mindfulness.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                                           (UUID(), 9, 4.7, 'Transformative spiritual healing techniques.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                                           (UUID(), 9, 4.6, 'Very healing and grounding session.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                                           (UUID(), 10, 4.8, 'Balanced my chakras effectively. Amazing experience.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                                           (UUID(), 10, 4.7, 'Felt very balanced and centered after the session.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


INSERT INTO session (session_id,program_id, title, start_time, end_time, location, description) VALUES
                                                                                         (UUID(),1, 'Morning Beginner Yoga', '2024-06-02 08:00:00', '2024-06-02 09:00:00', 'Studio A', 'Start your day with gentle yoga basics.'),
                                                                                         (UUID(),1, 'Evening Beginner Yoga', '2024-06-03 18:00:00', '2024-06-03 19:00:00', 'Studio A', 'End your day with relaxing beginner yoga.'),
                                                                                         (UUID(),2, 'Intermediate Yoga Flow', '2024-06-04 10:00:00', '2024-06-04 11:15:00', 'Studio B', 'Enhance your yoga practice with this intermediate session.'),
                                                                                         (UUID(),2, 'Intermediate Vinyasa', '2024-06-05 19:00:00', '2024-06-05 20:15:00', 'Studio B', 'A dynamic flow for intermediate yogis.'),
                                                                                         (UUID(),3, 'Advanced Yoga Workshop', '2024-06-06 09:00:00', '2024-06-06 10:30:00', 'Studio C', 'Deep dive into advanced yoga techniques.'),
                                                                                         (UUID(),3, 'Power Yoga for Advanced', '2024-06-07 18:00:00', '2024-06-07 19:30:00', 'Studio C', 'A challenging power yoga session for advanced practitioners.'),
                                                                                         (UUID(),4, 'Yoga for Stress Relief', '2024-06-08 08:00:00', '2024-06-08 09:00:00', 'Studio A', 'Relax and de-stress with calming yoga.'),
                                                                                         (UUID(),4, 'Evening Relaxation Yoga', '2024-06-09 18:00:00', '2024-06-09 19:00:00', 'Studio A', 'Unwind with this relaxation-focused session.'),
                                                                                         (UUID(),5, 'Morning Flexibility Yoga', '2024-06-10 07:00:00', '2024-06-10 08:00:00', 'Studio B', 'Improve your flexibility with this morning yoga session.'),
                                                                                         (UUID(),5, 'Evening Stretch Yoga', '2024-06-11 18:00:00', '2024-06-11 19:00:00', 'Studio B', 'End your day with a flexibility-focused yoga class.'),
                                                                                         (UUID(),6, 'Intro to Meditation', '2024-06-12 09:00:00', '2024-06-12 10:00:00', 'Meditation Room 1', 'Learn the basics of meditation in this introductory session.'),
                                                                                         (UUID(),6, 'Evening Meditation Basics', '2024-06-13 19:00:00', '2024-06-13 20:00:00', 'Meditation Room 1', 'End your day with calming meditation basics.'),
                                                                                         (UUID(),7, 'Advanced Meditation Techniques', '2024-06-14 08:00:00', '2024-06-14 09:30:00', 'Meditation Room 2', 'Explore advanced meditation techniques in depth.'),
                                                                                         (UUID(),7, 'Deep Meditation Practice', '2024-06-15 18:00:00', '2024-06-15 19:30:00', 'Meditation Room 2', 'Engage in deep meditation practices.'),
                                                                                         (UUID(),8, 'Mindfulness Morning', '2024-06-16 07:00:00', '2024-06-16 08:00:00', 'Mindfulness Hall', 'Start your day with mindfulness practices.'),
                                                                                         (UUID(),8, 'Mindfulness Evening', '2024-06-17 19:00:00', '2024-06-17 20:00:00', 'Mindfulness Hall', 'End your day with mindfulness techniques.'),
                                                                                         (UUID(),9, 'Spiritual Healing Session', '2024-06-18 09:00:00', '2024-06-18 10:30:00', 'Healing Room', 'Engage in spiritual healing techniques.'),
                                                                                         (UUID(),9, 'Evening Spiritual Healing', '2024-06-19 18:00:00', '2024-06-19 19:30:00', 'Healing Room', 'Unwind with spiritual healing practices.'),
                                                                                         (UUID(),10, 'Chakra Balancing Workshop', '2024-06-20 08:00:00', '2024-06-20 09:15:00', 'Chakra Studio', 'Balance your chakras in this workshop.'),
                                                                                         (UUID(),10, 'Evening Chakra Balance', '2024-06-21 18:00:00', '2024-06-21 19:15:00', 'Chakra Studio', 'End your day with a chakra balancing session.');
