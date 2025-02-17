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
           <a class="fp-navbar-brand" href="/api/customer/dashboard"> Foodie Panda </a>

            <!-- Navbar content -->
            <div class="fp-navbar-content">
                <div class="search">
                    <form class="fp-navform" id="searchForm">
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

    <!-- Message Container -->
        <div id="messageContainer" class="fp-container" style="display: none;">
            <p>Oops! No such restaurant found!</p>
        </div>

    <!-- Restaurant Cards Container -->
    <div id="restaurantCardsContainer" class="fp-container"></div>

    <!-- Restaurant Card Template -->
    <template id="restaurantCardTemplate">
    <div class="restaurant-card">
        <img class="restaurant-image" src="" alt="Restaurant Image" style="width: 100%; height: 250px;"/>
        <h3 class="restaurant-name"></h3>
        <p class="restaurant-menu"></p>
        <p class="restaurant-address"></p>
        <p class="availability-status"></p>
    </div>
</template>

    <script src="/static/js/navbar.js"></script>
    <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
</body>
</html>