let currentPage = 0;
const pageSize = 6;

// Function to load menu items
function loadMenuItems(page) {
    $.ajax({
        url: "/menu/available/" + page + "/size/" + pageSize,
        type: "GET",
        success: function (data) {
             $("#menuItemsContainer").empty();

                data.forEach(item => {
                    let cardClass = item.restaurantAvailable ? 'menu-card' : 'menu-card gray';
                    const card = `
                        <div class="${cardClass}" data-id="${item.id}">
                            <img src="data:image/jpeg;base64,${item.itemImage}" alt="${item.name}" style="width: 350px; height: 250px;"/>
                            <h5>${item.name}</h5>
                            <p>Price: ₹${item.price}</p>
                            <p class = "restro-name">${item.restaurantName}</p>
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


// Function to fetch restaurants
function fetchRestaurants(page) {
    $.ajax({
        url: "/api/customer/restaurant/page/" + page + "/size/" + pageSize,
        type: "GET",
        success: function (response) {
            $("#restaurant-cards").empty();

            response.content.forEach(restaurant => {
                let cardClass = restaurant.available ? 'restaurant-card' : 'restaurant-card gray';
                let card = `<div class='${cardClass}' ${restaurant.available ? 'onclick="window.location.href=\'/restaurant/main-page\'"' : ''}>
                                <img src="data:image/jpeg;base64,${restaurant.image}" alt="${restaurant.name}" style="width: 350px; height: 250px;"/>
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
});