<%@ include file="/WEB-INF/views/customer/navbar.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Foodie Panda</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="/static/css/customer-dashboard.css">
</head>
<body>
    <!-- Normal Dashboard -->
    <div id="normalDashboard">
        <!-- Restaurants Section -->
               <div class="container">
                   <h3>Restaurants</h3>
                   <div class="row" id="restaurant-cards"></div>

            <!-- Pagination -->
            <div class="text-center">
                <button id="prevPage" class="btn btn-primary">Previous</button>
                <span id="currentPage">Page 1</span>
                <button id="nextPage" class="btn btn-primary">Next</button>
            </div>
        </div>
    <!-- Search Results -->
    <div id="searchResults" style="display: none;">
        <div class="container">
            <h3>Search Results</h3>
            <div class="row" id="restaurantCardsContainer"></div>
        </div>
    </div>

    <!-- Footer -->
    <div id="footer">
        <%@ include file="/WEB-INF/views/footer.jsp" %>
    </div>

    <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.2/dist/js/bootstrap.bundle.min.js"></script>
    <script src="/static/js/customer-dashboard.js"></script>
    <script src="/static/js/login.js"></script>
</body>
</html>