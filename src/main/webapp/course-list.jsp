<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="pageTitle" value="Manage Courses" scope="request" />
<jsp:include page="common/header.jsp" />

<div class="card">
    <div class="card-body">
        <div class="d-flex justify-content-between align-items-center mb-3">
            <h1 class="card-title">Course Management</h1>
            <a href="${pageContext.request.contextPath}/courses/new" class="btn btn-primary">Add New Course</a>
        </div>
        <hr>

        <table class="table table-striped table-hover">
            <thead class="table-dark">
            <tr>
                <th>ID</th>
                <th>Course Name</th>
                <th>Course Code</th>
                <th>Credits</th>
                <th>Assigned Teacher</th>
                <th style="width: 15%;">Actions</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="course" items="${listCourse}">
                <tr>
                    <td><c:out value="${course.courseId}" /></td>
                    <td><c:out value="${course.courseName}" /></td>
                    <td><c:out value="${course.courseCode}" /></td>
                    <td><c:out value="${course.credits}" /></td>
                    <td><c:out value="${course.teacherName}" /></td>
                    <td>
                        <a href="${pageContext.request.contextPath}/courses/edit?id=<c:out value='${course.courseId}' />" class="btn btn-sm btn-warning">Edit</a>
                        &nbsp;
                        <a href="${pageContext.request.contextPath}/courses/delete?id=<c:out value='${course.courseId}' />" class="btn btn-sm btn-danger" onclick="return confirm('Are you sure you want to delete this course?');">Delete</a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>

<jsp:include page="common/footer.jsp" />

