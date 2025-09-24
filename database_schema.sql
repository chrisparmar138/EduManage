-- Drop tables in reverse order of creation to avoid foreign key constraint errors
DROP TABLE IF EXISTS salaries CASCADE;
DROP TABLE IF EXISTS grades CASCADE;
DROP TABLE IF EXISTS attendance CASCADE;
DROP TABLE IF EXISTS fees CASCADE;
DROP TABLE IF EXISTS enrollments CASCADE;
DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS courses CASCADE;
DROP TABLE IF EXISTS teachers CASCADE;
DROP TABLE IF EXISTS students CASCADE;


-- Create Teachers Table
CREATE TABLE teachers (
                          teacher_id SERIAL PRIMARY KEY,
                          first_name VARCHAR(50) NOT NULL,
                          last_name VARCHAR(50) NOT NULL,
                          email VARCHAR(100) UNIQUE NOT NULL,
                          hire_date DATE NOT NULL
);

-- Create Courses Table
CREATE TABLE courses (
                         course_id SERIAL PRIMARY KEY,
                         course_name VARCHAR(100) NOT NULL,
                         course_code VARCHAR(20) UNIQUE NOT NULL,
                         credits INT,
                         teacher_id INT REFERENCES teachers(teacher_id)
);

-- Create Students Table
CREATE TABLE students (
                          student_id SERIAL PRIMARY KEY,
                          first_name VARCHAR(50) NOT NULL,
                          last_name VARCHAR(50) NOT NULL,
                          date_of_birth DATE NOT NULL,
                          email VARCHAR(100) UNIQUE NOT NULL,
                          admission_date DATE
);

-- Create Users Table with CASCADING DELETES
CREATE TABLE users (
                       user_id SERIAL PRIMARY KEY,
                       username VARCHAR(50) UNIQUE NOT NULL,
                       password VARCHAR(255) NOT NULL,
                       role VARCHAR(20) NOT NULL,
                       student_id INT UNIQUE REFERENCES students(student_id) ON DELETE CASCADE,
                       teacher_id INT UNIQUE REFERENCES teachers(teacher_id) ON DELETE CASCADE
);

-- Create Enrollments Table
CREATE TABLE enrollments (
                             enrollment_id SERIAL PRIMARY KEY,
                             student_id INT NOT NULL REFERENCES students(student_id),
                             course_id INT NOT NULL REFERENCES courses(course_id),
                             enrollment_date DATE NOT NULL,
                             UNIQUE(student_id, course_id)
);

-- Create Grades Table
CREATE TABLE grades (
                        grade_id SERIAL PRIMARY KEY,
                        enrollment_id INT NOT NULL REFERENCES enrollments(enrollment_id),
                        grade NUMERIC(5, 2),
                        date_recorded DATE NOT NULL
);

-- Create Attendance Table
CREATE TABLE attendance (
                            attendance_id SERIAL PRIMARY KEY,
                            enrollment_id INT NOT NULL REFERENCES enrollments(enrollment_id),
                            attendance_date DATE NOT NULL,
                            status VARCHAR(20) NOT NULL -- e.g., PRESENT, ABSENT
);

-- Create Fees Table
CREATE TABLE fees (
                      fee_id SERIAL PRIMARY KEY,
                      student_id INT NOT NULL REFERENCES students(student_id),
                      description VARCHAR(255),
                      amount NUMERIC(10, 2) NOT NULL,
                      due_date DATE NOT NULL,
                      status VARCHAR(20) NOT NULL, -- e.g., PAID, UNPAID
                      payment_date DATE
);

-- Create Salaries Table
CREATE TABLE salaries (
                          salary_id SERIAL PRIMARY KEY,
                          teacher_id INT NOT NULL REFERENCES teachers(teacher_id),
                          description VARCHAR(255),
                          amount NUMERIC(10, 2) NOT NULL,
                          pay_date DATE NOT NULL,
                          status VARCHAR(20) NOT NULL -- e.g., PAID, UNPAID
);

-- =================================================================
-- EXPANDED SAMPLE DATA FOR THOROUGH TESTING
-- =================================================================

-- Sample Teachers
INSERT INTO teachers (teacher_id, first_name, last_name, email, hire_date) VALUES
                                                                               (1, 'Albus', 'Dumbledore', 'albus.d@hogwarts.edu', '1970-01-01'),
                                                                               (2, 'Minerva', 'McGonagall', 'minerva.m@hogwarts.edu', '1975-09-01'),
                                                                               (3, 'Severus', 'Snape', 'severus.s@hogwarts.edu', '1981-08-30'),
                                                                               (4, 'Filius', 'Flitwick', 'filius.f@hogwarts.edu', '1978-05-20');

-- Sample Students
INSERT INTO students (student_id, first_name, last_name, date_of_birth, email, admission_date) VALUES
                                                                                                   (1, 'Harry', 'Potter', '1980-07-31', 'harry.p@hogwarts.edu', '1991-09-01'),
                                                                                                   (2, 'Hermione', 'Granger', '1979-09-19', 'hermione.g@hogwarts.edu', '1991-09-01'),
                                                                                                   (3, 'Ron', 'Weasley', '1980-03-01', 'ron.w@hogwarts.edu', '1991-09-01'),
                                                                                                   (4, 'Draco', 'Malfoy', '1980-06-05', 'draco.m@hogwarts.edu', '1991-09-01'),
                                                                                                   (5, 'Luna', 'Lovegood', '1981-02-13', 'luna.l@hogwarts.edu', '1992-09-01');

