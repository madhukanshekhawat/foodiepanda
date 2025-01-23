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

    <nav class="navbar">
        <div class="logo">My Restaurant</div>
        <ul class="nav-links">
            <li><a href="/api/restaurant/addMenu">Add Menu Item</a></li>
            <li><a href="/api/restaurant/view-menu">View All Menu Items</a></li>
            <li><a href="/api/restaurant/view-coupon">View All Coupons</a></li>
            <li><a href="/api/restaurant/view-order">View All Orders</a></li>
            <li><a href="/api/restaurant/add-category">Add New Category</a></li>
            <li><a href="/api/restaurant/view-category">View Category</a></li>
        </ul>
    </nav>
</body>
</html>
