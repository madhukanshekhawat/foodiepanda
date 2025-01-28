<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/restaurantowner/base.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Menu Items</title>
    <link rel="stylesheet" href="/static/css/view-menu-item.css">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body>
    <div class="container">
        <h1>Menu Items</h1>
        <div class="search-container">
            <input type="text" id="searchMenuItem" placeholder="Search menu items...">
            <button id="searchButton">Search</button>
        </div>
        <div id="menuItemsContainer">
            <!-- Dynamic data will be injected here -->
        </div>
    </div>
<script src="/static/js/view-menu-item.js"></script>
</body>
</html>