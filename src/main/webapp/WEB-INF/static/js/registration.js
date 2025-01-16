$(document).ready(function() {
    $("#registerForm").submit(function(event) {
        event.preventDefault(); // Prevent form submission
        registerUser();
    });

    // Clear error messages when user starts typing
    $("input, radio").on("input change", function() {
        clearError($(this).attr("id") + "Error");
    });

    // Show/hide restaurant details based on role selection
    $("input[name='role']").change(function() {
        if ($(this).val() === "RESTAURANT_OWNER") {
            $("#restaurantDetails").show();
        } else {
            $("#restaurantDetails").hide();
        }
    });
});

function showError(elementId, message) {
    $("#" + elementId).text(message);
}

function clearError(elementId) {
    $("#" + elementId).text("");
}

function registerUser() {
    let firstName = $("#firstName").val();
    let lastName = $("#lastName").val();
    let email = $("#email").val();
    let phoneNumber = $("#phoneNumber").val();
    let password = $("#password").val();
    let confirmPassword = $("#confirmPassword").val();
    let role = $("input[name='role']:checked").val();

    // Validation checks
    if (!firstName) {
        showError("firstNameError", "First Name is required.");
        return;
    }

    if (!lastName) {
        showError("lastNameError", "Last Name is required.");
        return;
    }

    if (!email) {
        showError("emailError", "Email is required.");
        return;
    }

    if (!phoneNumber) {
        showError("phoneNumberError", "Phone Number is required.");
        return;
    }

    if (!password) {
        showError("passwordError", "Password is required.");
        return;
    }

    if (password !== confirmPassword) {
        showError("confirmPasswordError", "Passwords do not match.");
        return;
    }

    if (!role) {
        showError("roleError", "Role is required.");
        return;
    }

    let userData = {
        firstName: firstName,
        lastName: lastName,
        email: email,
        phoneNumber: phoneNumber,
        password: password,
        role: role
    };

    if (role === "RESTAURANT_OWNER") {
        let restaurantName = $("#restaurantName").val();
        let restaurantContact = $("#restaurantContact").val();
        let addressLine = $("#addressLine").val();
        let city = $("#city").val();
        let state = $("#state").val();
        let postalCode = $("#postalCode").val();

        if (!restaurantName) {
            showError("restaurantNameError", "Restaurant Name is required.");
            return;
        }

        if (!restaurantContact) {
            showError("restaurantContactError", "Restaurant Contact Number is required.");
            return;
        }

        if (!addressLine) {
            showError("addressLineError", "Address Line is required.");
            return;
        }

        if (!city) {
            showError("cityError", "City is required.");
            return;
        }

        if (!state) {
            showError("stateError", "State is required.");
            return;
        }

        if (!postalCode) {
            showError("postalCodeError", "Postal Code is required.");
            return;
        }

        userData = {
            firstName: firstName,
            lastName: lastName,
            email: email,
            phoneNumber: phoneNumber,
            password: password,
            role: role,
            restaurantName: restaurantName,
            restaurantContact: restaurantContact,
            addressLine: addressLine,
            city: city,
            state: state,
            postalCode: postalCode
        };
    }

    $.ajax({
        url: "/register",
        method: "POST",
        contentType: "application/json",
        data: JSON.stringify(userData),
        success: function(response) {
            if (role === "RESTAURANT_OWNER") {
                alert("Registration Successful!");
                window.location.href = "/approval-pending";
            } else {
                alert("Registration Successful!");
                 window.location.href = "/customer/da";
                $("#registerForm")[0].reset();
            }
        },
        error: function(xhr) {
            showError("errorMessages", "Error registering user.");
        }
    });
}