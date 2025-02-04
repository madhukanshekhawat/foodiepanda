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
        $.ajax({
            url: "/api/cart/items",
            method: "GET",
            success: function(cart) {
                const cartItemsContainer = $("#cartItems");

                if (cart.length === 0) {
                    cartItemsContainer.html("<p>Your cart is empty.</p>");
                } else {
                    cart.forEach(item => {
                        const cartItem = `
                            <div class="cart-item" data-id="${item.id}">
                                <img src="data:image/jpeg;base64,${item.image}" alt="${item.name}">
                                <div class="cart-item-details">
                                    <h5>${item.name}</h5>
                                    <p>Price: ₹${item.price}</p>
                                    <p>Quantity: <input type="number" class="item-quantity" value="${item.quantity}" min="1"></p>
                                    <button class="delete-item">Delete</button>
                                </div>
                            </div>
                        `;
                        cartItemsContainer.append(cartItem);
                    });
                }
            },
            error: function(xhr, status, error) {
                console.error("Error fetching cart items:", status, error);
                alert("Error fetching cart items.");
            }
        });
    }

    function loadAddresses() {
        $.ajax({
            url: "/api/user/addresses",
            method: "GET",
            success: function(addresses) {
                const addressDropdown = $("#addressDropdown");
                addresses.forEach(address => {
                    const option = `<option value="${address.id}">${address.details}</option>`;
                    addressDropdown.append(option);
                });
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
        const newAddress = $("#newAddress").val().trim();
        if (newAddress) {
            $.ajax({
                url: "/api/user/addresses",
                method: "POST",
                contentType: "application/json",
                data: JSON.stringify({ details: newAddress }),
                success: function(response) {
                    const option = `<option value="${response.id}">${response.details}</option>`;
                    $("#addressDropdown").append(option);
                    $("#newAddressForm").hide();
                    $("#newAddress").val("");
                },
                error: function(xhr, status, error) {
                    console.error("Error saving new address:", status, error);
                    alert("Error saving new address.");
                }
            });
        } else {
            alert("Please enter a valid address.");
        }
    });

    $(document).on("click", ".delete-item", function() {
        const itemId = $(this).closest(".cart-item").data("id");
        $.ajax({
            url: "/api/cart/items/" + itemId,
            method: "DELETE",
            success: function(response) {
                loadCartItems();
            },
            error: function(xhr, status, error) {
                console.error("Error deleting cart item:", status, error);
                alert("Error deleting cart item.");
            }
        });
    });

    $(document).on("change", ".item-quantity", function() {
        const itemId = $(this).closest(".cart-item").data("id");
        const newQuantity = $(this).val();
        $.ajax({
            url: "/api/cart/items/" + itemId,
            method: "PUT",
            contentType: "application/json",
            data: JSON.stringify({ quantity: newQuantity }),
            success: function(response) {
                loadCartItems();
            },
            error: function(xhr, status, error) {
                console.error("Error updating cart item quantity:", status, error);
                alert("Error updating cart item quantity.");
            }
        });
    });

    $("#proceedButton").click(function() {
        const selectedAddressId = $("#addressDropdown").val();
        if (selectedAddressId) {
            window.location.href = "/checkout?addressId=" + selectedAddressId;
        } else {
            alert("Please select an address.");
        }
    });
});