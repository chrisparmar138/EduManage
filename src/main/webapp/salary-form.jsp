<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:set var="pageTitle" value="Add New Salary Record" scope="request" />
<jsp:include page="/common/header.jsp" />

<div class="card">
    <div class="card-body">
        <div class="d-flex justify-content-between align-items-center mb-3">
            <h1 class="card-title">Add New Salary Record</h1>
            <a href="${pageContext.request.contextPath}/salaries" class="btn btn-secondary">Back to List</a>
        </div>
        <hr>

        <form action="${pageContext.request.contextPath}/salaries/insert" method="post">

            <div class="mb-3">
                <label for="teacherId" class="form-label">Select Teacher</label>
                <select class="form-select" id="teacherId" name="teacherId" required>
                    <option value="">-- Choose a Teacher --</option>
                    <c:forEach var="teacher" items="${listTeachers}">
                        <option value="${teacher.teacherId}">
                            <c:out value="${teacher.firstName} ${teacher.lastName}" />
                        </option>
                    </c:forEach>
                </select>
            </div>

            <div class="row">
                <div class="col-md-6 mb-3">
                    <label for="amount" class="form-label">Salary Amount</label>
                    <div class="input-group">
                        <span class="input-group-text">$</span>
                        <input type="number" class="form-control" id="amount" name="amount" step="0.01" min="0" required>
                    </div>
                </div>
                <div class="col-md-6 mb-3">
                    <label for="payDate" class="form-label">Payment Date</label>
                    <input type="date" class="form-control" id="payDate" name="payDate" required>
                </div>
            </div>

            <div class="d-flex justify-content-end mt-4">
                <button type="submit" class="btn btn-primary">Save Salary Record</button>
            </div>
        </form>
    </div>
</div>

<jsp:include page="/common/footer.jsp" />

