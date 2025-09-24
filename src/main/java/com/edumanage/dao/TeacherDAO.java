package com.edumanage.dao;

import com.edumanage.model.Teacher;
import com.edumanage.util.DatabaseUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TeacherDAO {

    public List<Teacher> getAllTeachers() {
        List<Teacher> teachers = new ArrayList<>();
        String sql = "SELECT * FROM teachers";
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                teachers.add(extractTeacherFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return teachers;
    }

    public Teacher getTeacherById(int teacherId) {
        Teacher teacher = null;
        String sql = "SELECT * FROM teachers WHERE teacher_id = ?";
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, teacherId);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                teacher = extractTeacherFromResultSet(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return teacher;
    }

    public int addTeacher(Teacher teacher) {
        String sql = "INSERT INTO teachers (first_name, last_name, email, hire_date) VALUES (?, ?, ?, ?)";
        int generatedTeacherId = 0;
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, teacher.getFirstName());
            preparedStatement.setString(2, teacher.getLastName());
            preparedStatement.setString(3, teacher.getEmail());
            preparedStatement.setDate(4, java.sql.Date.valueOf(teacher.getHireDate()));

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet rs = preparedStatement.getGeneratedKeys()) {
                    if (rs.next()) {
                        generatedTeacherId = rs.getInt(1);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return generatedTeacherId;
    }

    public void updateTeacher(Teacher teacher) {
        String sql = "UPDATE teachers SET first_name = ?, last_name = ?, email = ?, hire_date = ? WHERE teacher_id = ?";
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, teacher.getFirstName());
            preparedStatement.setString(2, teacher.getLastName());
            preparedStatement.setString(3, teacher.getEmail());
            preparedStatement.setDate(4, java.sql.Date.valueOf(teacher.getHireDate()));
            preparedStatement.setInt(5, teacher.getTeacherId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteTeacher(int teacherId) {
        String sql = "DELETE FROM teachers WHERE teacher_id = ?";
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, teacherId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Teacher extractTeacherFromResultSet(ResultSet rs) throws SQLException {
        int teacherId = rs.getInt("teacher_id");
        String firstName = rs.getString("first_name");
        String lastName = rs.getString("last_name");
        String email = rs.getString("email");
        LocalDate hireDate = rs.getDate("hire_date").toLocalDate();
        return new Teacher(teacherId, firstName, lastName, email, hireDate);
    }
}

