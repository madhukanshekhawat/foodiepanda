<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/restaurantowner/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>View Categories</title>
    <link rel="stylesheet" href="/static/css/view-category.css">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body>
<div class="table-container">
    <h2>All Categories</h2>
    <div id="categoriesContainer">
        <!-- Dynamic table will be injected here -->
    </div>
</div>
<script src="/static/js/view-category.js"></script>
</body>
</html>