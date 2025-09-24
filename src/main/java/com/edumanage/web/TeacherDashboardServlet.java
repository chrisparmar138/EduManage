package com.edumanage.web;

import com.edumanage.dao.AttendanceDAO;
import com.edumanage.dao.CourseDAO;
import com.edumanage.dao.EnrollmentDAO;
import com.edumanage.dao.GradeDAO;
import com.edumanage.model.Attendance;
import com.edumanage.model.Course;
import com.edumanage.model.Enrollment;
import com.edumanage.model.Grade;
import com.edumanage.model.User;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/teacher/*")
public class TeacherDashboardServlet extends HttpServlet {
    private CourseDAO courseDAO;
    private EnrollmentDAO enrollmentDAO;
    private AttendanceDAO attendanceDAO;
    private GradeDAO gradeDAO;

    public void init() {
        courseDAO = new CourseDAO();
        enrollmentDAO = new EnrollmentDAO();
        attendanceDAO = new AttendanceDAO();
        gradeDAO = new GradeDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getPathInfo();
        if (action == null) {
            action = "/";
        }

        try {
            if (action.equals("/save-attendance")) {
                saveAttendance(request, response);
            } else if (action.equals("/save-grades")) {
                saveGrades(request, response);
            } else {
                doGet(request, response);
            }
        } catch (Exception ex) {
            throw new ServletException(ex);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getPathInfo();
        if (action == null) {
            action = "/";
        }

        try {
            switch (action) {
                case "/my-students":
                    listMyStudents(request, response);
                    break;
                case "/attendance":
                    selectCourseForAttendance(request, response);
                    break;
                case "/attendance-form":
                    showAttendanceForm(request, response);
                    break;
                case "/grades":
                    selectCourseForGrades(request, response);
                    break;
                case "/grades-form":
                    showGradesForm(request, response);
                    break;
                default:
                    response.sendRedirect(request.getContextPath() + "/");
                    break;
            }
        } catch (Exception ex) {
            throw new ServletException(ex);
        }
    }

    private void listMyStudents(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        int teacherId = user.getTeacherId();

        List<Enrollment> enrollmentList = enrollmentDAO.getStudentsByTeacherId(teacherId);
        request.setAttribute("enrollmentList", enrollmentList);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/teacher-my-students.jsp");
        dispatcher.forward(request, response);
    }

    private void selectCourseForAttendance(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        int teacherId = user.getTeacherId();

        List<Course> courseList = courseDAO.getCoursesByTeacherId(teacherId);
        request.setAttribute("courseList", courseList);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/teacher-select-course-attendance.jsp");
        dispatcher.forward(request, response);
    }

    private void showAttendanceForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int courseId = Integer.parseInt(request.getParameter("courseId"));
        Course course = courseDAO.getCourseById(courseId);
        List<Enrollment> enrollmentList = enrollmentDAO.getEnrollmentsWithStudentDetailsByCourseId(courseId);

        request.setAttribute("course", course);
        request.setAttribute("enrollmentList", enrollmentList);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/teacher-attendance-form.jsp");
        dispatcher.forward(request, response);
    }

    private void saveAttendance(HttpServletRequest request, HttpServletResponse response) throws IOException {
        LocalDate attendanceDate = LocalDate.parse(request.getParameter("attendanceDate"));

        List<Attendance> attendanceList = new ArrayList<>();

        for (String paramName : request.getParameterMap().keySet()) {
            if (paramName.startsWith("status_")) {
                int enrollmentId = Integer.parseInt(paramName.substring("status_".length()));
                String statusString = request.getParameter(paramName);

                Attendance attendance = new Attendance();
                attendance.setEnrollmentId(enrollmentId);
                attendance.setAttendanceDate(attendanceDate);
                attendance.setStatus(Attendance.AttendanceStatus.valueOf(statusString));
                attendanceList.add(attendance);
            }
        }

        attendanceDAO.addBulkAttendance(attendanceList);
        response.sendRedirect(request.getContextPath() + "/teacher/attendance?status=success");
    }

    private void selectCourseForGrades(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        int teacherId = user.getTeacherId();

        List<Course> courseList = courseDAO.getCoursesByTeacherId(teacherId);
        request.setAttribute("courseList", courseList);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/teacher-select-course-grades.jsp");
        dispatcher.forward(request, response);
    }

    private void showGradesForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int courseId = Integer.parseInt(request.getParameter("courseId"));
        Course course = courseDAO.getCourseById(courseId);
        List<Enrollment> enrollmentList = enrollmentDAO.getEnrollmentsWithStudentDetailsByCourseId(courseId);

        Map<Integer, Double> existingGrades = new HashMap<>();
        Map<Integer, Grade> gradeMap = gradeDAO.getGradesByCourseId(courseId);
        for(Grade grade : gradeMap.values()) {
            existingGrades.put(grade.getEnrollmentId(), grade.getGrade());
        }

        request.setAttribute("course", course);
        request.setAttribute("enrollmentList", enrollmentList);
        request.setAttribute("existingGrades", existingGrades);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/teacher-grades-form.jsp");
        dispatcher.forward(request, response);
    }

    private void saveGrades(HttpServletRequest request, HttpServletResponse response) throws IOException {
        for (String paramName : request.getParameterMap().keySet()) {
            if (paramName.startsWith("grade_")) {
                int enrollmentId = Integer.parseInt(paramName.substring("grade_".length()));
                String gradeStr = request.getParameter(paramName);

                if (gradeStr != null && !gradeStr.trim().isEmpty()) {
                    double gradeValue = Double.parseDouble(gradeStr);

                    Grade existingGrade = gradeDAO.getGradeByEnrollmentId(enrollmentId);

                    if (existingGrade != null) {
                        existingGrade.setGrade(gradeValue);
                        existingGrade.setDateRecorded(LocalDate.now());
                        gradeDAO.updateGrade(existingGrade);
                    } else {
                        Grade newGrade = new Grade();
                        newGrade.setEnrollmentId(enrollmentId);
                        newGrade.setGrade(gradeValue);
                        newGrade.setDateRecorded(LocalDate.now());
                        gradeDAO.addGrade(newGrade);
                    }
                }
            }
        }
        response.sendRedirect(request.getContextPath() + "/teacher/grades?status=success");
    }
}

