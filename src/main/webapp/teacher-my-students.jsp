<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="pageTitle" value="My Students" scope="request" />
<jsp:include page="/common/header.jsp" />

<div class="card">
    <div class="card-body">
        <div class="d-flex justify-content-between align-items-center mb-3">
            <h1 class="card-title">My Students</h1>
            <a href="${pageContext.request.contextPath}/" class="btn btn-secondary">Back to Dashboard</a>
        </div>
        <hr>
        <p>Below is a list of all students enrolled in your courses.</p>

        <table class="table table-striped table-hover mt-4">
            <thead class="table-dark">
            <tr>
                <th>Student ID</th>
                <th>Student Name</th>
                <th>Course Name</th>
                <th>Enrollment Date</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="enrollment" items="${enrollmentList}">
                <tr>
                    <td><c:out value="${enrollment.studentId}" /></td>
                    <td><c:out value="${enrollment.studentName}" /></td>
                    <td><c:out value="${enrollment.courseName}" /></td>
                    <td><c:out value="${enrollment.enrollmentDate}" /></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>

<jsp:include page="/common/footer.jsp" />

