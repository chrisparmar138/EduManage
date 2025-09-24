<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:set var="pageTitle" value="Add New Enrollment" scope="request" />
<jsp:include page="/common/header.jsp" />

<div class="card">
    <div class="card-body">
        <div class="d-flex justify-content-between align-items-center mb-3">
            <h1 class="card-title">Add New Enrollment</h1>
            <a href="${pageContext.request.contextPath}/enrollments" class="btn btn-secondary">Back to List</a>
        </div>
        <hr>

        <form action="${pageContext.request.contextPath}/enrollments/insert" method="post">

            <div class="mb-3">
                <label for="studentId" class="form-label">Select Student</label>
                <select class="form-select" id="studentId" name="studentId" required>
                    <option value="">-- Choose a Student --</option>
                    <c:forEach var="student" items="${listStudent}">
                        <option value="${student.studentId}">
                            <c:out value="${student.firstName} ${student.lastName}" />
                        </option>
                    </c:forEach>
                </select>
            </div>

            <div class="mb-3">
                <label for="courseId" class="form-label">Select Course</label>
                <select class="form-select" id="courseId" name="courseId" required>
                    <option value="">-- Choose a Course --</option>
                    <c:forEach var="course" items="${listCourse}">
                        <option value="${course.courseId}">
                            <c:out value="${course.courseName}" />
                        </option>
                    </c:forEach>
                </select>
            </div>

            <div class="mb-3">
                <label for="enrollmentDate" class="form-label">Enrollment Date</label>
                <input type="date" class="form-control" id="enrollmentDate" name="enrollmentDate" required>
            </div>

            <div class="d-flex justify-content-end mt-4">
                <button type="submit" class="btn btn-primary">Save Enrollment</button>
            </div>
        </form>
    </div>
</div>

<jsp:include page="/common/footer.jsp" />

