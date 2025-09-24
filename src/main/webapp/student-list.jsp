<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="pageTitle" value="Manage Students" scope="request" />
<jsp:include page="/common/header.jsp" />

<div class="card">
    <div class="card-body">
        <div class="d-flex justify-content-between align-items-center mb-3">
            <h1 class="card-title">Student Management</h1>
            <a href="${pageContext.request.contextPath}/students/new" class="btn btn-success">Add New Student</a>
        </div>
        <hr>

        <%-- Updated to show a generic success message after a student is added --%>
        <c:if test="${not empty sessionScope.successMessage}">
            <div class="alert alert-info alert-dismissible fade show" role="alert">
                <strong>Success!</strong> <c:out value="${sessionScope.successMessage}" />
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
            </div>
            <%-- Remove the attribute from the session so it doesn't show again --%>
            <c:remove var="successMessage" scope="session" />
        </c:if>

        <table class="table table-striped table-hover mt-4">
            <thead class="table-dark">
            <tr>
                <th>ID</th>
                <th>First Name</th>
                <th>Last Name</th>
                <th>Email</th>
                <th>Date of Birth</th>
                <th>Admission Date</th>
                <th style="width: 15%;">Actions</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="student" items="${listStudent}">
                <tr>
                    <td><c:out value="${student.studentId}" /></td>
                    <td><c:out value="${student.firstName}" /></td>
                    <td><c:out value="${student.lastName}" /></td>
                    <td><c:out value="${student.email}" /></td>
                    <td><c:out value="${student.dateOfBirth}" /></td>
                    <td><c:out value="${student.admissionDate}" /></td>
                    <td>
                        <a href="${pageContext.request.contextPath}/students/edit?id=<c:out value='${student.studentId}' />" class="btn btn-primary btn-sm">Edit</a>
                        <a href="${pageContext.request.contextPath}/students/delete?id=<c:out value='${student.studentId}' />" class="btn btn-danger btn-sm" onclick="return confirm('Are you sure you want to delete this student?')">Delete</a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>

<jsp:include page="/common/footer.jsp" />

