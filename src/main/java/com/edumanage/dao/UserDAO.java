package com.edumanage.dao;

import com.edumanage.model.User;
import com.edumanage.util.DatabaseUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserDAO {

    public User getUserByUsername(String username) {
        User user = null;
        String sql = "SELECT * FROM users WHERE username = ?";
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, username);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                user = new User();
                user.setUserId(rs.getInt("user_id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setRole(User.UserRole.valueOf(rs.getString("role")));
                user.setStudentId(rs.getInt("student_id"));
                user.setTeacherId(rs.getInt("teacher_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public int addUser(User user) {
        String sql = "INSERT INTO users (username, password, role, student_id, teacher_id) VALUES (?, ?, ?, ?, ?)";
        int generatedUserId = 0;
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getRole().name());

            if (user.getStudentId() != 0) {
                preparedStatement.setInt(4, user.getStudentId());
            } else {
                preparedStatement.setNull(4, java.sql.Types.INTEGER);
            }

            if (user.getTeacherId() != 0) {
                preparedStatement.setInt(5, user.getTeacherId());
            } else {
                preparedStatement.setNull(5, java.sql.Types.INTEGER);
            }

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet rs = preparedStatement.getGeneratedKeys()) {
                    if (rs.next()) {
                        generatedUserId = rs.getInt(1);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return generatedUserId;
    }
}

