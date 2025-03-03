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
            <li><a href="/restaurant/profile-page">Profile</a><li>
            <li><a href="/restaurant/view-menu">Menu Overview</a></li>
            <li><a href="/restaurant/view-coupon">Discounts</a></li>
            <li><a href="/restaurant/view-category">Category List</a></li>
            <li><a href="/restaurant/manage-order">Order Summary</a></li>
            <li><a href="/restaurant/change-availability">Timing</a></li>
            <div class="formm-group">
                <label for="availabilityToggle">Available</label>
                <label class="switch">
                    <input type="checkbox" id="availabilityToggle">
                    <span class="slider"></span>
                </label>
            </div>
            <div class="nameContainer"> Hi, <span id="ownerName"> Owners name </span> </div>
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
               $.ajax({
                       url: "/api/restaurant/availability",
                       method: "GET",
                       success: function(response) {
                           const isAvailable = response.available;
                           $("#availabilityToggle").prop("checked", isAvailable);
                       },
                       error: function() {
                           alert("Failed to retrieve availability status.");
                       }
                   });

                   $("#availabilityToggle").change(function() {
                       const isAvailable = $(this).prop("checked");
                       $.ajax({
                           url: "/api/restaurant/availability",
                           method: "PUT",
                           contentType: "application/json",
                           data: JSON.stringify({ available: isAvailable }),
                           success: function() {
                               alert("Restaurant availability updated successfully.");
                           },
                           error: function() {
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
                          $("#ownerName").text(data.ownerDetails.firstName);
                      },
                      error: function () {
                          alert("Failed to fetch restaurant name.");
                      }
                  });

                  // Make the logo clickable to navigate to the dashboard
                  $("#restaurant-logo").click(function() {
                      window.location.href = "/restaurant/dashboard";
                  });
               });
    </script>

</body>
</html>
