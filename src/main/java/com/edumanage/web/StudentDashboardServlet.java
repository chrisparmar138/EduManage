package com.edumanage.web;

import com.edumanage.dao.AttendanceDAO;
import com.edumanage.dao.FeeDAO;
import com.edumanage.dao.GradeDAO;
import com.edumanage.model.Attendance;
import com.edumanage.model.Fee;
import com.edumanage.model.Grade;
import com.edumanage.model.User;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/student/*")
public class StudentDashboardServlet extends HttpServlet {
    private AttendanceDAO attendanceDAO;
    private GradeDAO gradeDAO;
    private FeeDAO feeDAO;

    @Override
    public void init() {
        attendanceDAO = new AttendanceDAO();
        gradeDAO = new GradeDAO();
        feeDAO = new FeeDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getPathInfo();
        if (action == null) {
            action = "/";
        }

        User user = (User) request.getSession().getAttribute("user");
        if (user == null || user.getStudentId() == 0) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        int studentId = user.getStudentId();

        switch (action) {
            case "/my-attendance":
                showMyAttendance(request, response, studentId);
                break;
            case "/my-grades":
                showMyGrades(request, response, studentId);
                break;
            case "/my-fees":
                showMyFees(request, response, studentId);
                break;
            default:
                response.sendRedirect(request.getContextPath() + "/");
                break;
        }
    }

    private void showMyAttendance(HttpServletRequest request, HttpServletResponse response, int studentId) throws ServletException, IOException {
        List<Attendance> attendanceList = attendanceDAO.getAttendanceByStudentId(studentId);
        request.setAttribute("attendanceList", attendanceList);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/student-my-attendance.jsp");
        dispatcher.forward(request, response);
    }

    private void showMyGrades(HttpServletRequest request, HttpServletResponse response, int studentId) throws ServletException, IOException {
        List<Grade> gradeList = gradeDAO.getGradesByStudentId(studentId);
        request.setAttribute("gradeList", gradeList);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/student-my-grades.jsp");
        dispatcher.forward(request, response);
    }

    private void showMyFees(HttpServletRequest request, HttpServletResponse response, int studentId) throws ServletException, IOException {
        List<Fee> feeList = feeDAO.getFeesByStudentId(studentId);
        request.setAttribute("feeList", feeList);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/student-my-fees.jsp");
        dispatcher.forward(request, response);
    }
}

