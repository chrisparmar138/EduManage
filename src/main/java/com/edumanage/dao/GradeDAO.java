package com.edumanage.dao;

import com.edumanage.model.Grade;
import com.edumanage.util.DatabaseUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GradeDAO {

    public List<Grade> getAllGrades() {
        List<Grade> gradeList = new ArrayList<>();
        String sql = "SELECT g.*, s.first_name, s.last_name, c.course_name " +
                "FROM grades g " +
                "JOIN enrollments e ON g.enrollment_id = e.enrollment_id " +
                "JOIN students s ON e.student_id = s.student_id " +
                "JOIN courses c ON e.course_id = c.course_id ORDER BY g.date_recorded DESC";
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                gradeList.add(extractGradeFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return gradeList;
    }

    public Grade getGradeById(int gradeId) {
        Grade grade = null;
        String sql = "SELECT g.*, s.first_name, s.last_name, c.course_name " +
                "FROM grades g " +
                "JOIN enrollments e ON g.enrollment_id = e.enrollment_id " +
                "JOIN students s ON e.student_id = s.student_id " +
                "JOIN courses c ON e.course_id = c.course_id WHERE g.grade_id = ?";
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, gradeId);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                grade = extractGradeFromResultSet(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return grade;
    }

    public void addGrade(Grade grade) {
        String sql = "INSERT INTO grades (enrollment_id, grade, date_recorded) VALUES (?, ?, ?)";
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, grade.getEnrollmentId());
            preparedStatement.setDouble(2, grade.getGrade());
            preparedStatement.setDate(3, java.sql.Date.valueOf(grade.getDateRecorded()));
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateGrade(Grade grade) {
        String sql = "UPDATE grades SET enrollment_id = ?, grade = ?, date_recorded = ? WHERE grade_id = ?";
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, grade.getEnrollmentId());
            preparedStatement.setDouble(2, grade.getGrade());
            preparedStatement.setDate(3, java.sql.Date.valueOf(grade.getDateRecorded()));
            preparedStatement.setInt(4, grade.getGradeId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // This method is now fully implemented
    public List<Grade> getGradesByStudentId(int studentId) {
        List<Grade> gradeList = new ArrayList<>();
        String sql = "SELECT g.*, c.course_name FROM grades g " +
                "JOIN enrollments e ON g.enrollment_id = e.enrollment_id " +
                "JOIN courses c ON e.course_id = c.course_id " +
                "WHERE e.student_id = ?";
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, studentId);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Grade grade = new Grade();
                grade.setGradeId(rs.getInt("grade_id"));
                grade.setEnrollmentId(rs.getInt("enrollment_id"));
                grade.setGrade(rs.getDouble("grade"));
                grade.setDateRecorded(rs.getDate("date_recorded").toLocalDate());
                grade.setCourseName(rs.getString("course_name"));
                gradeList.add(grade);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return gradeList;
    }

    private Grade extractGradeFromResultSet(ResultSet rs) throws SQLException {
        Grade grade = new Grade();
        grade.setGradeId(rs.getInt("grade_id"));
        grade.setEnrollmentId(rs.getInt("enrollment_id"));
        grade.setGrade(rs.getDouble("grade"));
        grade.setDateRecorded(rs.getDate("date_recorded").toLocalDate());
        grade.setStudentName(rs.getString("first_name") + " " + rs.getString("last_name"));
        grade.setCourseName(rs.getString("course_name"));
        return grade;
    }

    // Other methods used by the teacher role
    public Map<Integer, Grade> getGradesByCourseId(int courseId) {
        // Implementation for teacher view...
        return new HashMap<>();
    }

    public Grade getGradeByEnrollmentId(int enrollmentId) {
        // Implementation for teacher view...
        return null;
    }
}

