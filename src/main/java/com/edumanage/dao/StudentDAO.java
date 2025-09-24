package com.edumanage.dao;

import com.edumanage.model.Student;
import com.edumanage.util.DatabaseUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {

    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM students";
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                students.add(extractStudentFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }

    public Student getStudentById(int studentId) {
        Student student = null;
        String sql = "SELECT * FROM students WHERE student_id = ?";
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, studentId);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                student = extractStudentFromResultSet(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return student;
    }

    public int addStudent(Student student) {
        String sql = "INSERT INTO students (first_name, last_name, date_of_birth, email, admission_date) VALUES (?, ?, ?, ?, ?)";
        int generatedStudentId = 0;
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, student.getFirstName());
            preparedStatement.setString(2, student.getLastName());
            preparedStatement.setDate(3, java.sql.Date.valueOf(student.getDateOfBirth()));
            preparedStatement.setString(4, student.getEmail());
            preparedStatement.setDate(5, java.sql.Date.valueOf(student.getAdmissionDate()));

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet rs = preparedStatement.getGeneratedKeys()) {
                    if (rs.next()) {
                        generatedStudentId = rs.getInt(1);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return generatedStudentId;
    }

    public void updateStudent(Student student) {
        String sql = "UPDATE students SET first_name = ?, last_name = ?, date_of_birth = ?, email = ?, admission_date = ? WHERE student_id = ?";
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, student.getFirstName());
            preparedStatement.setString(2, student.getLastName());
            preparedStatement.setDate(3, java.sql.Date.valueOf(student.getDateOfBirth()));
            preparedStatement.setString(4, student.getEmail());
            preparedStatement.setDate(5, java.sql.Date.valueOf(student.getAdmissionDate()));
            preparedStatement.setInt(6, student.getStudentId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteStudent(int studentId) {
        String sql = "DELETE FROM students WHERE student_id = ?";
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, studentId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Student extractStudentFromResultSet(ResultSet rs) throws SQLException {
        int studentId = rs.getInt("student_id");
        String firstName = rs.getString("first_name");
        String lastName = rs.getString("last_name");
        LocalDate dateOfBirth = rs.getDate("date_of_birth").toLocalDate();
        String email = rs.getString("email");
        LocalDate admissionDate = rs.getDate("admission_date") != null ? rs.getDate("admission_date").toLocalDate() : null;
        return new Student(studentId, firstName, lastName, dateOfBirth, email, admissionDate);
    }
}

