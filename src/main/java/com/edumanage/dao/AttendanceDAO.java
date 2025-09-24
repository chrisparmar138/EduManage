package com.edumanage.dao;

import com.edumanage.model.Attendance;
import com.edumanage.util.DatabaseUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AttendanceDAO {

    public void addBulkAttendance(List<Attendance> attendanceList) {
        String sql = "INSERT INTO attendance (enrollment_id, attendance_date, status) VALUES (?, ?, ?)";
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            for (Attendance attendance : attendanceList) {
                preparedStatement.setInt(1, attendance.getEnrollmentId());
                preparedStatement.setDate(2, java.sql.Date.valueOf(attendance.getAttendanceDate()));
                preparedStatement.setString(3, attendance.getStatus().name());
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Attendance> getAttendanceByStudentId(int studentId) {
        List<Attendance> attendanceList = new ArrayList<>();
        String sql = "SELECT a.attendance_date, a.status, c.course_name " +
                "FROM attendance a " +
                "JOIN enrollments e ON a.enrollment_id = e.enrollment_id " +
                "JOIN courses c ON e.course_id = c.course_id " +
                "WHERE e.student_id = ?";

        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, studentId);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Attendance attendance = new Attendance();
                attendance.setAttendanceDate(rs.getDate("attendance_date").toLocalDate());
                attendance.setStatus(Attendance.AttendanceStatus.valueOf(rs.getString("status")));
                attendance.setCourseName(rs.getString("course_name"));
                attendanceList.add(attendance);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return attendanceList;
    }

    // New method to get the attendance summary
    public Map<String, Integer> getAttendanceSummaryByStudentId(int studentId) {
        Map<String, Integer> summary = new HashMap<>();
        summary.put("PRESENT", 0);
        summary.put("ABSENT", 0);

        String sql = "SELECT a.status, COUNT(a.status) as status_count " +
                "FROM attendance a " +
                "JOIN enrollments e ON a.enrollment_id = e.enrollment_id " +
                "WHERE e.student_id = ? " +
                "GROUP BY a.status";

        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, studentId);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                String status = rs.getString("status");
                int count = rs.getInt("status_count");
                if (summary.containsKey(status)) {
                    summary.put(status, count);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return summary;
    }
}

