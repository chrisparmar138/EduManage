package com.edumanage.dao;

import com.edumanage.model.Enrollment;
import com.edumanage.model.Student;
import com.edumanage.util.DatabaseUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EnrollmentDAO {

    public List<Enrollment> getAllEnrollments() {
        List<Enrollment> enrollmentList = new ArrayList<>();
        // This query joins the necessary tables to get student and course names
        String sql = "SELECT e.*, s.first_name, s.last_name, c.course_name " +
                "FROM enrollments e " +
                "JOIN students s ON e.student_id = s.student_id " +
                "JOIN courses c ON e.course_id = c.course_id " +
                "ORDER BY s.last_name, c.course_name";
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Enrollment enrollment = new Enrollment();
                enrollment.setEnrollmentId(rs.getInt("enrollment_id"));
                enrollment.setStudentId(rs.getInt("student_id"));
                enrollment.setCourseId(rs.getInt("course_id"));
                enrollment.setEnrollmentDate(rs.getDate("enrollment_date").toLocalDate());
                enrollment.setStudentName(rs.getString("first_name") + " " + rs.getString("last_name"));
                enrollment.setCourseName(rs.getString("course_name"));
                enrollmentList.add(enrollment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return enrollmentList;
    }

    public void addEnrollment(Enrollment enrollment) {
        String sql = "INSERT INTO enrollments (student_id, course_id, enrollment_date) VALUES (?, ?, ?)";
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, enrollment.getStudentId());
            preparedStatement.setInt(2, enrollment.getCourseId());
            preparedStatement.setDate(3, java.sql.Date.valueOf(enrollment.getEnrollmentDate()));
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteEnrollment(int enrollmentId) {
        String sql = "DELETE FROM enrollments WHERE enrollment_id = ?";
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, enrollmentId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Other methods for teacher/student views
    public List<Enrollment> getStudentsByTeacherId(int teacherId) {
        List<Enrollment> enrollmentList = new ArrayList<>();
        // Implementation for teacher view...
        return enrollmentList;
    }

    public List<Enrollment> getEnrollmentsWithStudentDetailsByCourseId(int courseId) {
        List<Enrollment> enrollmentList = new ArrayList<>();
        // Implementation for teacher view...
        return enrollmentList;
    }
}

