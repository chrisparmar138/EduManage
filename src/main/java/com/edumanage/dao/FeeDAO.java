package com.edumanage.dao;

import com.edumanage.model.Fee;
import com.edumanage.util.DatabaseUtil;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FeeDAO {

    public List<Fee> getAllFees() {
        List<Fee> feeList = new ArrayList<>();
        String sql = "SELECT f.*, s.first_name, s.last_name FROM fees f JOIN students s ON f.student_id = s.student_id";
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Fee fee = new Fee();
                fee.setFeeId(rs.getInt("fee_id"));
                fee.setStudentId(rs.getInt("student_id"));
                fee.setDescription(rs.getString("description"));
                fee.setAmount(rs.getBigDecimal("amount"));
                fee.setDueDate(rs.getDate("due_date").toLocalDate());
                fee.setStatus(Fee.FeeStatus.valueOf(rs.getString("status")));
                if (rs.getDate("payment_date") != null) {
                    fee.setPaymentDate(rs.getDate("payment_date").toLocalDate());
                }
                fee.setStudentName(rs.getString("first_name") + " " + rs.getString("last_name"));
                feeList.add(fee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return feeList;
    }

    public List<Fee> getFeesByStudentId(int studentId) {
        List<Fee> feeList = new ArrayList<>();
        String sql = "SELECT * FROM fees WHERE student_id = ?";
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, studentId);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Fee fee = new Fee();
                fee.setFeeId(rs.getInt("fee_id"));
                fee.setStudentId(rs.getInt("student_id"));
                fee.setDescription(rs.getString("description"));
                fee.setAmount(rs.getBigDecimal("amount"));
                fee.setDueDate(rs.getDate("due_date").toLocalDate());
                fee.setStatus(Fee.FeeStatus.valueOf(rs.getString("status")));
                if (rs.getDate("payment_date") != null) {
                    fee.setPaymentDate(rs.getDate("payment_date").toLocalDate());
                }
                feeList.add(fee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return feeList;
    }

    public void addFee(Fee fee) {
        String sql = "INSERT INTO fees (student_id, description, amount, due_date, status) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, fee.getStudentId());
            preparedStatement.setString(2, fee.getDescription());
            preparedStatement.setBigDecimal(3, fee.getAmount());
            preparedStatement.setDate(4, java.sql.Date.valueOf(fee.getDueDate()));
            preparedStatement.setString(5, fee.getStatus().name());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateFeeStatus(int feeId, Fee.FeeStatus status, LocalDate paymentDate) {
        String sql = "UPDATE fees SET status = ?, payment_date = ? WHERE fee_id = ?";
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, status.name());
            preparedStatement.setDate(2, java.sql.Date.valueOf(paymentDate));
            preparedStatement.setInt(3, feeId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

