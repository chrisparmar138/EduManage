package com.edumanage.web;

import com.edumanage.dao.EnrollmentDAO;
import com.edumanage.dao.GradeDAO;
import com.edumanage.model.Enrollment;
import com.edumanage.model.Grade;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@WebServlet("/grades/*")
public class GradeServlet extends HttpServlet {
    private GradeDAO gradeDAO;
    private EnrollmentDAO enrollmentDAO;

    public void init() {
        gradeDAO = new GradeDAO();
        enrollmentDAO = new EnrollmentDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getPathInfo();
        if (action == null) {
            action = "/";
        }

        try {
            switch (action) {
                case "/insert":
                    insertGrade(request, response);
                    break;
                case "/update":
                    updateGrade(request, response);
                    break;
                default:
                    doGet(request, response);
                    break;
            }
        } catch (Exception ex) {
            throw new ServletException(ex);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getPathInfo();
        if (action == null) {
            action = "/list";
        }

        try {
            switch (action) {
                case "/new":
                    showNewForm(request, response);
                    break;
                case "/edit":
                    showEditForm(request, response);
                    break;
                case "/list":
                default:
                    listGrades(request, response);
                    break;
            }
        } catch (Exception ex) {
            throw new ServletException(ex);
        }
    }

    private void listGrades(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Grade> gradeList = gradeDAO.getAllGrades();
        request.setAttribute("gradeList", gradeList);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/grade-list.jsp");
        dispatcher.forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Enrollment> enrollmentList = enrollmentDAO.getAllEnrollments();
        request.setAttribute("enrollmentList", enrollmentList);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/grade-form.jsp");
        dispatcher.forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Grade existingGrade = gradeDAO.getGradeById(id);
        List<Enrollment> enrollmentList = enrollmentDAO.getAllEnrollments();
        request.setAttribute("grade", existingGrade);
        request.setAttribute("enrollmentList", enrollmentList);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/grade-form.jsp");
        dispatcher.forward(request, response);
    }

    private void insertGrade(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int enrollmentId = Integer.parseInt(request.getParameter("enrollmentId"));
        double gradeValue = Double.parseDouble(request.getParameter("grade"));

        Grade newGrade = new Grade();
        newGrade.setEnrollmentId(enrollmentId);
        newGrade.setGrade(gradeValue);
        newGrade.setDateRecorded(LocalDate.now());

        gradeDAO.addGrade(newGrade);
        response.sendRedirect(request.getContextPath() + "/grades/list");
    }

    private void updateGrade(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int gradeId = Integer.parseInt(request.getParameter("gradeId"));
        int enrollmentId = Integer.parseInt(request.getParameter("enrollmentId"));
        double gradeValue = Double.parseDouble(request.getParameter("grade"));
        LocalDate dateRecorded = LocalDate.parse(request.getParameter("dateRecorded"));

        Grade grade = new Grade();
        grade.setGradeId(gradeId);
        grade.setEnrollmentId(enrollmentId);
        grade.setGrade(gradeValue);
        grade.setDateRecorded(dateRecorded);

        gradeDAO.updateGrade(grade);
        response.sendRedirect(request.getContextPath() + "/grades/list");
    }
}

