package com.edumanage.web;

import com.edumanage.dao.CourseDAO;
import com.edumanage.dao.EnrollmentDAO;
import com.edumanage.dao.StudentDAO;
import com.edumanage.model.Course;
import com.edumanage.model.Enrollment;
import com.edumanage.model.Student;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@WebServlet("/enrollments/*")
public class EnrollmentServlet extends HttpServlet {
    private EnrollmentDAO enrollmentDAO;
    private StudentDAO studentDAO;
    private CourseDAO courseDAO;

    public void init() {
        enrollmentDAO = new EnrollmentDAO();
        studentDAO = new StudentDAO();
        courseDAO = new CourseDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getPathInfo();
        try {
            if ("/insert".equals(action)) {
                insertEnrollment(request, response);
            } else {
                listEnrollments(request, response);
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
                case "/delete":
                    deleteEnrollment(request, response);
                    break;
                default:
                    listEnrollments(request, response);
                    break;
            }
        } catch (Exception ex) {
            throw new ServletException(ex);
        }
    }

    private void listEnrollments(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Enrollment> listEnrollment = enrollmentDAO.getAllEnrollments();
        request.setAttribute("listEnrollment", listEnrollment);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/enrollment-list.jsp");
        dispatcher.forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Student> listStudent = studentDAO.getAllStudents();
        List<Course> listCourse = courseDAO.getAllCourses();
        request.setAttribute("listStudent", listStudent);
        request.setAttribute("listCourse", listCourse);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/enrollment-form.jsp");
        dispatcher.forward(request, response);
    }

    private void insertEnrollment(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int studentId = Integer.parseInt(request.getParameter("studentId"));
        int courseId = Integer.parseInt(request.getParameter("courseId"));
        LocalDate enrollmentDate = LocalDate.parse(request.getParameter("enrollmentDate"));

        Enrollment newEnrollment = new Enrollment(0, studentId, courseId, enrollmentDate, null, null);
        enrollmentDAO.addEnrollment(newEnrollment);
        response.sendRedirect(request.getContextPath() + "/enrollments/list");
    }

    private void deleteEnrollment(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        enrollmentDAO.deleteEnrollment(id);
        response.sendRedirect(request.getContextPath() + "/enrollments/list");
    }
}

