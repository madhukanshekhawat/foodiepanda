$(document).ready(function() {
    // Check if the user is logged in
    $.ajax({
        url: "/api/user/status",
        method: "GET",
        success: function(response) {
            if (response.loggedIn) {
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

    function loadAddresses() {
        $.ajax({
            url: "/api/user/addresses",
            method: "GET",
            success: function(addresses) {
                const addressContainer = $("#addressContainer");
                if (addresses.length === 0) {
                    $("#newAddressForm").show();
                } else {
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
                    <div>
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
});