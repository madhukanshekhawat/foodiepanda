<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/admin/base.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>Restaurant List</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/view-customers.css">

    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/view-customers.js"></script>
</head>
<body>

    <h2 class="customer-heading">Customers List</h2>

    <div class="customer-table-container">
        <table class="customer-table">
            <thead>
                <tr>
                    <th>Customer Name</th>
                    <th>Phone Number</th>
                </tr>
            </thead>
            <tbody id="customer-data">
                <!-- Data will be inserted here by JavaScript -->
            </tbody>
        </table>
    </div>

</body>
</html>