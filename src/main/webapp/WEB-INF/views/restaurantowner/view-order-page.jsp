<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/restaurantowner/base.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Orders</title>
    <link rel="stylesheet" href="/static/css/view-restaurant-orders.css">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body>
    <div class="container">
        <h1>Orders</h1>
        <div class="search-container">
            <input type="text" id="searchStatus" placeholder="Search by status...">
            <button id="searchButton">Search</button>
        </div>
        <div id="ordersContainer">
            <!-- Orders will be dynamically loaded here -->
        </div>
    </div>

    <script src="/static/js/view-restaurant-order.js"></script>
</body>
</html>
