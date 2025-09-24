<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="pageTitle" value="Dashboard" scope="request" />
<jsp:include page="common/header.jsp" />

<div class="container mt-5">
    <div class="row">
        <%-- Admin Dashboard --%>
        <c:if test="${sessionScope.user.role == 'ADMIN'}">
            <div class="col-12">
                <div class="card">
                    <div class="card-header">
                        <h2>Admin Dashboard</h2>
                    </div>
                    <div class="card-body">
                        <p>Welcome, Admin! From here you can manage all aspects of the school.</p>
                        <hr>
                        <div class="list-group">
                            <a href="${pageContext.request.contextPath}/students" class="list-group-item list-group-item-action">Manage Students</a>
                            <a href="${pageContext.request.contextPath}/teachers" class="list-group-item list-group-item-action">Manage Teachers</a>
                            <a href="${pageContext.request.contextPath}/courses" class="list-group-item list-group-item-action">Manage Courses</a>
                            <a href="${pageContext.request.contextPath}/enrollments" class="list-group-item list-group-item-action">Manage Enrollments</a>
                            <a href="${pageContext.request.contextPath}/grades" class="list-group-item list-group-item-action">Manage All Grades</a>
                            <a href="${pageContext.request.contextPath}/fees" class="list-group-item list-group-item-action">Manage Student Fees</a>
                            <a href="${pageContext.request.contextPath}/salaries" class="list-group-item list-group-item-action">Manage Teacher Salaries</a>
                            <a href="${pageContext.request.contextPath}/reports" class="list-group-item list-group-item-action fw-bold">Generate Student Report Card</a>
                        </div>
                    </div>
                </div>
            </div>
        </c:if>

        <%-- Teacher Dashboard --%>
        <c:if test="${sessionScope.user.role == 'TEACHER'}">
            <div class="col-12">
                <div class="card">
                    <div class="card-header">
                        <h2>Teacher Dashboard</h2>
                    </div>
                    <div class="card-body">
                        <p>Welcome, Teacher! Here are your available actions.</p>
                        <hr>
                        <div class="list-group">
                            <a href="${pageContext.request.contextPath}/teacher/my-students" class="list-group-item list-group-item-action">View My Students</a>
                            <a href="${pageContext.request.contextPath}/teacher/attendance" class="list-group-item list-group-item-action">Manage Attendance</a>
                            <a href="${pageContext.request.contextPath}/teacher/grades" class="list-group-item list-group-item-action">Manage My Grades</a>
                        </div>
                    </div>
                </div>
            </div>
        </c:if>

        <%-- Student Dashboard --%>
        <c:if test="${sessionScope.user.role == 'STUDENT'}">
            <div class="col-12">
                <div class="card">
                    <div class="card-header">
                        <h2>Student Dashboard</h2>
                    </div>
                    <div class="card-body">
                        <p>Welcome, Student! Here is your personal information.</p>
                        <hr>
                        <div class="list-group">
                            <a href="${pageContext.request.contextPath}/student/my-attendance" class="list-group-item list-group-item-action">View My Attendance</a>
                            <a href="${pageContext.request.contextPath}/student/my-grades" class="list-group-item list-group-item-action">View My Grades</a>
                            <a href="${pageContext.request.contextPath}/student/my-fees" class="list-group-item list-group-item-action">View My Fees</a>
                        </div>
                    </div>
                </div>
            </div>
        </c:if>
    </div>
</div>

<jsp:include page="common/footer.jsp" />

