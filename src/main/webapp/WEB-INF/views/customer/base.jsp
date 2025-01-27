<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Foodie Panda</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css">
    <style>
       /* Navbar Styling */
       .navbar {
           background-color: #F5EFFF; /* Light background for navbar */
           box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.1); /* Subtle shadow */
           padding: 10px 20px;
       }

       /* Text Logo */
       .navbar-brand {
           font-size: 24px; /* Larger font for the logo */
           font-weight: bold; /* Bold text for emphasis */
           color: #A294F9; /* Primary theme color */
           text-decoration: none; /* Remove underline */
           transition: color 0.3s ease; /* Smooth hover effect */
       }

       .navbar-brand:hover {
           color: #7C68E8; /* Slightly darker purple on hover */
       }

       /* Search Bar */
       .search-bar {
           width: 300px; /* Default width */
           border-radius: 20px; /* Rounded corners */
           border: 1px solid #E5D9F2; /* Subtle border matching theme */
           padding: 8px 15px;
           background-color: #ffffff; /* White background for input */
           transition: width 0.3s ease, box-shadow 0.3s ease; /* Smooth expansion */
       }

       .search-bar:focus {
           width: 400px; /* Expand on focus */
           outline: none;
           box-shadow: 0 0 6px rgba(162, 148, 249, 0.5); /* Purple glow on focus */
       }

       /* Search Button */
       .btn-outline-success {
           border-radius: 20px; /* Rounded corners */
           color: #A294F9; /* Primary theme color */
           border-color: #A294F9;
           transition: background-color 0.3s ease, color 0.3s ease;
       }

       .btn-outline-success:hover {
           background-color: #A294F9; /* Purple fill on hover */
           color: #ffffff; /* White text on hover */
       }

       /* Login and Signup Buttons */
       .btn-outline-primary {
           border-radius: 20px; /* Rounded corners */
           color: #CDC1FF; /* Secondary theme color */
           border-color: #CDC1FF;
           transition: background-color 0.3s ease, color 0.3s ease;
       }

       .btn-outline-primary:hover {
           background-color: #CDC1FF; /* Purple fill on hover */
           color: #ffffff; /* White text on hover */
       }

       .btn-primary {
           border-radius: 20px; /* Rounded corners */
           background-color: #A294F9; /* Primary theme color */
           border-color: #A294F9;
           color: #ffffff; /* White text */
           transition: background-color 0.3s ease, box-shadow 0.3s ease;
       }

       .btn-primary:hover {
           background-color: #7C68E8; /* Slightly darker purple on hover */
           box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.2); /* Subtle shadow */
       }

       .image{
          height: 400px;
       }

       /* Responsive Adjustments */
       @media (max-width: 768px) {
           .search-bar {
               width: 100%; /* Full width for smaller screens */
               margin-bottom: 10px;
           }

           .navbar {
               padding: 10px;
           }

           .d-flex > a {
               width: 100%; /* Buttons take full width */
               margin-bottom: 5px; /* Add spacing between buttons */
           }

           .btn-outline-primary,
           .btn-primary {
               text-align: center;
           }
       }

       /* Navbar Toggle Button */
       .navbar-toggler {
           border: none; /* Clean look for toggle button */
       }

       .navbar-toggler-icon {
       background-image: url("data:image/svg+xml;charset=utf8,%3Csvg viewBox='0 0 30 30' xmlns='http://www.w3.org/2000/svg'%3E%3Cpath stroke='rgba%280, 0, 0, 0.5%29' stroke-width='2' d='M4 7h22M4 15h22M4 23h22'/%3E%3C/svg%3E");
       }
    </style>
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


    <div id="carouselExampleSlidesOnly" class="carousel slide" data-bs-ride="carousel">
      <div class="carousel-inner">
        <div class="carousel-item active">
          <img src="/static/images/CheeseBurger.jpg" class="d-block w-100 image" alt="...">
        </div>
      </div>
    </div>

       <!-- Restaurants Section -->
       <div class="container">
           <h3>Restaurants</h3>
           <div class="row" id="restaurant-cards"></div>

           <!-- Pagination -->
           <div class="text-center">
               <button id="prev-btn" class="btn btn-primary">Previous</button>
               <span id="page-number">Page 1</span>
               <button id="next-btn" class="btn btn-primary">Next</button>
           </div>
       </div>

       <!-- Menu Item Details Modal -->
       <div class="modal" id="menuItemModal" tabindex="-1" role="dialog">
           <div class="modal-dialog" role="document">
               <div class="modal-content">
                   <div class="modal-header">
                       <h5 class="modal-title">Menu Item Details</h5>
                       <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                           <span aria-hidden="true">&times;</span>
                       </button>
                   </div>
                   <div class="modal-body" id="menu-item-details">
                       <!-- Menu item details will be injected here -->
                   </div>
               </div>
           </div>
       </div>

       <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
       <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.2/dist/js/bootstrap.bundle.min.js"></script>

       <script>
           let currentPage = 0;
           const pageSize = 6;

           // Fetch restaurants with pagination
           function loadRestaurants(page) {
               $.get("/api/customer/restaurant" + page + "size=" + pageSize,
                function (data) {
                   $('#restaurant-cards').empty(); // Clear existing cards

                   // Create restaurant cards
                   data.content.forEach(restaurant => {
                       const restaurantCard = `
                           <div class="col-md-4">
                               <div class="restaurant-card ${restaurant.isAvailable ? '' : 'gray'}" data-id="${restaurant.id}">
                                   <div class="restaurant-name">${restaurant.name}</div>
                                   <div class="restaurant-address">${restaurant.address}</div>
                                   <div class="availability-status">${restaurant.isAvailable ? 'Available' : 'Not Available'}</div>
                               </div>
                           </div>
                       `;
                       $('#restaurant-cards').append(restaurantCard);
                   });

                   // Update pagination
                   updatePagination(data);
               });
           }

           // Update pagination buttons
           function updatePagination(data) {
               $('#page-number').text(`Page ${currentPage + 1}`);
               $('#prev-btn').prop('disabled', currentPage === 0);
               $('#next-btn').prop('disabled', !data.hasNext);
           }

           // Previous page
           $('#prev-btn').click(function() {
               if (currentPage > 0) {
                   currentPage--;
                   loadRestaurants(currentPage);
               }
           });

           // Next page
           $('#next-btn').click(function() {
               currentPage++;
               loadRestaurants(currentPage);
           });

           // Display menu item details on card click
           $(document).on('click', '.restaurant-card:not(.gray)', function() {
               const restaurantId = $(this).data('id');
               fetchMenuItemDetails(restaurantId);
           });

           // Fetch and display menu item details in modal
           function fetchMenuItemDetails(restaurantId) {
               $.get(`/api/customer/restaurant/${restaurantId}/menu`, function(menuItems) {
                   let menuDetailsHTML = '';
                   menuItems.forEach(item => {
                       menuDetailsHTML += `
                           <div>
                               <h5>${item.name}</h5>
                               <img src="${item.imageUrl}" alt="${item.name}" style="width: 100px; height: 100px;">
                               <p>${item.description}</p>
                               <p><strong>Price:</strong> $${item.price}</p>
                           </div>
                       `;
                   });

                   $('#menu-item-details').html(menuDetailsHTML);
                   $('#menuItemModal').modal('show');
               });
           }

           // Initial load
           loadRestaurants(currentPage);
       </script>
   </body>
   </html>

