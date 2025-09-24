<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="pageTitle" value="Manage Grades" scope="request" />
<jsp:include page="common/header.jsp" />

<div class="card">
    <div class="card-body">
        <div class="d-flex justify-content-between align-items-center mb-3">
            <h1 class="card-title">Manage Grades for: <c:out value="${course.courseName}"/></h1>
            <a href="${pageContext.request.contextPath}/teacher/grades" class="btn btn-secondary">Change Course</a>
        </div>
        <hr>

        <form action="${pageContext.request.contextPath}/teacher/save-grades" method="post">
            <input type="hidden" name="courseId" value="${course.courseId}" />

            <table class="table table-striped table-hover mt-4">
                <thead class="table-dark">
                <tr>
                    <th>Student Name</th>
                    <th>Current Grade</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="enrollment" items="${enrollmentList}">
                    <tr>
                        <td><c:out value="${enrollment.studentName}"/></td>
                        <td>
                            <input type="number" class="form-control"
                                   name="grade_${enrollment.enrollmentId}"
                                   value="${existingGrades[enrollment.enrollmentId]}"
                                   step="0.01" min="0" max="100"
                                   placeholder="Enter grade">
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>

            <div class="d-flex justify-content-end mt-4">
                <button type="submit" class="btn btn-primary">Save Grades</button>
            </div>
        </form>
    </div>
</div>

<jsp:include page="common/footer.jsp" />

