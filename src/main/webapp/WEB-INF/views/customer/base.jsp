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
    <!-- Navbar -->
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
        <div class="container">
            <!-- Logo -->
            <a class="navbar-brand" href="#">
                Foodie Panda
            </a>

            <!-- Toggle button for mobile view -->
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarContent" aria-controls="navbarContent" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>

            <!-- Navbar content -->
            <div class="collapse navbar-collapse" id="navbarContent">
                <form class="d-flex mx-auto my-2 my-lg-0">
                    <!-- Search Bar -->
                    <input class="form-control me-2 search-bar" type="search" placeholder="Search" aria-label="Search">
                    <button class="btn btn-outline-success" type="submit">Search</button>
                </form>

                <!-- Login & Signup Buttons -->
                <div class="d-flex">
                    <a href="/api/user-login" class="btn btn-outline-primary me-2">Login</a>
                    <a href="/api/registration" class="btn btn-primary">Signup</a>
                </div>
            </div>
        </div>
    </nav>

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

       <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
       <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.2/dist/js/bootstrap.bundle.min.js"></script>
    <script src="/static/js/customer-dashboard.js"></script>
   </body>
   </html>

