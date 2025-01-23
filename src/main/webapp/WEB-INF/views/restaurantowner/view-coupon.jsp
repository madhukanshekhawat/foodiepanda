<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/restaurantowner/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>View Coupons</title>
</head>
<body>
    <h2>Your Restaurant's Coupons</h2>
    <table border="1" id="couponsTable">
        <thead>
            <tr>
                <th>Code</th>
                <th>Discount Percentage</th>
                <th>Valid From</th>
                <th>Valid To</th>
                <th>Min Order Value</th>
                <th>Usage Limit</th>
                <th>Action</th>
            </tr>
        </thead>
        <tbody>
            <!-- Rows will be dynamically populated by JavaScript -->
        </tbody>
    </table>

    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="/static/js/view-coupon.js"></script>
</body>
</html>

