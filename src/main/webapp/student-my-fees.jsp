<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="pageTitle" value="My Fees" scope="request" />
<jsp:include page="common/header.jsp" />

<div class="card">
    <div class="card-body">
        <div class="d-flex justify-content-between align-items-center mb-3">
            <h1 class="card-title">My Fee Records</h1>
            <a href="${pageContext.request.contextPath}/" class="btn btn-secondary">Back to Dashboard</a>
        </div>
        <hr>

        <table class="table table-striped table-hover mt-4">
            <thead class="table-dark">
            <tr>
                <th>Description</th>
                <th>Amount Due</th>
                <th>Due Date</th>
                <th>Status</th>
                <th>Payment Date</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="fee" items="${feeList}">
                <tr>
                    <td><c:out value="${fee.description}" /></td>
                    <td>$<c:out value="${fee.amount}" /></td>
                    <td><c:out value="${fee.dueDate}" /></td>
                    <td>
                        <c:if test="${fee.status == 'PAID'}">
                            <span class="badge bg-success">Paid</span>
                        </c:if>
                        <c:if test="${fee.status == 'UNPAID'}">
                            <span class="badge bg-danger">Unpaid</span>
                        </c:if>
                    </td>
                    <td><c:out value="${fee.paymentDate}" /></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>

<jsp:include page="common/footer.jsp" />

