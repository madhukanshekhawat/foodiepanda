$(document).ready(function() {
    // Check if the user is logged in
    $.ajax({
        url: "/api/user/status",
        method: "GET",
        success: function(response) {
            if (response.loggedIn) {
                transferLocalCartToServer();
                loadCartItemsFromServer();
                loadAddresses();
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

    function transferLocalCartToServer() {
        const localCart = JSON.parse(localStorage.getItem("cart")) || [];
        if (localCart.length > 0) {
            $.ajax({
                url: "/api/cart/transfer",
                method: "POST",
                contentType: "application/json",
                data: JSON.stringify(localCart),
                success: function() {
                    localStorage.removeItem("cart");
                },
                error: function(xhr, status, error) {
                    console.error("Error transferring local cart to server:", status, error);
                }
            });
        }
    }

    function loadCartItemsFromServer() {
        $.ajax({
            url: "/api/cart/items",
            method: "GET",
            success: function(cart) {
                renderCartItems(cart);
            },
            error: function(xhr, status, error) {
                console.error("Error loading cart items from server:", status, error);
                alert("Error loading cart items.");
            }
        });
    }

    function loadCartItemsFromLocalStorage() {
        const cart = JSON.parse(localStorage.getItem("cart")) || [];
        renderCartItems(cart);
    }

    function renderCartItems(cart) {
        const cartItemsContainer = $("#cartItems");
        const totalAmountContainer = $("#totalAmount");

        if (cart.length === 0) {
            cartItemsContainer.html("<p>Oops, nothing is there in the cart.</p>");
            $("#addNewAddress").hide();
            $("#addressContainer").hide();
            $("#proceedButton").hide();
            totalAmountContainer.hide();
        } else {
            cartItemsContainer.empty();
            let totalAmount = 0;

            cart.forEach((item, index) => {
                const cartItem = `
                    <div class="cart-item" data-index="${index}">
                        <div class="cart-item-details">
                            <h5>${item.name}</h5>
                            <div class="cart-item-controls">
                                <p>Price: ₹${item.price}</p>
                                <p>Quantity:
                                    <button class="decrease-quantity">-</button>
                                    <span>${item.quantity}</span>
                                    <button class="increase-quantity">+</button>
                                </p>
                                <button class="remove-item">Remove</button>
                            </div>
                        </div>
                    </div>`;
                cartItemsContainer.append(cartItem);
                totalAmount += item.price * item.quantity;
            });

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

                // Store addresses for duplicate check
                $("#addressContainer").data("addresses", addresses);
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

        // Check for duplicate address
        const addresses = $("#addressContainer").data("addresses") || [];
        if (isDuplicateAddress(newAddress, addresses)) {
            alert("This address already exists.");
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

    $(document).on("click", ".increase-quantity", function() {
        const index = $(this).closest(".cart-item").data("index");
        let cart = JSON.parse(localStorage.getItem("cart")) || [];
        cart[index].quantity++;
        localStorage.setItem("cart", JSON.stringify(cart));
        renderCartItems(cart);
    });

    $(document).on("click", ".decrease-quantity", function() {
        const index = $(this).closest(".cart-item").data("index");
        let cart = JSON.parse(localStorage.getItem("cart")) || [];
        if (cart[index].quantity > 1) {
            cart[index].quantity--;
            localStorage.setItem("cart", JSON.stringify(cart));
            renderCartItems(cart);
        }
    });

    $(document).on("click", ".remove-item", function() {
        const index = $(this).closest(".cart-item").data("index");
        let cart = JSON.parse(localStorage.getItem("cart")) || [];
        cart.splice(index, 1);
        localStorage.setItem("cart", JSON.stringify(cart));
        renderCartItems(cart);
    });

    $("#checkoutButton").click(function() {
        proceedToCheckout();
    });

    function proceedToCheckout() {
        const selectedAddressId = $('#addressDropdown').val();
        if (!selectedAddressId) {
            alert("Please select an address before proceeding.");
            return;
        }

        let cartData = localStorage.getItem("cart");
        let cartItems = cartData ? JSON.parse(localStorage.getItem("cart")) : [];

        if (cartItems.length === 0) {
            alert("Your cart is empty.");
            return;
        }

        let restaurantId = cartItems.length > 0 ? cartItems[0].restaurantId : null;
        if (!restaurantId) {
            alert("Restaurant ID is missing.");
            return;
        }

        let formattedCartItems = cartItems.map(item => ({
            menuItemId: item.menuItemId,
            quantity: item.quantity,
            price: item.price
        }));

        $.ajax({
            url: "/order/place-order",
            type: "POST",
            contentType: "application/json",
            data: JSON.stringify({
                addressId: selectedAddressId,
                restaurantId: restaurantId,
                items: formattedCartItems
            }),
            success: function(response) {
                console.log("Order Placed Successfully:", response);
                localStorage.setItem("orderId", response.orderId);
                localStorage.removeItem("cart");
                window.location.href = "/api/customer/order-status";
            },
            error: function(xhr, status, error) {
                console.error("Error Placing Order:", status, error);
                alert("Error placing order. Please try again.");
            }
        });
    }
     function isDuplicateAddress(address, addresses) {
            return addresses.some(function(existingAddress) {
                return existingAddress.addressLine.toLowerCase() === address.addressLine.toLowerCase() &&
                       existingAddress.city.toLowerCase() === address.city.toLowerCase() &&
                       existingAddress.state.toLowerCase() === address.state.toLowerCase() &&
                       existingAddress.postalCode === address.postalCode &&
                       existingAddress.label.toLowerCase() === address.label.toLowerCase();
            });
        }

        $(document).on("click", ".increase-quantity", function() {
            const index = $(this).closest(".cart-item").data("index");
            let cart = JSON.parse(localStorage.getItem("cart")) || [];
            cart[index].quantity++;
            localStorage.setItem("cart", JSON.stringify(cart));
            renderCartItems(cart);
        });

        $(document).on("click", ".decrease-quantity", function() {
            const index = $(this).closest(".cart-item").data("index");
            let cart = JSON.parse(localStorage.getItem("cart")) || [];
            if (cart[index].quantity > 1) {
                cart[index].quantity--;
                localStorage.setItem("cart", JSON.stringify(cart));
                renderCartItems(cart);
            }
        });

        $(document).on("click", ".remove-item", function() {
            const index = $(this).closest(".cart-item").data("index");
            let cart = JSON.parse(localStorage.getItem("cart")) || [];
            cart.splice(index, 1);
            localStorage.setItem("cart", JSON.stringify(cart));
            renderCartItems(cart);
        });

        $("#checkoutButton").click(function() {
            proceedToCheckout();
        });

        function proceedToCheckout() {
            const selectedAddressId = $('#addressDropdown').val();
            if (!selectedAddressId) {
                alert("Please select an address before proceeding.");
                return;
            }

            let cartData = localStorage.getItem("cart");
            let cartItems = cartData ? JSON.parse(localStorage.getItem("cart")) : [];

            if (cartItems.length === 0) {
                alert("Your cart is empty.");
                return;
            }

            let restaurantId = cartItems.length > 0 ? cartItems[0].restaurantId : null;
            if (!restaurantId) {
                alert("Restaurant ID is missing.");
                return;
            }

            let formattedCartItems = cartItems.map(item => ({
                menuItemId: item.menuItemId,
                quantity: item.quantity,
                price: item.price
            }));

            $.ajax({
                url: "/order/place-order",
                type: "POST",
                contentType: "application/json",
                data: JSON.stringify({
                    addressId: selectedAddressId,
                    restaurantId: restaurantId,
                    items: formattedCartItems
                }),
                success: function(response) {
                    console.log("Order Placed Successfully:", response);
                    localStorage.setItem("orderId", response.orderId);
                    localStorage.removeItem("cart");
                    window.location.href = "/api/customer/order-status";
                },
                error: function(xhr, status, error) {
                    console.error("Error Placing Order:", status, error);
                    alert("Error placing order. Please try again.");
                }
            });
        }
    });