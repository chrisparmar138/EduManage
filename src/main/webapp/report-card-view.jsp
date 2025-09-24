<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="pageTitle" value="Student Report Card" scope="request" />
<jsp:include page="/common/header.jsp" />

<style>
    /* These styles will only be applied when printing the page */
    @media print {
        /* Hide any element with the 'no-print' class */
        .no-print {
            display: none !important;
        }
        /* Hide the main navigation header during printing */
        .navbar {
            display: none !important;
        }
        /* Ensure the card takes up the full width and has no shadow when printed */
        .card {
            box-shadow: none !important;
            border: none !important;
        }
    }
</style>

<div class="card">
    <div class="card-body">
        <%-- Added the 'no-print' class to this div to hide it during printing --%>
        <div class="d-flex justify-content-between align-items-center mb-3 no-print">
            <h1 class="card-title">Student Report Card</h1>
            <div>
                <a href="${pageContext.request.contextPath}/reports" class="btn btn-secondary">Select Another Student</a>
                <button onclick="window.print()" class="btn btn-info">Print Report</button>
            </div>
        </div>
        <hr class="no-print">

        <%-- This title will only appear on the printed version --%>
        <div class="d-none d-print-block text-center mb-4">
            <h1>Student Report Card</h1>
        </div>


        <%-- Student Information Section --%>
        <div class="mt-4">
            <h3>Student Information</h3>
            <table class="table table-bordered">
                <tbody>
                <tr>
                    <th style="width: 20%;">Full Name</th>
                    <td><c:out value="${student.firstName} ${student.lastName}" /></td>
                </tr>
                <tr>
                    <th>Email</th>
                    <td><c:out value="${student.email}" /></td>
                </tr>
                <tr>
                    <th>Date of Birth</th>
                    <td><c:out value="${student.dateOfBirth}" /></td>
                </tr>
                <tr>
                    <th>Admission Date</th>
                    <td><c:out value="${student.admissionDate}" /></td>
                </tr>
                </tbody>
            </table>
        </div>

        <%-- Grades Section --%>
        <div class="mt-5">
            <h3>Academic Performance</h3>
            <table class="table table-striped table-hover">
                <thead class="table-dark">
                <tr>
                    <th>Course Name</th>
                    <th>Grade</th>
                    <th>Date Recorded</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="grade" items="${gradeList}">
                    <tr>
                        <td><c:out value="${grade.courseName}" /></td>
                        <td><c:out value="${grade.grade}" /></td>
                        <td><c:out value="${grade.dateRecorded}" /></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>

        <%-- Attendance Summary Section --%>
        <div class="mt-5">
            <h3>Attendance Summary</h3>
            <table class="table table-bordered" style="width: 50%;">
                <tbody>
                <tr>
                    <th style="width: 30%;">Days Present</th>
                    <td><span class="badge bg-success fs-6">${attendanceSummary['PRESENT']}</span></td>
                </tr>
                <tr>
                    <th>Days Absent</th>
                    <td><span class="badge bg-danger fs-6">${attendanceSummary['ABSENT']}</span></td>
                </tr>
                </tbody>
            </table>
        </div>
    </div>
</div>

<jsp:include page="/common/footer.jsp" />

