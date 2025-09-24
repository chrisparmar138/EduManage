package com.edumanage.web;

import com.edumanage.dao.StudentDAO;
import com.edumanage.dao.UserDAO;
import com.edumanage.model.Student;
import com.edumanage.model.User;
import com.edumanage.util.EmailUtil; // Import the new EmailUtil

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

@WebServlet("/students/*")
public class StudentServlet extends HttpServlet {
    private StudentDAO studentDAO;
    private UserDAO userDAO;

    public void init() {
        studentDAO = new StudentDAO();
        userDAO = new UserDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getPathInfo();
        try {
            switch (action) {
                case "/insert":
                    insertStudentAndUser(request, response);
                    break;
                case "/update":
                    updateStudent(request, response);
                    break;
                default:
                    listStudents(request, response);
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
                    deleteStudent(request, response);
                    break;
                default:
                    listStudents(request, response);
                    break;
            }
        } catch (Exception ex) {
            throw new ServletException(ex);
        }
    }

    private void listStudents(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Student> listStudent = studentDAO.getAllStudents();
        request.setAttribute("listStudent", listStudent);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/student-list.jsp");
        dispatcher.forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/student-form.jsp");
        dispatcher.forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Student existingStudent = studentDAO.getStudentById(id);
        request.setAttribute("student", existingStudent);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/student-form.jsp");
        dispatcher.forward(request, response);
    }

    private void insertStudentAndUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Step 1: Create the student profile
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        LocalDate dateOfBirth = LocalDate.parse(request.getParameter("dateOfBirth"));
        String email = request.getParameter("email");
        LocalDate admissionDate = LocalDate.parse(request.getParameter("admissionDate"));

        Student newStudent = new Student(0, firstName, lastName, dateOfBirth, email, admissionDate);
        int newStudentId = studentDAO.addStudent(newStudent);

        // Step 2: Generate credentials and create the user account
        String username = firstName.toLowerCase() + "." + lastName.toLowerCase();
        String password = generateRandomPassword();

        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(password);
        newUser.setRole(User.UserRole.STUDENT);
        newUser.setStudentId(newStudentId); // Link user to the new student profile
        userDAO.addUser(newUser);

        // Step 3: Send the credentials email
        EmailUtil.sendCredentialsEmail(email, username, password);

        // Step 4: Set a generic success message for the admin
        HttpSession session = request.getSession();
        session.setAttribute("successMessage", "New student and user account created successfully. Credentials have been sent to " + email);

        response.sendRedirect(request.getContextPath() + "/students/list");
    }

    private void updateStudent(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        LocalDate dateOfBirth = LocalDate.parse(request.getParameter("dateOfBirth"));
        String email = request.getParameter("email");
        LocalDate admissionDate = LocalDate.parse(request.getParameter("admissionDate"));

        Student student = new Student(id, firstName, lastName, dateOfBirth, email, admissionDate);
        studentDAO.updateStudent(student);
        response.sendRedirect(request.getContextPath() + "/students/list");
    }

    private void deleteStudent(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Note: In a real app, you'd also want to handle deleting the associated user account
        int id = Integer.parseInt(request.getParameter("id"));
        studentDAO.deleteStudent(id);
        response.sendRedirect(request.getContextPath() + "/students/list");
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

