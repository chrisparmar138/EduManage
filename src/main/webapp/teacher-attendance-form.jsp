<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="pageTitle" value="Take Attendance" scope="request" />
<jsp:include page="common/header.jsp" />

<div class="card">
    <div class="card-body">
        <div class="d-flex justify-content-between align-items-center mb-3">
            <h1 class="card-title">Attendance for: <c:out value="${course.courseName}"/></h1>
            <a href="${pageContext.request.contextPath}/teacher/attendance" class="btn btn-secondary">Change Course</a>
        </div>
        <hr>

        <form action="${pageContext.request.contextPath}/teacher/save-attendance" method="post">
            <input type="hidden" name="courseId" value="${course.courseId}" />

            <div class="mb-3">
                <label for="attendanceDate" class="form-label">Attendance Date:</label>
                <input type="date" class="form-control" id="attendanceDate" name="attendanceDate" required>
            </div>

            <table class="table table-striped table-hover mt-4">
                <thead class="table-dark">
                <tr>
                    <th>Student Name</th>
                    <th>Status</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="enrollment" items="${enrollmentList}" varStatus="status">
                    <tr>
                        <td><c:out value="${enrollment.studentName}"/></td>
                        <td>
                            <div class="form-check form-check-inline">
                                <input class="form-check-input" type="radio" id="present_${enrollment.enrollmentId}" name="status_${enrollment.enrollmentId}" value="PRESENT" checked>
                                <label class="form-check-label" for="present_${enrollment.enrollmentId}">Present</label>
                            </div>
                            <div class="form-check form-check-inline">
                                <input class="form-check-input" type="radio" id="absent_${enrollment.enrollmentId}" name="status_${enrollment.enrollmentId}" value="ABSENT">
                                <label class="form-check-label" for="absent_${enrollment.enrollmentId}">Absent</label>
                            </div>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>

            <div class="d-flex justify-content-end mt-4">
                <button type="submit" class="btn btn-primary">Save Attendance</button>
            </div>
        </form>
    </div>
</div>

<jsp:include page="common/footer.jsp" />

