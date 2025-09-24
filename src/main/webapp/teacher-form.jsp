<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%-- Set the page title for the header --%>
<c:if test="${teacher != null}">
    <c:set var="pageTitle" value="Edit Teacher" scope="request" />
</c:if>
<c:if test="${teacher == null}">
    <c:set var="pageTitle" value="Add New Teacher" scope="request" />
</c:if>

<%-- Corrected the include path --%>
<jsp:include page="/common/header.jsp" />

<div class="card">
    <div class="card-body">
        <div class="d-flex justify-content-between align-items-center mb-3">
            <h1 class="card-title">
                <c:if test="${teacher != null}">Edit Teacher</c:if>
                <c:if test="${teacher == null}">Add New Teacher</c:if>
            </h1>
            <a href="${pageContext.request.contextPath}/teachers" class="btn btn-secondary">Back to List</a>
        </div>
        <hr>

        <form action="${pageContext.request.contextPath}/teachers/${teacher != null ? 'update' : 'insert'}" method="post">

            <c:if test="${teacher != null}">
                <input type="hidden" name="id" value="<c:out value='${teacher.teacherId}' />" />
            </c:if>

            <div class="row">
                <div class="col-md-6 mb-3">
                    <label for="firstName" class="form-label">First Name</label>
                    <input type="text" class="form-control" id="firstName" name="firstName" value="<c:out value='${teacher.firstName}' />" required>
                </div>
                <div class="col-md-6 mb-3">
                    <label for="lastName" class="form-label">Last Name</label>
                    <input type="text" class="form-control" id="lastName" name="lastName" value="<c:out value='${teacher.lastName}' />" required>
                </div>
            </div>

            <div class="mb-3">
                <label for="email" class="form-label">Email Address</label>
                <input type="email" class="form-control" id="email" name="email" value="<c:out value='${teacher.email}' />" required>
            </div>

            <div class="mb-3">
                <label for="hireDate" class="form-label">Hire Date</label>
                <input type="date" class="form-control" id="hireDate" name="hireDate" value="<c:out value='${teacher.hireDate}' />" required>
            </div>

            <div class="d-flex justify-content-end mt-4">
                <button type="submit" class="btn btn-primary">Save Teacher</button>
            </div>
        </form>
    </div>
</div>

<%-- Corrected the include path --%>
<jsp:include page="/common/footer.jsp" />

