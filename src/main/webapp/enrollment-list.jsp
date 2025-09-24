<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="pageTitle" value="Manage Enrollments" scope="request" />
<jsp:include page="common/header.jsp" />

<div class="card">
    <div class="card-body">
        <div class="d-flex justify-content-between align-items-center mb-3">
            <h1 class="card-title">Enrollment Management</h1>
            <a href="${pageContext.request.contextPath}/enrollments/new" class="btn btn-primary">Add New Enrollment</a>
        </div>
        <hr>

        <table class="table table-striped table-hover">
            <thead class="table-dark">
            <tr>
                <th>ID</th>
                <th>Student Name</th>
                <th>Course Name</th>
                <th>Enrollment Date</th>
                <th style="width: 15%;">Actions</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="enrollment" items="${listEnrollment}">
                <tr>
                    <td><c:out value="${enrollment.enrollmentId}" /></td>
                    <td><c:out value="${enrollment.studentName}" /></td>
                    <td><c:out value="${enrollment.courseName}" /></td>
                    <td><c:out value="${enrollment.enrollmentDate}" /></td>
                    <td>
                        <a href="${pageContext.request.contextPath}/enrollments/delete?id=<c:out value='${enrollment.enrollmentId}' />" class="btn btn-sm btn-danger" onclick="return confirm('Are you sure you want to delete this enrollment?');">Delete</a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>

<jsp:include page="common/footer.jsp" />

