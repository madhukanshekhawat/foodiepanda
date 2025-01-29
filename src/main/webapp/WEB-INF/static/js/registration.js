$(document).ready(function() {
    $("#registerForm").submit(function(event) {
        event.preventDefault();
        registerUserAndRestaurant();
    });

    $("input[name='role']").change(function() {
        if ($(this).val() === "RESTAURANT_OWNER") {
            $("#restaurantDetails").slideDown();
        } else {
            $("#restaurantDetails").slideUp();
        }
    });

    // Clear error messages when user starts typing
    $("input, radio").on("input change", function() {
        clearError($(this).attr("id") + "Error");
    });
});

function showError(elementId, message) {
    $("#" + elementId).text(message);
}

function clearError(elementId) {
    $("#" + elementId).text("");
}

async function registerUserAndRestaurant() {
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

    let user = {
        firstName: firstName,
        lastName: lastName,
        email: email,
        phoneNumber: phoneNumber,
        password: password,
        role: role
    };

    let restaurant = {
        restaurantName: $("#restaurantName").val(),
        restaurantContact: $("#restaurantContact").val(),
        addressLine: $("#addressLine").val(),
        city: $("#city").val(),
        state: $("#state").val(),
        postalCode: $("#postalCode").val()
    };

    try {
        // Register the user
        const userResponse = await fetch('/register/registerUser', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(user),
        });

        if (!userResponse.ok) {
            throw new Error('Failed to register user');
        }

        const ownerId = await userResponse.json();

        if (role === "RESTAURANT_OWNER") {
            // Register the restaurant with the owner ID
            restaurant.ownerId = ownerId;
            const restaurantResponse = await fetch('/register/restaurant', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(restaurant),
            });

            if (restaurantResponse.ok) {
                alert('Restaurant registered successfully');
                window.location.href = "/api/approval-pending";
            } else {
                console.error('Failed to register restaurant');
            }
        } else {
            alert("Registration Successful!");
            window.location.href = "/api/customer/dashboard";
        }
    } catch (error) {
        console.error('An error occurred:', error);
        showError("errorMessages", "Error registering user or restaurant.");
    }
}