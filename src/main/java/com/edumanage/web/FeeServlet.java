package com.edumanage.web;

import com.edumanage.dao.FeeDAO;
import com.edumanage.dao.StudentDAO;
import com.edumanage.model.Fee;
import com.edumanage.model.Student;

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

@WebServlet("/fees/*")
public class FeeServlet extends HttpServlet {
    private FeeDAO feeDAO;
    private StudentDAO studentDAO;

    @Override
    public void init() {
        feeDAO = new FeeDAO();
        studentDAO = new StudentDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getPathInfo();
        if (action == null) {
            action = "/";
        }

        try {
            switch (action) {
                case "/insert":
                    insertFee(request, response);
                    break;
                case "/updateStatus":
                    updateFeeStatus(request, response);
                    break;
                default:
                    doGet(request, response);
                    break;
            }
        } catch (Exception ex) {
            throw new ServletException(ex);
        }
    }

    @Override
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
                case "/list":
                default:
                    listFees(request, response);
                    break;
            }
        } catch (Exception ex) {
            throw new ServletException(ex);
        }
    }

    private void listFees(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Fee> feeList = feeDAO.getAllFees();
        request.setAttribute("feeList", feeList);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/fee-list.jsp");
        dispatcher.forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Student> studentList = studentDAO.getAllStudents();
        request.setAttribute("studentList", studentList);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/fee-form.jsp");
        dispatcher.forward(request, response);
    }

    private void insertFee(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int studentId = Integer.parseInt(request.getParameter("studentId"));
        String description = request.getParameter("description");
        BigDecimal amount = new BigDecimal(request.getParameter("amount"));
        LocalDate dueDate = LocalDate.parse(request.getParameter("dueDate"));

        Fee newFee = new Fee();
        newFee.setStudentId(studentId);
        newFee.setDescription(description);
        newFee.setAmount(amount);
        newFee.setDueDate(dueDate);
        newFee.setStatus(Fee.FeeStatus.UNPAID); // Use the enum for UNPAID status

        feeDAO.addFee(newFee);
        response.sendRedirect(request.getContextPath() + "/fees/list");
    }

    private void updateFeeStatus(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int feeId = Integer.parseInt(request.getParameter("feeId"));
        // For simplicity, we assume payment is made today when status is changed to PAID
        feeDAO.updateFeeStatus(feeId, Fee.FeeStatus.PAID, LocalDate.now());
        response.sendRedirect(request.getContextPath() + "/fees/list");
    }
}

