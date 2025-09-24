package com.edumanage.web;

import com.edumanage.dao.SalaryDAO;
import com.edumanage.dao.TeacherDAO;
import com.edumanage.model.Salary;
import com.edumanage.model.Teacher;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@WebServlet("/salaries/*")
public class SalaryServlet extends HttpServlet {
    private SalaryDAO salaryDAO;
    private TeacherDAO teacherDAO;

    public void init() {
        salaryDAO = new SalaryDAO();
        teacherDAO = new TeacherDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getPathInfo();
        if ("/insert".equals(action)) {
            try {
                insertSalary(request, response);
            } catch (Exception e) {
                throw new ServletException(e);
            }
        } else {
            doGet(request, response);
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
                default:
                    listSalaries(request, response);
                    break;
            }
        } catch (Exception ex) {
            throw new ServletException(ex);
        }
    }

    private void listSalaries(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Salary> listSalaries = salaryDAO.getAllSalaries();
        request.setAttribute("listSalaries", listSalaries);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/salary-list.jsp");
        dispatcher.forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Teacher> listTeachers = teacherDAO.getAllTeachers();
        request.setAttribute("listTeachers", listTeachers);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/salary-form.jsp");
        dispatcher.forward(request, response);
    }

    private void insertSalary(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int teacherId = Integer.parseInt(request.getParameter("teacherId"));
        BigDecimal amount = new BigDecimal(request.getParameter("amount"));
        LocalDate payDate = LocalDate.parse(request.getParameter("payDate"));

        Salary newSalary = new Salary();
        newSalary.setTeacherId(teacherId);
        newSalary.setAmount(amount);
        newSalary.setPayDate(payDate);
        salaryDAO.addSalary(newSalary);
        response.sendRedirect(request.getContextPath() + "/salaries/list");
    }
}

