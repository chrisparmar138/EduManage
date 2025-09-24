package com.edumanage.web;

import com.edumanage.dao.CourseDAO;
import com.edumanage.dao.TeacherDAO;
import com.edumanage.model.Course;
import com.edumanage.model.Teacher;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/courses/*")
public class CourseServlet extends HttpServlet {
    private CourseDAO courseDAO;
    private TeacherDAO teacherDAO;

    public void init() {
        courseDAO = new CourseDAO();
        teacherDAO = new TeacherDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getPathInfo();
        try {
            switch (action) {
                case "/insert":
                    insertCourse(request, response);
                    break;
                case "/update":
                    updateCourse(request, response);
                    break;
                default:
                    listCourses(request, response);
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
                case "/delete":
                    deleteCourse(request, response);
                    break;
                default:
                    listCourses(request, response);
                    break;
            }
        } catch (Exception ex) {
            throw new ServletException(ex);
        }
    }

    private void listCourses(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Course> listCourse = courseDAO.getAllCourses();
        request.setAttribute("listCourse", listCourse);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/course-list.jsp");
        dispatcher.forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Teacher> listTeacher = teacherDAO.getAllTeachers();
        request.setAttribute("listTeacher", listTeacher);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/course-form.jsp");
        dispatcher.forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Course existingCourse = courseDAO.getCourseById(id);
        List<Teacher> listTeacher = teacherDAO.getAllTeachers();
        request.setAttribute("course", existingCourse);
        request.setAttribute("listTeacher", listTeacher);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/course-form.jsp");
        dispatcher.forward(request, response);
    }

    private void insertCourse(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String courseName = request.getParameter("courseName");
        String courseCode = request.getParameter("courseCode");
        int credits = Integer.parseInt(request.getParameter("credits"));
        int teacherId = Integer.parseInt(request.getParameter("teacherId"));

        Course newCourse = new Course(0, courseName, courseCode, credits, teacherId, null);
        courseDAO.addCourse(newCourse);
        response.sendRedirect(request.getContextPath() + "/courses/list");
    }

    private void updateCourse(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String courseName = request.getParameter("courseName");
        String courseCode = request.getParameter("courseCode");
        int credits = Integer.parseInt(request.getParameter("credits"));
        int teacherId = Integer.parseInt(request.getParameter("teacherId"));

        Course course = new Course(id, courseName, courseCode, credits, teacherId, null);
        courseDAO.updateCourse(course);
        response.sendRedirect(request.getContextPath() + "/courses/list");
    }

    private void deleteCourse(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        courseDAO.deleteCourse(id);
        response.sendRedirect(request.getContextPath() + "/courses/list");
    }
}

