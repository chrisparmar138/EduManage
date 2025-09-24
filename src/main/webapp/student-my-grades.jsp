<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="pageTitle" value="My Grades" scope="request" />
<jsp:include page="common/header.jsp" />

<div class="card">
    <div class="card-body">
        <div class="d-flex justify-content-between align-items-center mb-3">
            <h1 class="card-title">My Grades</h1>
            <a href="${pageContext.request.contextPath}/" class="btn btn-secondary">Back to Dashboard</a>
        </div>
        <hr>

        <table class="table table-striped table-hover mt-4">
            <thead class="table-dark">
            <tr>
                <th>Course</th>
                <th>Grade</th>
                <th>Date Recorded</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="grade" items="${gradeList}">
                <tr>
                    <td><c:out value="${grade.courseName}" /></td>
                    <td><c:out value="${grade.grade}" /></td>
                    <td><c:out value="${grade.dateRecorded}" /></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>

<jsp:include page="common/footer.jsp" />

