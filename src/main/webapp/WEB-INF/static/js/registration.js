$(document).ready(function() {
    $("#registerForm").submit(function(event) {
        event.preventDefault(); // Prevent form submission
        registerUser();
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
                $("#registerForm")[0].reset();
            }
        },
        error: function(xhr) {
            showError("errorMessages", "Error registering user.");
        }
    });
}