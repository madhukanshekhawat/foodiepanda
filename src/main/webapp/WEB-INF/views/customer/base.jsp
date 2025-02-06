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

       <div class = "menu-container">
          <h3>Quick Picks To Explore</h3>
          <div id="menuItemsContainer" class="menu-container"></div>

          <!-- Pagination -->
            <div class="text-center">
                <button id="prev" class="btn btn-primary">Previous</button>
                <span id="currentPage">Page 1</span>
                <button id="nex" class="btn btn-primary">Next</button>
            </div>
        </div>

        <!-- Modal Popup HTML -->
                    <div id="menuItemModal" class="modal">
                        <div class="modal-content">
                            <span class="close">&times;</span>
                            <img id="modalImage" src="" alt="Menu Item" style="width: 100%; height: 250px;"/>
                            <h4 id="modalName"></h4>
                            <p id="modalDescription"></p>
                            <p id="modalPrice"></p>
                            <div>
                                <button id="decreaseQuantity">-</button>
                                <input type="number" id="quantity" value="1" min="1" readonly>
                                <button id="increaseQuantity">+</button>
                            </div>
                            <button class="addToCart">Add to Cart</button>
                        </div>
                    </div>
                    <%@ include file="/WEB-INF/views/footer.jsp" %>
       <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
       <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.2/dist/js/bootstrap.bundle.min.js"></script>
    <script src="/static/js/customer-dashboard.js"></script>
   </body>
   </html>

