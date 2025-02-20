$(document).ready(function() {
    // Check if the user is logged in
    $.ajax({
        url: "/api/user/status",
        method: "GET",
        success: function(response) {
            if (response.loggedIn) {
                loadCartItemsFromAPI();
                loadAddresses();
                if (!localStorage.getItem("cartSynced")) {
                    transferLocalStorageToCart();
                }
            } else {
                loadCartItemsFromLocalStorage();
                alert("Please log in to view your cart.");
                window.location.href = "/api/user-login";
            }
        },
        error: function(xhr, status, error) {
            console.error("Error checking login status:", status, error);
            alert("Error checking login status.");
        }
    });

    function loadCartItemsFromAPI() {
            $.ajax({
                url: "/api/cart/items",
                method: "GET",
                success: function(cartItems) {
                    const localCart = JSON.parse(localStorage.getItem("cart")) || {};
                    if (cartItems.length === 0 && Object.keys(localCart).length === 0) {
                        // Both the cart in the database and local storage are empty
                        $("#cartItems").html("<p>Oops! No orders yet.</p>");
                        $("#addNewAddress").hide();
                        $("#addressContainer").hide();
                        $("#proceedButton").hide();
                        $("#totalAmount").hide();
                    } else {
                        syncCartItems(cartItems);
                    }
                },
                error: function(xhr, status, error) {
                    console.error("Error fetching cart items:", status, error);
                    alert("Error fetching cart items.");
                }
            });
        }

    function loadCartItemsFromLocalStorage() {
        const cart = JSON.parse(localStorage.getItem("cart")) || {};
        renderCartItems(cart);
    }

    function syncCartItems(apiCartItems) {
        const localCart = JSON.parse(localStorage.getItem("cart")) || {};
        if (Object.keys(localCart).length === 0) {
            renderCartItems(apiCartItems);
        } else {
            const restaurantId = Object.keys(localCart)[0];
            const sameRestaurant = apiCartItems.every(item => item.restaurantId === restaurantId);
            if (sameRestaurant) {
                const mergedCart = mergeCarts(apiCartItems, localCart);
                localStorage.setItem("cart", JSON.stringify(mergedCart));
                renderCartItems(mergedCart);
            } else {
                localStorage.setItem("cart", JSON.stringify(localCart));
                renderCartItems(localCart);
            }
        }
    }

    function getItemDetails(menuItemId) {
        let itemDetails = null;

        $.ajax({
            url: "/menu/" + menuItemId,
            method: "GET",
            async: false, // Make the request synchronous
            success: function(response) {
                itemDetails = response;
            },
            error: function(xhr, status, error) {
                console.error("Error fetching item details:", status, error);
                alert("Error fetching item details.");
            }
        });

        return itemDetails;
    }

    function mergeCarts(apiCartItems, localCart) {
        const mergedCart = {};

        // Add API cart items to mergedCart
        apiCartItems.forEach(apiItem => {
            mergedCart[apiItem.menuItemId] = apiItem.quantity;
        });

        // Add or update local cart items in mergedCart
        Object.keys(localCart).forEach(menuItemId => {
            if (mergedCart[menuItemId]) {
                mergedCart[menuItemId] = Math.max(mergedCart[menuItemId], localCart[menuItemId]);
            } else {
                mergedCart[menuItemId] = localCart[menuItemId];
            }
        });

        return mergedCart;
    }

    function getRestaurantDetails(restaurantId) {
        let restaurantDetails = null;

        $.ajax({
            url: "/api/restaurant/detail/" + restaurantId,
            method: "GET",
            async: false, // Make the request synchronous
            success: function(response) {
                restaurantDetails = response;
            },
            error: function(xhr, status, error) {
                console.error("Error fetching restaurant details:", status, error);
                alert("Error fetching restaurant details.");
            }
        });

        return restaurantDetails;
    }

     function renderCartItems(cart) {
         const cartItemsContainer = $("#cartItems");
         const totalAmountContainer = $("#totalAmount");

         if (Object.keys(cart).length === 0) {
             cartItemsContainer.html("<p>Oops, nothing is there in the cart.</p>");
             $("#addNewAddress").hide();
             $("#addressContainer").hide();
             $("#proceedButton").hide();
             totalAmountContainer.hide();
         } else {
             cartItemsContainer.empty();
             let totalAmount = 0;
             let restaurantName = "";

             Object.keys(cart).forEach(menuItemId => {
                 const quantity = cart[menuItemId];
                 const item = getItemDetails(menuItemId); // Implement this function to get item details

                 if (!restaurantName) {
                     const restaurantDetails = getRestaurantDetails(item.restaurantId);
                     restaurantName = restaurantDetails.name; // Set the restaurant name
                 }

                 const cartItem = `
                     <div class="cart-item" data-id="${menuItemId}">
                         <div class="cart-item-details">
                             <h5>${item.name}</h5>
                             <div class="cart-item-controls">
                                 <p>Price: ₹${item.price}</p>
                                 <p>Quantity:
                                     <button class="decrease">-</button>
                                     <span>${quantity}</span>
                                     <button class="increase">+</button>
                                 </p>
                                 <button class="remove">Remove</button>
                             </div>
                         </div>
                     </div>`;
                 cartItemsContainer.append(cartItem);
                 totalAmount += item.price * quantity;
             });

             // Display the restaurant name at the top
             const restaurantHeader = `<h3>Restaurant: ${restaurantName}</h3>`;
             cartItemsContainer.prepend(restaurantHeader);

             totalAmountContainer.text(`Total Amount: ₹${totalAmount}`);
             totalAmountContainer.show();
             $("#addNewAddress").show();
             $("#addressContainer").show();
             $("#proceedButton").show();
         }
     }

    function loadAddresses() {
        $.ajax({
            url: "/api/user/addresses",
            method: "GET",
            success: function(addresses) {
                const addressContainer = $("#addressContainer");
                addressContainer.empty();

                if (addresses.length === 0) {
                    $("#newAddressForm").show();
                } else {
                    const header = `<h3>Select Address</h3>`;
                    addressContainer.append(header);

                    const dropdown = $('<select id="addressDropdown" class="form-select"></select>');
                    dropdown.append('<option value="">Select an address</option>');

                    addresses.forEach(address => {
                        const option = `<option value="${address.addressId}">${address.label} - ${address.addressLine}, ${address.city}, ${address.state}, ${address.postalCode}</option>`;
                        dropdown.append(option);
                    });

                    addressContainer.append(dropdown);
                }
            },
            error: function(xhr, status, error) {
                console.error("Error fetching addresses:", status, error);
                alert("Error fetching addresses.");
            }
        });
    }

    $("#addNewAddress").click(function() {
        $("#newAddressForm").toggle();
    });

    $("#saveNewAddress").click(function() {
        const newAddress = {
            label: $("input[name='addressLabel']:checked").val(),
            addressLine: $("#newAddressLine").val().trim(),
            city: $("#newAddressCity").val().trim(),
            postalCode: $("#newAddressPostalCode").val().trim(),
            state: $("#newAddressState").val().trim()
        };

        if (!newAddress.label || !newAddress.addressLine || !newAddress.city || !newAddress.postalCode || !newAddress.state) {
            alert("Please fill in all the required fields.");
            return;
        }

        $.ajax({
            url: "/api/user/address/add",
            method: "POST",
            contentType: "application/json",
            data: JSON.stringify(newAddress),
            success: function(response) {
                const newOption = `<option value="${response.addressId}">${response.label} - ${response.addressLine}, ${response.city}, ${response.state}, ${response.postalCode}</option>`;
                $("#addressDropdown").append(newOption);
                $("#newAddressForm").hide();
                $("input[name='addressLabel']").prop('checked', false);
                $("#newAddressLine").val("");
                $("#newAddressCity").val("");
                $("#newAddressPostalCode").val("");
                $("#newAddressState").val("");
                alert("Address added successfully!");
            },
            error: function(xhr, status, error) {
                console.error("Error saving new address:", status, error);
                alert("Error saving new address.");
            }
        });
    });

    $(document).on("click", ".increase", function() {
        const menuItemId = $(this).closest(".cart-item").data("id");
        let cart = JSON.parse(localStorage.getItem("cart")) || {};
        cart[menuItemId]++;
        localStorage.setItem("cart", JSON.stringify(cart));
        renderCartItems(cart);
        transferLocalStorageToCart();
    });

    $(document).on("click", ".decrease", function() {
        const menuItemId = $(this).closest(".cart-item").data("id");
        let cart = JSON.parse(localStorage.getItem("cart")) || {};
        if (cart[menuItemId] > 1) {
            cart[menuItemId]--;
            localStorage.setItem("cart", JSON.stringify(cart));
            renderCartItems(cart);
            transferLocalStorageToCart();
        }
    });

   $(document).on("click", ".remove", function() {
       const menuItemId = $(this).closest(".cart-item").data("id");
       let cart = JSON.parse(localStorage.getItem("cart")) || {};

       // Call the API to remove the item from the server
       $.ajax({
           url: "/api/cart/remove-item",
           method: "DELETE",
           data: { menuItemId: menuItemId },
           success: function(response) {
               console.log("Item removed from server:", response);
               delete cart[menuItemId]; // Remove the item from the cart object
               localStorage.setItem("cart", JSON.stringify(cart)); // Update localStorage
               renderCartItems(cart);
               transferLocalStorageToCart();
           },
           error: function(xhr, status, error) {
               console.error("Error removing item from server:", status, error);
               alert("Error removing item from server.");
           }
       });
   });

    $("#proceedButton").click(function() {
        proceedToCheckout();
    });

function proceedToCheckout() {
    const selectedAddressId = $('#addressDropdown').val();
    if (!selectedAddressId) {
        alert("Please select an address before proceeding.");
        return;
    }

    $.ajax({
        url: "/api/cart/items",
        method: "GET",
        contentType: "application/json",
        success: function(cartItems) {
            $.ajax({
                url: "/order/place-order",
                method: "POST",
                contentType: "application/json",
                data: JSON.stringify({
                    addressId: selectedAddressId,
                    restaurantId: cartItems.length > 0 ? cartItems[0].restaurantId : null,
                    items: cartItems
                }), success: function(response) {
                        console.log("Order Placed Successfully:", response);
                        localStorage.setItem("orderId", response.orderId);

                        // Clear the cart
                        $.ajax({
                            url: "/api/cart/clear",
                            method: "DELETE",
                            contentType: "application/json",
                            success: function() {
                                console.log("Cart Cleared Successfully");
                                localStorage.removeItem("cart");
                                window.location.href = "/api/customer/order-status";
                            },
                            error: function(xhr, status, error) {
                                console.error("Error Clearing Cart:", status, error);
                                alert("Error clearing cart. Please try again.");
                            }
                        });
                    },
                    error: function(xhr, status, error) {
                        console.error("Error Placing Order:", status, error);
                        alert("Error placing order. Please try again.");
                    }
                });
            },
            error: function(xhr, status, error) {
                console.error("Error Fetching Cart Items:", status, error);
                alert("Error fetching cart items. Please try again.");
            }
        });
    }

    function transferLocalStorageToCart() {
        const localCart = JSON.parse(localStorage.getItem("cart")) || {};
        console.log("Local Cart Items:", localCart);

        if (Object.keys(localCart).length === 0) {
            console.log("No items in local cart to transfer.");
            return;
        }

        $.ajax({
            url: "/api/cart/sync",
            method: "POST",
            contentType: "application/json",
            data: JSON.stringify(localCart), // Send the entire object
            success: function(response) {
                console.log("Local storage cart items transferred to server:", response);
                localStorage.setItem("cartSynced", "true");
                loadCartItemsFromAPI();
            },
            error: function(xhr, status, error) {
                console.error("Error transferring local storage cart items:", status, error);
                alert("Error transferring local storage cart items.");
            }
        });
    }
});

$(document).ready(function() {
         // Hide the search input and button
         $("#searchInput").hide();
         $("#nav-searchBtn").hide();
     });