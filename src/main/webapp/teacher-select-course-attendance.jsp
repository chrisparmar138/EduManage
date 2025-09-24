<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="pageTitle" value="Select Course for Attendance" scope="request" />
<jsp:include page="common/header.jsp" />

<div class="card">
    <div class="card-body">
        <div class="d-flex justify-content-between align-items-center mb-3">
            <h1 class="card-title">Manage Attendance</h1>
            <a href="${pageContext.request.contextPath}/" class="btn btn-secondary">Back to Dashboard</a>
        </div>
        <hr>
        <h5 class="card-subtitle mb-2 text-muted">Please select a course to take or view attendance.</h5>

        <div class="list-group mt-4">
            <c:forEach var="course" items="${courseList}">
                <a href="${pageContext.request.contextPath}/teacher/attendance-form?courseId=${course.courseId}" class="list-group-item list-group-item-action">
                    <c:out value="${course.courseName}" /> (<c:out value="${course.courseCode}" />)
                </a>
            </c:forEach>
        </div>
    </div>
</div>

<jsp:include page="common/footer.jsp" />

