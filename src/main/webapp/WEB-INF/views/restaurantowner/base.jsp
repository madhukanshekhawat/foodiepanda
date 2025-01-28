<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Restaurant Owner Dashboard</title>
    <link rel="stylesheet" href="/static/css/restaurantowner-dashboard.css">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body>
<div class="wrapper">
    <nav class="navbar">
        <div class="logo" id="restaurant-logo">My Restaurant</div>
        <ul class="nav-links">
            <li><a href="/api/restaurant/addMenu">New Dish</a></li>
            <li><a href="/api/restaurant/view-menu">Menu Overview</a></li>
            <li><a href="/api/restaurant/view-coupon">Discounts</a></li>
            <li><a href="/api/restaurant/add-category">Create Category</a></li>
            <li><a href="/api/restaurant/view-category">Category List</a></li>
            <li><a href="/api/restaurant/manage-order">Order Summary</a></li>
            <li><a href="/api/restaurant/view-profile">Timing</a></li>
            <div class="form-group">
                <label for="availabilityToggle">Available</label>
                <label class="switch">
                    <input type="checkbox" id="availabilityToggle">
                    <span class="slider"></span>
                </label>
            </div>
            <form id="logoutForm" action="/logout" method="POST" style="display:inline;">
            <button type="submit" class="logout-btn">Logout</button>
            <% if(request.getParameter("logout")!=null){%> alert: You have been logged out successfully
            <%} %>
            </form>
        </ul>
    </nav>
        </div>
    <script>
           $(document).ready(function() {
                       // Retrieve the state from local storage and set the toggle button
                       const isAvailable = localStorage.getItem('restaurantAvailability') === 'true';
                       $("#availabilityToggle").prop("checked", isAvailable);

                       $("#availabilityToggle").change(function () {
                           const isAvailable = $(this).prop("checked");
                           localStorage.setItem('restaurantAvailability', isAvailable); // Save the state in local storage
                           $.ajax({
                               url: "/api/restaurant/availability",
                               method: "PUT",
                               contentType: "application/json",
                               data: JSON.stringify({ available: isAvailable }),
                               success: function () {
                                   alert("Restaurant availability updated successfully.");
                               },
                               error: function () {
                                   alert("Failed to update availability.");
                                   $("#availabilityToggle").prop("checked", !isAvailable); // Revert toggle in case of failure
                               }
                           });
                       });

                       // Fetch the restaurant name from the API and set it
                       $.ajax({
                           url: "/api/restaurant/profile",
                           method: "GET",
                           success: function (data) {
                               $("#restaurant-logo").text(data.name);
                               $("#user-name").text("Hi, " + data.ownerDetails.firstName);
                           },
                           error: function () {
                               alert("Failed to fetch restaurant name.");
                           }
                       });

                       // Make the logo clickable to navigate to the dashboard
                       $("#restaurant-logo").click(function() {
                           window.location.href = "/api/restaurant/dashboard";
                       });
                   });
    </script>
</body>
</html>
