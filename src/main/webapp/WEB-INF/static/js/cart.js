$(document).ready(function() {
    // Check if the user is logged in
    $.ajax({
        url: "/api/user/status",
        method: "GET",
        success: function(response) {
            if (response.loggedIn) {
                loadCartItems();
                loadAddresses();
            } else {
                alert("Please log in to view your cart.");
                window.location.href = "/api/user-login";
            }
        },
        error: function(xhr, status, error) {
            console.error("Error checking login status:", status, error);
            alert("Error checking login status.");
        }
    });

    function loadCartItems() {
        const cart = JSON.parse(localStorage.getItem("cart")) || [];
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
                   // Add the header
                   const header = `<h3>Select Address</h3>`;
                   addressContainer.append(header);

                   addresses.forEach(address => {
                       const radioOption = `
                           <div>
                               <input type="radio" name="address" value="${address.id}" id="address-${address.id}">
                               <label for="address-${address.id}">${address.label} - ${address.addressLine}, ${address.city}, ${address.state}, ${address.postalCode}</label>
                           </div>`;
                       addressContainer.append(radioOption);
                   });
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

        // Validation
        if (!newAddress.label) {
            alert("Please select an address label.");
            return;
        }
        if (!newAddress.addressLine) {
            alert("Please enter the address line.");
            return;
        }
        if (newAddress.addressLine.length > 50) {
            alert("Address line should not exceed 50 characters.");
            return;
        }
        if (!newAddress.city) {
            alert("Please enter the city.");
            return;
        }
        if (!newAddress.postalCode) {
            alert("Please enter the postal code.");
            return;
        }
        if (!newAddress.state) {
            alert("Please enter the state.");
            return;
        }

        $.ajax({
            url: "/api/user/address/add",
            method: "POST",
            contentType: "application/json",
            data: JSON.stringify(newAddress),
            success: function(response) {
                const radioOption = `
                    <div class="address-labels">
                        <input type="radio" name="address" value="${response.id}" id="address-${response.id}">
                        <label for="address-${response.id}">${response.label} - ${response.addressLine}, ${response.city}, ${response.state}, ${response.postalCode}</label>
                    </div>`;
                $("#addressContainer").append(radioOption);
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

    // Increase quantity
    $(document).on("click", ".increase-quantity", function() {
        const index = $(this).closest(".cart-item").data("index");
        let cart = JSON.parse(localStorage.getItem("cart")) || [];
        cart[index].quantity++;
        localStorage.setItem("cart", JSON.stringify(cart));
        loadCartItems();
    });

    // Decrease quantity
    $(document).on("click", ".decrease-quantity", function() {
        const index = $(this).closest(".cart-item").data("index");
        let cart = JSON.parse(localStorage.getItem("cart")) || [];
        if (cart[index].quantity > 1) {
            cart[index].quantity--;
            localStorage.setItem("cart", JSON.stringify(cart));
            loadCartItems();
        }
    });

    // Remove item
    $(document).on("click", ".remove-item", function() {
        const index = $(this).closest(".cart-item").data("index");
        let cart = JSON.parse(localStorage.getItem("cart")) || [];
        cart.splice(index, 1);
        localStorage.setItem("cart", JSON.stringify(cart));
        loadCartItems();
    });

    $("#checkoutButton").click(function() {
        // Handle checkout process
        alert("Proceeding to checkout...");
    });

    // Function to add new item to cart
    function addItemToCart(newItem) {
        let cart = JSON.parse(localStorage.getItem("cart")) || [];
        if (cart.length > 0 && cart[0].restaurantId !== newItem.restaurantId) {
            if (confirm("Your cart contains items from a different restaurant. Do you want to replace them?")) {
                cart = [newItem];
            } else {
                return;
            }
        } else {
            // Check if the item already exists in the cart
            const existingItemIndex = cart.findIndex(item => item.id === newItem.id);
            if (existingItemIndex !== -1) {
                // Update the quantity of the existing item
                cart[existingItemIndex].quantity += newItem.quantity;
            } else {
                // Add the new item to the cart
                cart.push(newItem);
            }
        }
        localStorage.setItem("cart", JSON.stringify(cart));
        loadCartItems();
    }
});