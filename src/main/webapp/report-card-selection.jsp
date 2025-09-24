<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="pageTitle" value="Select Student for Report" scope="request" />
<jsp:include page="common/header.jsp" />

<div class="card">
    <div class="card-body">
        <div class="d-flex justify-content-between align-items-center mb-3">
            <h1 class="card-title">Generate Student Report Card</h1>
            <a href="${pageContext.request.contextPath}/" class="btn btn-secondary">Back to Dashboard</a>
        </div>
        <hr>
        <p>Please select a student from the list below to view their report card.</p>

        <form action="${pageContext.request.contextPath}/reports/view" method="get" class="mt-4">
            <div class="row g-3 align-items-center">
                <div class="col-auto">
                    <label for="studentId" class="col-form-label">Select Student:</label>
                </div>
                <div class="col-md-6">
                    <select name="studentId" id="studentId" class="form-select" required>
                        <option value="">-- Choose a Student --</option>
                        <c:forEach var="student" items="${studentList}">
                            <option value="${student.studentId}">
                                <c:out value="${student.firstName} ${student.lastName}" />
                            </option>
                        </c:forEach>
                    </select>
                </div>
                <div class="col-auto">
                    <button type="submit" class="btn btn-primary">View Report</button>
                </div>
            </div>
        </form>
    </div>
</div>

<jsp:include page="common/footer.jsp" />
