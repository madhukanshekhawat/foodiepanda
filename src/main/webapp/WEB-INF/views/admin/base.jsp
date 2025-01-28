<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">    <link rel="stylesheet" href="/static/css/base.css">
    <title>Document</title>
</head>
<body>
<nav class="navbar">
  <div class="container-fluid">
    <a class="logo" href="#"><img src="/static/images/logo.png" alt="logo"/></a>
    <button class="navbar-toggler" id="navbarToggle"><i class="fa fa-ellipsis-v" aria-hidden="true"></i></button>
    <ul class="navbar-nav" id="navbarNav">
      <li class="nav-item">
        <a class="nav-link" id="homeLink" href="/admin/dashboard">Home</a>
      </li>
      <li class="nav-item">
        <a class="nav-link" id="viewRestaurantLink" href="/admin/view-restaurant">View Restaurants</a>
      </li>
      <li class="nav-item">
        <a class="nav-link" id="viewCustomerLink" href="/admin/view-customer">View Customers</a>
      </li>
      <li class="nav-item">
        <a class="nav-link" id="approveOwnerLink" href="/admin/approve-owners">Approve Owner</a>
      </li>
      <li class="nav-item">
        <a class="nav-link" id="createCouponLink" href="/admin/create-coupon">Create Coupon</a>
      </li>
      <li class="nav-item">
        <a class="nav-link" id="viewOwnerLink" href="/admin/approved-owners">View Owners</a>
      </li>
    </ul>
  </div>
</nav>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="/static/js/base.js"></script>
</body>
</html>