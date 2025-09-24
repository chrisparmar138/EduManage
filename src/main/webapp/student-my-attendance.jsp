<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="pageTitle" value="My Attendance" scope="request" />
<jsp:include page="common/header.jsp" />

<div class="card">
    <div class="card-body">
        <div class="d-flex justify-content-between align-items-center mb-3">
            <h1 class="card-title">My Attendance Record</h1>
            <a href="${pageContext.request.contextPath}/" class="btn btn-secondary">Back to Dashboard</a>
        </div>
        <hr>

        <table class="table table-striped table-hover mt-4">
            <thead class="table-dark">
            <tr>
                <th>Date</th>
                <th>Course</th>
                <th>Status</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="attendance" items="${attendanceList}">
                <tr>
                    <td><c:out value="${attendance.attendanceDate}" /></td>
                    <td><c:out value="${attendance.courseName}" /></td>
                    <td>
                        <c:if test="${attendance.status == 'PRESENT'}">
                            <span class="badge bg-success">Present</span>
                        </c:if>
                        <c:if test="${attendance.status == 'ABSENT'}">
                            <span class="badge bg-danger">Absent</span>
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>

<jsp:include page="common/footer.jsp" />

