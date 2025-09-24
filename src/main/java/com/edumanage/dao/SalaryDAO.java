package com.edumanage.dao;

import com.edumanage.model.Salary;
import com.edumanage.util.DatabaseUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SalaryDAO {

    public List<Salary> getAllSalaries() {
        List<Salary> salaryList = new ArrayList<>();
        String sql = "SELECT s.*, t.first_name, t.last_name FROM salaries s JOIN teachers t ON s.teacher_id = t.teacher_id";
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Salary salary = new Salary();
                salary.setSalaryId(rs.getInt("salary_id"));
                salary.setTeacherId(rs.getInt("teacher_id"));
                salary.setDescription(rs.getString("description"));
                salary.setAmount(rs.getBigDecimal("amount"));
                salary.setPayDate(rs.getDate("pay_date").toLocalDate());
                salary.setStatus(Salary.SalaryStatus.valueOf(rs.getString("status")));
                salary.setTeacherName(rs.getString("first_name") + " " + rs.getString("last_name"));
                salaryList.add(salary);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return salaryList;
    }

    public void addSalary(Salary salary) {
        String sql = "INSERT INTO salaries (teacher_id, description, amount, pay_date, status) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, salary.getTeacherId());
            preparedStatement.setString(2, salary.getDescription());
            preparedStatement.setBigDecimal(3, salary.getAmount());
            preparedStatement.setDate(4, java.sql.Date.valueOf(salary.getPayDate()));
            preparedStatement.setString(5, salary.getStatus().name());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateSalaryStatus(int salaryId, Salary.SalaryStatus status) {
        String sql = "UPDATE salaries SET status = ? WHERE salary_id = ?";
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, status.name());
            preparedStatement.setInt(2, salaryId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

