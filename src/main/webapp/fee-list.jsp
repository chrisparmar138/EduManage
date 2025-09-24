<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="pageTitle" value="Manage Student Fees" scope="request" />
<jsp:include page="/common/header.jsp" />

<div class="card">
    <div class="card-body">
        <div class="d-flex justify-content-between align-items-center mb-3">
            <h1 class="card-title">Student Fee Management</h1>
            <a href="${pageContext.request.contextPath}/fees/new" class="btn btn-success">Add New Fee Record</a>
        </div>
        <hr>

        <table class="table table-striped table-hover mt-4">
            <thead class="table-dark">
            <tr>
                <th>Fee ID</th>
                <th>Student Name</th>
                <th>Description</th>
                <th>Amount (₹)</th>
                <th>Due Date</th>
                <th>Status</th>
                <th>Actions</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="fee" items="${feeList}">
                <tr>
                    <td><c:out value="${fee.feeId}" /></td>
                    <td><c:out value="${fee.studentName}" /></td>
                    <td><c:out value="${fee.description}" /></td>
                    <td>₹<c:out value="${fee.amount}" /></td>
                    <td><c:out value="${fee.dueDate}" /></td>
                    <td>
                            <%-- Corrected the check to use .name() --%>
                        <c:if test="${fee.status.name() == 'PAID'}">
                            <span class="badge bg-success">Paid</span>
                        </c:if>
                        <c:if test="${fee.status.name() == 'UNPAID'}">
                            <span class="badge bg-danger">Unpaid</span>
                        </c:if>
                    </td>
                    <td>
                            <%-- Corrected the check to use .name() --%>
                        <c:if test="${fee.status.name() == 'UNPAID'}">
                            <form action="${pageContext.request.contextPath}/fees/updateStatus" method="post" style="display:inline;">
                                <input type="hidden" name="feeId" value="${fee.feeId}" />
                                <button type="submit" class="btn btn-info btn-sm">Mark as Paid</button>
                            </form>
                            <a href="#" class="btn btn-warning btn-sm">Send Reminder</a>
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>

<jsp:include page="/common/footer.jsp" />

