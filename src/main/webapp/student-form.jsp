<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%-- Set the page title for the header --%>
<c:if test="${student != null}">
    <c:set var="pageTitle" value="Edit Student" scope="request" />
</c:if>
<c:if test="${student == null}">
    <c:set var="pageTitle" value="Add New Student" scope="request" />
</c:if>

<%-- Corrected the include path --%>
<jsp:include page="/common/header.jsp" />

<div class="card">
    <div class="card-body">
        <div class="d-flex justify-content-between align-items-center mb-3">
            <h1 class="card-title">
                <c:if test="${student != null}">Edit Student</c:if>
                <c:if test="${student == null}">Add New Student</c:if>
            </h1>
            <a href="${pageContext.request.contextPath}/students" class="btn btn-secondary">Back to List</a>
        </div>
        <hr>

        <form action="${pageContext.request.contextPath}/students/${student != null ? 'update' : 'insert'}" method="post">

            <c:if test="${student != null}">
                <input type="hidden" name="id" value="<c:out value='${student.studentId}' />" />
            </c:if>

            <div class="row">
                <div class="col-md-6 mb-3">
                    <label for="firstName" class="form-label">First Name</label>
                    <input type="text" class="form-control" id="firstName" name="firstName" value="<c:out value='${student.firstName}' />" required>
                </div>
                <div class="col-md-6 mb-3">
                    <label for="lastName" class="form-label">Last Name</label>
                    <input type="text" class="form-control" id="lastName" name="lastName" value="<c:out value='${student.lastName}' />" required>
                </div>
            </div>

            <div class="mb-3">
                <label for="email" class="form-label">Email Address</label>
                <input type="email" class="form-control" id="email" name="email" value="<c:out value='${student.email}' />" required>
            </div>

            <div class="row">
                <div class="col-md-6 mb-3">
                    <label for="dateOfBirth" class="form-label">Date of Birth</label>
                    <input type="date" class="form-control" id="dateOfBirth" name="dateOfBirth" value="<c:out value='${student.dateOfBirth}' />" required>
                </div>
                <div class="col-md-6 mb-3">
                    <label for="admissionDate" class="form-label">Admission Date</label>
                    <input type="date" class="form-control" id="admissionDate" name="admissionDate" value="<c:out value='${student.admissionDate}' />" required>
                </div>
            </div>

            <div class="d-flex justify-content-end mt-4">
                <button type="submit" class="btn btn-primary">Save Student</button>
            </div>
        </form>
    </div>
</div>

<%-- Corrected the include path --%>
<jsp:include page="/common/footer.jsp" />

