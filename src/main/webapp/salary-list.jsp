<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Manage Salaries</title>
    <style>
        body { font-family: Arial, sans-serif; background-color: #f8f9fa; margin: 0; padding: 20px; }
        .container { max-width: 900px; margin: 40px auto; padding: 20px; background-color: #fff; border-radius: 8px; box-shadow: 0 2px 4px rgba(0,0,0,0.1); }
        .header { display: flex; justify-content: space-between; align-items: center; border-bottom: 1px solid #dee2e6; padding-bottom: 10px; margin-bottom: 20px; }
        h1 { color: #343a40; }
        .btn { padding: 8px 15px; text-decoration: none; border-radius: 4px; color: white; display: inline-block; }
        .btn-primary { background-color: #007bff; }
        .btn-primary:hover { background-color: #0056b3; }
        .btn-secondary { background-color: #6c757d; }
        .btn-secondary:hover { background-color: #5a6268; }
        table { width: 100%; border-collapse: collapse; margin-top: 20px; }
        th, td { padding: 12px; border: 1px solid #dee2e6; text-align: left; }
        th { background-color: #e9ecef; }
    </style>
</head>
<body>
<div class="container">
    <div class="header">
        <h1>Teacher Salary Records</h1>
        <div>
            <a href="${pageContext.request.contextPath}/salaries/new" class="btn btn-primary">Add New Payment</a>
            <a href="${pageContext.request.contextPath}/" class="btn btn-secondary">Back to Dashboard</a>
        </div>
    </div>

    <table>
        <thead>
        <tr>
            <th>Teacher Name</th>
            <th>Amount</th>
            <th>Payment Date</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="salary" items="${listSalaries}">
            <tr>
                <td><c:out value="${salary.teacherName}" /></td>
                <td>$<c:out value="${salary.amount}" /></td>
                <td><c:out value="${salary.payDate}" /></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html>