-- Default Users for ALL sample students and teachers
INSERT INTO users (username, password, role, student_id, teacher_id) VALUES
                                                                         ('admin', 'adminpass', 'ADMIN', NULL, NULL),
-- Teacher Users
                                                                         ('albus.d', 'teacherpass1', 'TEACHER', NULL, 1),
                                                                         ('minerva.m', 'teacherpass2', 'TEACHER', NULL, 2),
                                                                         ('severus.s', 'teacherpass3', 'TEACHER', NULL, 3),
                                                                         ('filius.f', 'teacherpass4', 'TEACHER', NULL, 4),
-- Student Users
                                                                         ('harry.p', 'studentpass1', 'STUDENT', 1, NULL),
                                                                         ('hermione.g', 'studentpass2', 'STUDENT', 2, NULL),
                                                                         ('ron.w', 'studentpass3', 'STUDENT', 3, NULL),
                                                                         ('draco.m', 'studentpass4', 'STUDENT', 4, NULL),
                                                                         ('luna.l', 'studentpass5', 'STUDENT', 5, NULL);

-- Sample Courses
INSERT INTO courses (course_id, course_name, course_code, credits, teacher_id) VALUES
                                                                                   (1, 'Transfiguration', 'TRAN101', 5, 2), -- Taught by McGonagall
                                                                                   (2, 'Potions', 'POT101', 5, 3),          -- Taught by Snape
                                                                                   (3, 'Defense Against the Dark Arts', 'DADA101', 5, 1), -- Taught by Dumbledore
                                                                                   (4, 'Charms', 'CHARM101', 4, 4);        -- Taught by Flitwick

-- Sample Enrollments
INSERT INTO enrollments (student_id, course_id, enrollment_date) VALUES
                                                                     (1, 1, '1991-09-02'), (1, 2, '1991-09-02'), (1, 3, '1991-09-02'), -- Harry in 3 courses
                                                                     (2, 1, '1991-09-02'), (2, 2, '1991-09-02'), (2, 4, '1991-09-02'), -- Hermione in 3 courses
                                                                     (3, 2, '1991-09-02'), (3, 4, '1991-09-02'),                       -- Ron in 2 courses
                                                                     (4, 2, '1991-09-02'),                                             -- Draco in 1 course
                                                                     (5, 4, '1992-09-02');                                             -- Luna in 1 course

-- Sample Grades
INSERT INTO grades (enrollment_id, grade, date_recorded) VALUES
                                                             (1, 88.00, '1991-12-20'), -- Harry in Transfiguration
                                                             (4, 98.50, '1991-12-20'), -- Hermione in Transfiguration
                                                             (5, 100.00, '1991-12-21');-- Hermione in Charms

-- Sample Attendance
INSERT INTO attendance (enrollment_id, attendance_date, status) VALUES
                                                                    (1, '1991-09-03', 'PRESENT'), (1, '1991-09-04', 'ABSENT'),
                                                                    (4, '1991-09-03', 'PRESENT'), (4, '1991-09-04', 'PRESENT');

-- Sample Fees
INSERT INTO fees (student_id, description, amount, due_date, status, payment_date) VALUES
                                                                                       (1, 'Fall 1991 Tuition', 500.00, '1991-10-01', 'PAID', '1991-09-15'),
                                                                                       (2, 'Fall 1991 Tuition', 500.00, '1991-10-01', 'PAID', '1991-09-20'),
                                                                                       (3, 'Fall 1991 Tuition', 500.00, '1991-10-01', 'UNPAID', NULL),
                                                                                       (4, 'Fall 1991 Tuition', 550.00, '1991-10-01', 'PAID', '1991-09-10'),
                                                                                       (5, 'Fall 1992 Tuition', 600.00, '1992-10-01', 'UNPAID', NULL);

-- Sample Salaries
INSERT INTO salaries (teacher_id, description, amount, pay_date, status) VALUES
                                                                             (2, 'September 1991 Salary', 2000.00, '1991-09-30', 'PAID'),
                                                                             (3, 'September 1991 Salary', 1800.00, '1991-09-30', 'PAID'),
                                                                             (2, 'October 1991 Salary', 2000.00, '1991-10-31', 'UNPAID'),
                                                                             (1, 'October 1991 Salary', 3000.00, '1991-10-31', 'UNPAID');

-- Reset sequences to avoid conflicts with manual IDs
SELECT setval('teachers_teacher_id_seq', (SELECT MAX(teacher_id) FROM teachers), true);
SELECT setval('students_student_id_seq', (SELECT MAX(student_id) FROM students), true);
SELECT setval('users_user_id_seq', (SELECT MAX(user_id) FROM users), true);
SELECT setval('courses_course_id_seq', (SELECT MAX(course_id) FROM courses), true);
SELECT setval('enrollments_enrollment_id_seq', (SELECT MAX(enrollment_id) FROM enrollments), true);

