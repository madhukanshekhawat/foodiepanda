<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/admin/base.jsp" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Approve Owners</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/approve-owners.css">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="/static/js/approve-owners.js"></script>
</head>
<body>
<div class="content">
    <h1>Approve Restaurant Owner</h1>

    <div class="table-container">
        <table >
            <thead>
                <tr>
                    <th>Owner Name</th>
                    <th>Restaurant Name</th>
                    <th>Phone Number</th>
                    <th>Email</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody id="owners-table">
                <!-- Data will be inserted here dynamically -->
            </tbody>
        </table>
    </div>
</div>
</body>
</html>
