let currentPage = 0;
const pageSize = 6;
let searchQuery = "";
let restaurantData = null;

     $(document).ready(function() {
         // Hide the search input and button
         $("#searchInput").hide();
         $("#nav-searchBtn").hide();
     });

function loadMenuItems(page) {
    $.ajax({
        url: "/menu/available/" + page + "/size/" + pageSize,
        type: "GET",
        data: { name: searchQuery },
        success: function (data) {
            $("#menuItemsContainer").empty();

            data.forEach(item => {
                let cardClass = item.restaurantAvailable ? 'menu-cardss' : 'menu-cardss gray';
                const card = `
                    <div class="${cardClass}" data-id="${item.id}" data-restaurant-id="${item.restaurantId}">
                        <img src="data:image/jpeg;base64,${item.itemImage}" alt="${item.name}" style="width: 100%; height: 250px;"/>
                        <h5>${item.name}</h5>
                        <p>Price: ₹${item.price}</p>
                        <p>${item.category}</p>
                        <p class="restro-name">${item.restaurantName}</p>
                    </div>
                `;
                $("#menuItemsContainer").append(card);
            });

            // Update pagination buttons
            $("#currentPage").text(page + 1);
            $("#prev").prop("disabled", page === 0);
            $("#nex").prop("disabled", data.last);
        }
    });
}

function fetchRestaurantDetails(restaurantId) {
    $.ajax({
        url: "/api/restaurant/detail/" + restaurantId,
        method: "GET",
        success: function(data) {
            // Store restaurant data in local storage
            localStorage.setItem("restaurantData", JSON.stringify(data));
            // Redirect to the restaurant detail page
            window.location.href = "/api/customer/restaurant-detail";
        },
        error: function(xhr, status, error) {
            console.error("Error loading restaurant details:", status, error);
        }
    });
}

function fetchRestaurants(page) {
    $.ajax({
        url: "/api/customer/restaurant/page/" + page + "/size/" + pageSize,
        type: "GET",
        success: function (response) {
            $("#restaurant-cards").empty();

            response.content.forEach(restaurant => {
                let cardClass = restaurant.available ? 'restaurant-card' : 'restaurant-card gray';
                let card = `<div class='${cardClass}' data-id="${restaurant.restaurantId}">
                                <img src="data:image/jpeg;base64,${restaurant.image}" alt="${restaurant.name}" style="width: 100%; height: 250px;"/>
                                <p class="restaurant-name">${restaurant.name}</p>
                                <p class="restaurant-address">${restaurant.address}</p>
                                <p class="availability-status">${restaurant.available ? 'Available' : 'Not Available'}</p>
                            </div>`;
                $("#restaurant-cards").append(card);
            });

            // Update pagination buttons
            $("#currentPage").text(page + 1);
            $("#prevPage").prop("disabled", page === 0);
            $("#nextPage").prop("disabled", response.last);
        }
    });
}

// Pagination for menu items
$("#prev").click(function () {
    if (currentPage > 0) {
        currentPage--;
        loadMenuItems(currentPage);
    }
});

$("#nex").click(function () {
    currentPage++;
    loadMenuItems(currentPage);
});

// Pagination for restaurants
$("#prevPage").click(function () {
    if (currentPage > 0) {
        currentPage--;
        fetchRestaurants(currentPage);
    }
});

$("#nextPage").click(function () {
    currentPage++;
    fetchRestaurants(currentPage);
});

// Initialize on document ready
$(document).ready(function () {
    loadMenuItems(currentPage);
    fetchRestaurants(currentPage);

    $(document).on("input", "#searchInput", function(){
        searchQuery = $(this).val().trim();
        loadMenuItems(0);
    });

    $(document).on("click", ".restaurant-card", function() {
        const restaurantId = $(this).data("id");
        fetchRestaurantDetails(restaurantId);
    });

    // On the restaurant detail page
    const storedRestaurantData = localStorage.getItem("restaurantData");
    if (storedRestaurantData) {
        restaurantData = JSON.parse(storedRestaurantData);

        $("#restaurantName").text(restaurantData.name);
        $("#restaurantAddress").text(restaurantData.address);
        $("#availability").text(restaurantData.available ? "Open" : "Closed");
        $("#startTime").text(restaurantData.availabilityStartTime);
        $("#endTime").text(restaurantData.availabilityEndTime);

        // Optionally, you can also display the menu items if they are part of the restaurant data
        if (restaurantData.menuItems) {
            restaurantData.menuItems.forEach(item => {
                const card = `
                    <div class="menu-card" data-id="${item.id}" data-restaurant-id="${restaurantData.restaurantId}">
                        <img src="data:image/jpeg;base64,${item.image}" alt="${item.name}" style="width: 100%; height: 250px;"/>
                        <h5>${item.name}</h5>
                        <p>Price: ₹${item.price}</p>
                        <p>${item.categoryName}</p>
                    </div>
                `;
                $("#menuItems").append(card);
            });
        }
    } else {
        alert("No restaurant data found.");
    }

    // Modal popup functionality
    $(document).on("click", ".menu-card", function() {
        const itemId = $(this).data("id");
        const restaurantId = $(this).data("restaurant-id");
        const item = restaurantData.menuItems.find(item => item.id === itemId);

        if (item) {
            $("#modalImage").attr("src", "data:image/jpeg;base64," + item.image);
            $("#modalName").text(item.name);
            $("#modalDescription").text(item.description);
            $("#modalPrice").text("Price: ₹" + item.price);
            $("#quantity").val(1);

            // Set data attributes for the Add to Cart button
            $(".addToCart").data("id", item.id);
            $(".addToCart").data("name", item.name);
            $(".addToCart").data("price", item.price);
            $(".addToCart").data("image", item.image);
            $(".addToCart").data("restaurant-id",restaurantId);

            $("#menuItemModal").show();
        }
    });

    // Close the modal
    $(".close").click(function() {
        $("#menuItemModal").hide();
    });

    // Increase quantity
    $("#increaseQuantity").click(function() {
        let quantity = parseInt($("#quantity").val());
        $("#quantity").val(quantity + 1);
    });

    // Decrease quantity
    $("#decreaseQuantity").click(function() {
        let quantity = parseInt($("#quantity").val());
        if (quantity > 1) {
            $("#quantity").val(quantity - 1);
        }
    });

    $(document).on("click", ".addToCart", function () {
        const menuItemId = $(this).data("id");
        const quantity = parseInt($("#quantity").val());
        const restaurantId = $(this).data("restaurant-id");

        let cart = JSON.parse(localStorage.getItem("cart")) || {};
        let cartRestaurantId = localStorage.getItem("cartRestaurantId");

        if (cartRestaurantId && cartRestaurantId !== restaurantId.toString()) {
            if (confirm("Your cart contains items from a different restaurant. Do you want to replace them?")) {
                // Replace the cart with the new item
                cart = {};
                cart[menuItemId] = quantity;
                localStorage.setItem("cart", JSON.stringify(cart));
                localStorage.setItem("cartRestaurantId", restaurantId);
                alert("Cart items replaced with items from the new restaurant.");
            } else {
                // Do not add the new item to the cart
                alert("Action cancelled. The item was not added to the cart.");
                return;
            }
        } else {
            if (cart[menuItemId]) {
                cart[menuItemId] += quantity;
            } else {
                cart[menuItemId] = quantity;
            }
            localStorage.setItem("cart", JSON.stringify(cart));
            localStorage.setItem("cartRestaurantId", restaurantId);
        }

        alert("Added to cart: " + quantity + " items");
        $("#menuItemModal").hide();

    });
});
