<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%-- Set the page title for the header --%>
<c:if test="${course != null}">
    <c:set var="pageTitle" value="Edit Course" scope="request" />
</c:if>
<c:if test="${course == null}">
    <c:set var="pageTitle" value="Add New Course" scope="request" />
</c:if>

<jsp:include page="/common/header.jsp" />

<div class="card">
    <div class="card-body">
        <div class="d-flex justify-content-between align-items-center mb-3">
            <h1 class="card-title">
                <c:if test="${course != null}">Edit Course</c:if>
                <c:if test="${course == null}">Add New Course</c:if>
            </h1>
            <a href="${pageContext.request.contextPath}/courses" class="btn btn-secondary">Back to List</a>
        </div>
        <hr>

        <form action="${pageContext.request.contextPath}/courses/${course != null ? 'update' : 'insert'}" method="post">

            <c:if test="${course != null}">
                <input type="hidden" name="id" value="<c:out value='${course.courseId}' />" />
            </c:if>

            <div class="row">
                <div class="col-md-6 mb-3">
                    <label for="courseName" class="form-label">Course Name</label>
                    <input type="text" class="form-control" id="courseName" name="courseName" value="<c:out value='${course.courseName}' />" required>
                </div>
                <div class="col-md-6 mb-3">
                    <label for="courseCode" class="form-label">Course Code</label>
                    <input type="text" class="form-control" id="courseCode" name="courseCode" value="<c:out value='${course.courseCode}' />" required>
                </div>
            </div>

            <div class="mb-3">
                <label for="credits" class="form-label">Credits</label>
                <input type="number" class="form-control" id="credits" name="credits" value="<c:out value='${course.credits}' />" required>
            </div>

            <div class="mb-3">
                <label for="teacherId" class="form-label">Assign Teacher</label>
                <select class="form-select" id="teacherId" name="teacherId" required>
                    <option value="">-- Select a Teacher --</option>
                    <c:forEach var="teacher" items="${listTeacher}">
                        <option value="${teacher.teacherId}" ${teacher.teacherId == course.teacherId ? 'selected' : ''}>
                            <c:out value="${teacher.firstName} ${teacher.lastName}" />
                        </option>
                    </c:forEach>
                </select>
            </div>

            <div class="d-flex justify-content-end mt-4">
                <button type="submit" class="btn btn-primary">Save Course</button>
            </div>
        </form>
    </div>
</div>

<jsp:include page="/common/footer.jsp" />

