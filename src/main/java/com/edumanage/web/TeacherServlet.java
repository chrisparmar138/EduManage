package com.edumanage.web;

import com.edumanage.dao.TeacherDAO;
import com.edumanage.dao.UserDAO;
import com.edumanage.model.Teacher;
import com.edumanage.model.User;
import com.edumanage.util.EmailUtil; // Import the EmailUtil

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;

@WebServlet("/teachers/*")
public class TeacherServlet extends HttpServlet {
    private TeacherDAO teacherDAO;
    private UserDAO userDAO;

    public void init() {
        teacherDAO = new TeacherDAO();
        userDAO = new UserDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getPathInfo();
        try {
            switch (action) {
                case "/insert":
                    insertTeacherAndUser(request, response);
                    break;
                case "/update":
                    updateTeacher(request, response);
                    break;
                default:
                    listTeachers(request, response);
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
                    deleteTeacher(request, response);
                    break;
                default:
                    listTeachers(request, response);
                    break;
            }
        } catch (Exception ex) {
            throw new ServletException(ex);
        }
    }

    private void listTeachers(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Teacher> listTeacher = teacherDAO.getAllTeachers();
        request.setAttribute("listTeacher", listTeacher);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/teacher-list.jsp");
        dispatcher.forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/teacher-form.jsp");
        dispatcher.forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Teacher existingTeacher = teacherDAO.getTeacherById(id);
        request.setAttribute("teacher", existingTeacher);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/teacher-form.jsp");
        dispatcher.forward(request, response);
    }

    private void insertTeacherAndUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        LocalDate hireDate = LocalDate.parse(request.getParameter("hireDate"));

        Teacher newTeacher = new Teacher(0, firstName, lastName, email, hireDate);
        int newTeacherId = teacherDAO.addTeacher(newTeacher);

        // Generate credentials and create the user account
        String username = firstName.toLowerCase() + "." + lastName.toLowerCase();
        String password = generateRandomPassword();

        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(password);
        newUser.setRole(User.UserRole.TEACHER);
        newUser.setTeacherId(newTeacherId); // Link user to the new teacher profile
        userDAO.addUser(newUser);

        // Send the credentials email
        EmailUtil.sendCredentialsEmail(email, username, password);

        // Set a generic success message for the admin
        HttpSession session = request.getSession();
        session.setAttribute("successMessage", "New teacher and user account created successfully. Credentials have been sent to " + email);

        response.sendRedirect(request.getContextPath() + "/teachers/list");
    }

    private void updateTeacher(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        LocalDate hireDate = LocalDate.parse(request.getParameter("hireDate"));

        Teacher teacher = new Teacher(id, firstName, lastName, email, hireDate);
        teacherDAO.updateTeacher(teacher);
        response.sendRedirect(request.getContextPath() + "/teachers/list");
    }

    private void deleteTeacher(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        teacherDAO.deleteTeacher(id);
        response.sendRedirect(request.getContextPath() + "/teachers/list");
    }

    private String generateRandomPassword() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder(8);
        Random random = new Random();
        for (int i = 0; i < 8; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }
        return sb.toString();
    }
}

