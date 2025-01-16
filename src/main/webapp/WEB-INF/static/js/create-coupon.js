$(document).ready(function() {
    // Fetch and populate the restaurant dropdown
    $.ajax({
        url: "/api/restaurant",
        method: "GET",
        success: function(data) {
            let restaurantDropdown = $("#restaurantId");
            data.forEach(function(restaurant) {
                restaurantDropdown.append(new Option(restaurant.name, restaurant.restaurantId));
            });
        },
        error: function(xhr) {
            showError("restaurantIdError", "Error fetching restaurants.");
        }
    });

    $("#couponForm").submit(function(event) {
        event.preventDefault(); // Prevent form submission
        createCoupon();
    });

    // Clear error messages when user starts typing
    $("input, select").on("input change", function() {
        clearError($(this).attr("id") + "Error");
    });
});

function showError(elementId, message) {
    $("#" + elementId).text(message);
}

function clearError(elementId) {
    $("#" + elementId).text("");
}

function createCoupon() {
    let restaurantId = $("#restaurantId").val();
    let code = $("#code").val();
    let discountPercentage = $("#discountPercentage").val();
    let validFrom = $("#validFrom").val();
    let validTo = $("#validTo").val();
    let minOrderValue = $("#minOrderValue").val();
    let usageLimit = $("#usageLimit").val();
    let applicableTo = $("#applicableTo").val();

    // Validation checks
    let isValid = true;

    if (!restaurantId) {
        showError("restaurantIdError", "Restaurant is required.");
        isValid = false;
    }

    if (!code) {
        showError("codeError", "Coupon code is required.");
        isValid = false;
    }

    if (discountPercentage < 1) {
        showError("discountPercentageError", "Discount percentage must be at least 1%.");
        isValid = false;
    }

    if (!validFrom) {
        showError("validFromError", "Valid from date is required.");
        isValid = false;
    }

    if (!validTo) {
        showError("validToError", "Valid to date is required.");
        isValid = false;
    }

    if (minOrderValue < 199) {
        showError("minOrderValueError", "Minimum order value must be at least 199.");
        isValid = false;
    }

    if (usageLimit < 1) {
        showError("usageLimitError", "Usage limit must be at least 1.");
        isValid = false;
    }

    if (!isValid) {
        return;
    }

    let couponData = {
        restaurantId: restaurantId,
        code: code,
        discountPercentage: discountPercentage,
        validFrom: validFrom,
        validTo: validTo,
        minOrderValue: minOrderValue,
        usageLimit: usageLimit,
        applicableTo: applicableTo
    };

    $.ajax({
        url: "/api/admin/create-coupon",
        method: "POST",
        contentType: "application/json",
        data: JSON.stringify(couponData),
        success: function(response) {
            alert("Coupon Created Successfully!");
            $("#couponForm")[0].reset();
        },
        error: function(xhr) {
            if (xhr.status === 404) {
                showError("restaurantIdError", "Error: No such restaurant ID.");
            } else {
                showError("errorMessages", "Error creating coupon.");
            }
        }
    });
}