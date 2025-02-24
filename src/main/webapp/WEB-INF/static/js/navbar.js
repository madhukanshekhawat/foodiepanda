document.addEventListener('DOMContentLoaded', function() {
    let token = getCookie('JWT-TOKEN');
    let isLoggedIn = token !== null;

    function updateUI() {
        if (isLoggedIn) {
            document.getElementById('authButtons').style.display = 'none';
            document.getElementById('logoutForm').style.display = 'block';
            document.getElementById('orderSummaryLink').style.display = 'block';
            document.getElementById('showAddressLink').style.display = 'block';
            fetchCartItems();
        } else {
            document.getElementById('authButtons').style.display = 'flex';
            document.getElementById('logoutForm').style.display = 'none';
            document.getElementById('orderSummaryLink').style.display = 'none';
            document.getElementById('showAddressLink').style.display = 'none';
        }
    }

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

    function fetchCartItems() {
        // Check if the cart has already been checked after login
        if (localStorage.getItem("cartCheckedAfterLogin")) {
            console.log('Cart has already been checked after login.');
            return;
        }

        $.ajax({
            url: '/api/cart/items',
            method: 'GET',
            success: function(data) {
                let newCart = {};
                let newCartRestaurantId = null;

                data.forEach(item => {
                    newCart[item.menuItemId] = item.quantity;
                    newCartRestaurantId = item.restaurantId;
                });

                // Check if newCart contains items
                if (Object.keys(newCart).length > 0) {
                    let existingCart = JSON.parse(localStorage.getItem("cart"));
                    let existingCartRestaurantId = localStorage.getItem("cartRestaurantId");

                    if (existingCart && existingCartRestaurantId) {
                        if (existingCartRestaurantId === newCartRestaurantId) {
                            // Add quantities if menu item ID matches
                            for (let menuItemId in newCart) {
                                if (existingCart[menuItemId]) {
                                    existingCart[menuItemId] += newCart[menuItemId];
                                } else {
                                    existingCart[menuItemId] = newCart[menuItemId];
                                }
                            }
                            localStorage.setItem("cart", JSON.stringify(existingCart));
                        } else {
                            // Different restaurant ID, delete incoming data from DB
                            $.ajax({
                                url: '/api/cart/clear',
                                method: 'DELETE',
                                success: function() {
                                    console.log('Cart items from different restaurant deleted.');
                                },
                                error: function(xhr, status, error) {
                                    console.error('Error deleting cart items:', error);
                                }
                            });
                        }
                    } else {
                        // No existing cart, store new data
                        localStorage.setItem("cart", JSON.stringify(newCart));
                        localStorage.setItem("cartRestaurantId", newCartRestaurantId);
                    }
                } else {
                    console.log('No items in the new cart.');
                }

                // Set the flag to indicate the cart has been checked
                localStorage.setItem("cartCheckedAfterLogin", "true");
            },
            error: function(xhr, status, error) {
                console.error('Error fetching cart items:', error);
            }
        });
    }

    updateUI();

    document.getElementById('logoutForm').addEventListener('submit', function(event) {
        document.cookie = 'JWT-TOKEN=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;';
        isLoggedIn = false;
        updateUI();
    });

function renderRestaurantCards(restaurants) {
    const container = document.getElementById('restaurantCardsContainer');
    const messageContainer = document.getElementById('messageContainer');

    container.innerHTML = ''; // Clear previous data

    if (Array.isArray(restaurants) && restaurants.length > 0) {
        messageContainer.style.display = 'none';

        const template = document.getElementById('restaurantCardTemplate');

        restaurants.forEach((restaurant) => {
            if (!template) {
                console.error("Template with ID 'restaurantCardTemplate' not found!");
                return;
            }

            let card = document.importNode(template.content, true); // Clone the template content

            let nameElem = card.querySelector('.restaurant-name');
            let addressElem = card.querySelector('.restaurant-address');
            let imgElem = card.querySelector('.restaurant-image');
            let avaElem = card.querySelector('.availability-status');
            let cardElem = card.querySelector('.restaurant-card');

            if (nameElem && addressElem && imgElem && cardElem) {
                nameElem.textContent = restaurant.name;
                addressElem.textContent = restaurant.address;
                imgElem.src = `data:image/jpeg;base64,${restaurant.firstMenuItemImage}`;
                imgElem.alt = restaurant.name;
                avaElem.textContent = restaurant.available ? 'Available' : 'Not Available';

                // Store restaurantId in local storage
                card.querySelector('.restaurant-card').dataset.restaurantId = restaurant.restaurantId;

                if (!restaurant.available) {
                   cardElem.classList.add('unavailable');
                 }
            } else {
                console.error("One of the elements inside the template was not found!");
            }

            container.appendChild(card);
        });
    } else {
        messageContainer.style.display = 'block';
    }
}

// Click event to fetch restaurant details
$(document).on("click", ".restaurant-card", function () {
    const restaurantId = $(this).data("restaurantId");
    console.log("Clicked restaurant ID:", restaurantId); // Log the restaurant ID

    if (restaurantId === undefined) {
        console.error("Restaurant ID is undefined!");
        return;
    }

    // Store restaurantId in local storage
    localStorage.setItem("selectedRestaurantId", restaurantId);

    $.ajax({
        url: "/api/restaurant/detail/" + restaurantId,
        method: "GET",
        success: function (data) {
            if (data.available) {
                // Store restaurant data in local storage
                localStorage.setItem("restaurantData", JSON.stringify(data));
                // Redirect to the restaurant detail page
                window.location.href = "/api/customer/restaurant-detail";
            } else {
                alert("This restaurant is currently unavailable.");
            }
        },
        error: function (xhr, status, error) {
            console.error("Error fetching restaurant details:", error);
        }
    });
});

// Function to get restaurantId from local storage
function getRestaurantIdFromLocalStorage() {
    return localStorage.getItem("selectedRestaurantId");
}

    function renderNormalDashboard() {
        // Show all elements for the normal dashboard
        document.getElementById('normalDashboard').style.display = 'block';
        document.getElementById('searchResults').style.display = 'none';
        document.getElementById('footer').style.display = 'block';
        fetchAllRestaurants();
    }

    function renderSearchResults(restaurants) {
        // Hide normal dashboard elements and show search results
        document.getElementById('normalDashboard').style.display = 'none';
        document.getElementById('searchResults').style.display = 'block';
        document.getElementById('footer').style.display = 'none';
        renderRestaurantCards(restaurants);
    }

    function debounce(func, delay) {
        let debounceTimer;
        return function() {
            const context = this;
            const args = arguments;
            clearTimeout(debounceTimer);
            debounceTimer = setTimeout(() => func.apply(context, args), delay);
        };
    }

    document.getElementById('searchInput').addEventListener('input', debounce(function() {
        const query = this.value.toLowerCase();

        if (query.trim().length === 0) {
           location.reload();
        } else {
            $.ajax({
                url: '/api/customer/search-restaurants',
                method: 'GET',
                data: { query: query },
                success: function(response) {
                    renderSearchResults(response);
                },
                error: function(error) {
                    console.error('Error fetching search results:', error);
                }
            });
        }
    }, 300));

    loadMenuItems(currentPage);
    fetchRestaurants(currentPage);
});