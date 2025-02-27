<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/restaurantowner/base.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Restaurant Dashboard</title>
    <style>
        /* Center the content and style the text */
        .content {
            display: flex;
            justify-content: center;
            align-items: center;
            height: 85vh; /* Full viewport height */
            text-align: center;
        }

        .content h1 {
            color: gray;
            font-weight: bold;
            font-size: 2rem; /* Adjust font size as needed */
        }
    </style>
</head>
<body>
    <div class="content" id="main-content">
        <h1>Welcome to Restaurant Panel</h1>
    </div>

        <!-- Footer -->
        <div id="footer">
            <%@ include file="/WEB-INF/views/footer.jsp" %>
        </div>
</body>
</html>