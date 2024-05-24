-- Insert Program 1
INSERT INTO program (title, description, duration, content, fees, program_type, position)
VALUES ('Software Development Fundamentals', 'This program covers the basics of software development...', 24, '...', 1000.00, 'TRAINING', 1);

-- Insert Program 2 (with prerequisite)
INSERT INTO program (title, description, duration, content, fees, program_type, position, prerequisite_id)
VALUES ('Java Programming', 'Learn the fundamentals of Java...', 16, '...', 1500.00, 'COURSE', 2, 1);

-- Insert Program 3 (different program type)
INSERT INTO program (title, description, duration, content, fees, program_type, position)
VALUES ('Data Analysis with Python', 'Master data analysis skills using Python...', 20, '...', 1200.00, 'COURSE', 3);


-- Insert Review 1 for Program 1
INSERT INTO review (program_id, rating, content, creation_date)
VALUES (1, 4.5, 'Excellent program for beginners!', NOW());

-- Insert Review 2 for Program 2
INSERT INTO review (program_id, rating, content, creation_date)
VALUES (2, 5.0, 'Java program is very well structured!', NOW());

-- Insert Review 3 for Program 3 (multiple reviews for a program)
INSERT INTO review (program_id, rating, content, creation_date)
VALUES (3, 4.0, 'Great introduction to data analysis!', NOW());

-- Insert Review 4 for Program 3
INSERT INTO review (program_id, rating, content, creation_date)
VALUES (3, 4.8, 'Learned a lot of useful techniques!', NOW());


INSERT INTO review (program_id, rating, content, creation_date)
VALUES (3, 5.0, 'This program exceeded my expectations! The techniques were easy to follow and highly effective.', NOW());

-- Adding a moderately positive review
INSERT INTO review (program_id, rating, content, creation_date)
VALUES (3, 4.5, 'Good course with solid advice. Some sections were a bit rushed, but overall very useful!', NOW());

-- Adding a detailed positive review
INSERT INTO review (program_id, rating, content, creation_date)
VALUES (3, 4.0, 'The program is quite comprehensive and covers all the bases. Would appreciate more in-depth examples in future revisions.', NOW());

-- Adding a neutral review
INSERT INTO review (program_id, rating, content, creation_date)
VALUES (3, 1, 'Decent course but not as detailed as I had hoped. Great for beginners though!', NOW());

-- Adding a mixed feedback review
INSERT INTO review (program_id, rating, content, creation_date)
VALUES (3, 2, 'Some excellent sections but some others not so much. The presentation style could be improved.', NOW());

-- Adding a critical review
INSERT INTO review (program_id, rating, content, creation_date)
VALUES (3, 2, 'I found this program to be quite basic and lacking in advanced techniques. Not for those who have prior experience.', NOW());




INSERT INTO program (title, description, duration, content, fees, program_type, position)
VALUES
    ('Data Analysis with Python', 'Learn advanced data analysis techniques using Python libraries like pandas and NumPy.', 30, 'Includes data cleaning, statistical analysis, and visualization.', 1500.00, 'COURSE', 1),
    ('Introduction to Machine Learning', 'Explore fundamental machine learning concepts and algorithms.', 25, 'Covers supervised and unsupervised learning, regression, and classification.', 1700.00, 'COURSE', 2),
    ('Web Development with React', 'Develop dynamic web applications using React JS.', 20, 'Focuses on components, state management, and hooks.', 1350.00, 'COURSE', 3),
    ('Cloud Computing Essentials', 'An overview of cloud services, focusing on AWS, Azure, and Google Cloud.', 15, 'Learn to deploy scalable applications in the cloud.', 1600.00, 'COURSE', 4),
    ('Blockchain Basics', 'Introduction to blockchain technology and its applications.', 18, 'Includes cryptocurrency principles, smart contracts, and Ethereum.', 1800.00, 'COURSE', 5),
    ('Advanced SQL Techniques', 'Master complex SQL queries and database optimization.', 12, 'Topics include subqueries, joins, indexing, and performance tuning.', 1250.00, 'COURSE', 6),
    ('Cybersecurity Fundamentals', 'Learn to protect systems and networks against cyber threats.', 22, 'Covers encryption, network security, and ethical hacking.', 1900.00, 'COURSE', 7),
    ('Artificial Intelligence with Python', 'Explore AI techniques using Python.', 28, 'Study neural networks, natural language processing, and computer vision.', 2100.00, 'COURSE', 8),
    ('Project Management Principles', 'Foundations of managing and leading projects in tech.', 16, 'Includes methodologies like Agile and Scrum.', 1300.00, 'COURSE', 9),
    ('Data Visualization with Tableau', 'Learn to create impactful visualizations and dashboards.', 14, 'Covers data import, visualization best practices, and interactive dashboards.', 1450.00, 'COURSE', 10);