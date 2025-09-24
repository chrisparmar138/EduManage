<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:if test="${grade != null}">
    <c:set var="pageTitle" value="Edit Grade" scope="request" />
</c:if>
<c:if test="${grade == null}">
    <c:set var="pageTitle" value="Add New Grade" scope="request" />
</c:if>

<jsp:include page="/common/header.jsp" />

<div class="card">
    <div class="card-body">
        <div class="d-flex justify-content-between align-items-center mb-3">
            <h1 class="card-title">
                <c:if test="${grade != null}">Edit Grade</c:if>
                <c:if test="${grade == null}">Add New Grade</c:if>
            </h1>
            <a href="${pageContext.request.contextPath}/grades" class="btn btn-secondary">Back to List</a>
        </div>
        <hr>

        <form action="${pageContext.request.contextPath}/grades/${grade != null ? 'update' : 'insert'}" method="post">

            <c:if test="${grade != null}">
                <input type="hidden" name="gradeId" value="<c:out value='${grade.gradeId}' />" />
            </c:if>

            <div class="mb-3">
                <label for="enrollmentId" class="form-label">Select Student & Course (Enrollment)</label>
                <select class="form-select" id="enrollmentId" name="enrollmentId" required>
                    <option value="">-- Choose an Enrollment --</option>
                    <c:forEach var="enrollment" items="${enrollmentList}">
                        <option value="${enrollment.enrollmentId}" ${grade.enrollmentId == enrollment.enrollmentId ? 'selected' : ''}>
                            <c:out value="${enrollment.studentName} - ${enrollment.courseName}" />
                        </option>
                    </c:forEach>
                </select>
            </div>

            <div class="mb-3">
                <label for="grade" class="form-label">Grade</label>
                <input type="number" class="form-control" id="grade" name="grade" value="<c:out value='${grade.grade}' />" step="0.01" min="0" max="100" required>
            </div>

            <c:if test="${grade != null}">
                <div class="mb-3">
                    <label for="dateRecorded" class="form-label">Date Recorded</label>
                    <input type="date" class="form-control" id="dateRecorded" name="dateRecorded" value="<c:out value='${grade.dateRecorded}' />" required>
                </div>
            </c:if>

            <div class="d-flex justify-content-end mt-4">
                <button type="submit" class="btn btn-primary">Save Grade</button>
            </div>
        </form>
    </div>
</div>

<jsp:include page="/common/footer.jsp" />

