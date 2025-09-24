<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>EduManage | ${param.pageTitle}</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            background-color: #f8f9fa;
        }
        .navbar {
            box-shadow: 0 2px 4px rgba(0,0,0,.1);
        }
        .container {
            margin-top: 2rem;
            margin-bottom: 2rem;
        }
        .card {
            border: none;
            border-radius: .75rem;
            box-shadow: 0 4px 6px rgba(0,0,0,.1);
        }
    </style>
</head>
<body>

<nav class="navbar navbar-expand-lg navbar-light bg-white">
    <div class="container-fluid">
        <a class="navbar-brand" href="${pageContext.request.contextPath}/index.jsp">EduManage</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav ms-auto">
                <c:if test="${sessionScope.user != null}">
                    <li class="nav-item">
                        <span class="navbar-text me-3">
                            Welcome, <strong><c:out value="${sessionScope.user.username}"/></strong> (<c:out value="${sessionScope.user.role}"/>)
                        </span>
                    </li>
                    <li class="nav-item">
                        <a class="btn btn-outline-danger" href="${pageContext.request.contextPath}/logout">Logout</a>
                    </li>
                </c:if>
            </ul>
        </div>
    </div>
</nav>

<div class="container">