package com.edumanage.web;

import com.edumanage.dao.AttendanceDAO;
import com.edumanage.dao.GradeDAO;
import com.edumanage.dao.StudentDAO;
import com.edumanage.model.Grade;
import com.edumanage.model.Student;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet("/reports/*")
public class ReportServlet extends HttpServlet {
    private StudentDAO studentDAO;
    private GradeDAO gradeDAO;
    private AttendanceDAO attendanceDAO;

    public void init() {
        studentDAO = new StudentDAO();
        gradeDAO = new GradeDAO();
        attendanceDAO = new AttendanceDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getPathInfo();
        if (action == null) {
            action = "/select";
        }

        try {
            switch (action) {
                case "/view":
                    viewReportCard(request, response);
                    break;
                default:
                    showStudentSelection(request, response);
                    break;
            }
        } catch (Exception ex) {
            throw new ServletException(ex);
        }
    }

    private void showStudentSelection(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Student> studentList = studentDAO.getAllStudents();
        request.setAttribute("studentList", studentList);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/report-card-selection.jsp");
        dispatcher.forward(request, response);
    }

    private void viewReportCard(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int studentId = Integer.parseInt(request.getParameter("studentId"));

        // Fetch all necessary data for the report
        Student student = studentDAO.getStudentById(studentId);
        List<Grade> gradeList = gradeDAO.getGradesByStudentId(studentId);
        Map<String, Integer> attendanceSummary = attendanceDAO.getAttendanceSummaryByStudentId(studentId);

        // Set data as request attributes
        request.setAttribute("student", student);
        request.setAttribute("gradeList", gradeList);
        request.setAttribute("attendanceSummary", attendanceSummary);

        // Forward to the report card view
        RequestDispatcher dispatcher = request.getRequestDispatcher("/report-card-view.jsp");
        dispatcher.forward(request, response);
    }
}
