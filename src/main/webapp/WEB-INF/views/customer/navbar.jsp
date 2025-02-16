<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Foodie Panda</title>
    <link rel="stylesheet" href="/static/css/customer-dashboard.css">
</head>
<body>
    <!-- Navbar -->
    <nav class="fp-navbar">
        <div class="fp-container">
            <!-- Logo -->
            <a class="fp-navbar-brand" href="/api/customer/dashboard">
                Foodie Panda
            </a>

            <!-- Navbar content -->
            <div class="fp-navbar-content">
                <div class="search">
                    <form class="fp-navform">
                        <!-- Search Bar -->
                        <input id="searchInput" class="fp-search-bar" type="search" placeholder="Search" aria-label="Search">
                        <button id="nav-searchBtn" class="fp-success" type="submit">Search</button>
                    </form>
                </div>
                <!-- Login & Signup Buttons -->
                <div class="fp-d-flex" id="authButtons">
                    <a href="/api/user-login" class="fp-login">Login</a>
                    <a href="/api/registration" class="fp-signup">Signup</a>
                </div>
                <form id="logoutForm" action="/logout" method="POST" style="display: none;">
                    <button type="submit" class="fp-logout-bttn">Logout</button>
                </form>

                <div class="fp-d-flex fp-align-items-center">
                    <a href="/api/customer/order-summary" class="fp-order-summary" id="orderSummaryLink" style="display: none;">Order Summary</a>
                    <a href="/api/customer/show-address" class="fp-order-summary" id="showAddressLink" style="display: none;">Show Addresses</a>
                    <a href="/api/customer/cart" class="fp-cart">Cart</a>
                </div>
            </div>

            <div id="searchResults" class="fp-search-results"></div>
        </div>
    </nav>
    <script src="/static/js/navbar.js"></script>
    <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
    <script>
        document.addEventListener('DOMContentLoaded', function() {
            // Check login state by verifying JWT token in cookies
            let token = getCookie('JWT-TOKEN');
            let isLoggedIn = token !== null;

            // Function to update UI based on login state
            function updateUI() {
                if (isLoggedIn) {
                    document.getElementById('authButtons').style.display = 'none';
                    document.getElementById('logoutForm').style.display = 'block';
                    document.getElementById('orderSummaryLink').style.display = 'block';
                    document.getElementById('showAddressLink').style.display = 'block';
                } else {
                    document.getElementById('authButtons').style.display = 'flex';
                    document.getElementById('logoutForm').style.display = 'none';
                    document.getElementById('orderSummaryLink').style.display = 'none';
                    document.getElementById('showAddressLink').style.display = 'none';
                }
            }

            // Function to get cookie by name
            function getCookie(name) {
                let cookieArr = document.cookie.split(";");
                for (let i = 0; i < cookieArr.length; i++) {
                    let cookiePair = cookieArr[i].split("=");
                    if (name === cookiePair[0].trim()) {
                        return decodeURIComponent(cookiePair[1]);
                    }
                }
                return null;
            }

            // Call updateUI on page load
            updateUI();

            // Handle logout
            document.getElementById('logoutForm').addEventListener('submit', function(event) {
                event.preventDefault();
                document.cookie = 'JWT-TOKEN=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;';
                isLoggedIn = false;
                updateUI();
            });
        });
    </script>
</body>
</html>