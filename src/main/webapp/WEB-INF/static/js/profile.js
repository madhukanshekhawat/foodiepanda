$(document).ready(function() {
    // Fetch customer profile
    $.ajax({
        url: "/api/customer/profile",
        type: "GET",
        success: function(data) {
            $("#email").text(data.email);
            $("#firstName").text(data.firstName);
            $("#lastName").text(data.lastName);
            $("#phoneNumber").text(data.phoneNumber);
        },
        error: function(error) {
            alert("Error fetching profile: " + error.responseText);
        }
    });

    // Show popup for editing profile
    $("#editProfileBtn").click(function() {
        $.ajax({
            url: "/api/customer/profile",
            type: "GET",
            success: function(data) {
                $("#editFirstName").val(data.firstName);
                $("#editLastName").val(data.lastName);
                $("#editPhoneNumber").val(data.phoneNumber);
                $("#editProfilePopup").show();
            },
            error: function(error) {
                alert("Error fetching profile: " + error.responseText);
            }
        });
    });

    // Close popup
    $("#closePopupBtn").click(function() {
        $("#editProfilePopup").hide();
    });

    // Enable update button only if there are unique changes
    $("#editFirstName, #editLastName, #editPhoneNumber").on("input", function() {
        let originalFirstName = $("#firstName").text();
        let originalLastName = $("#lastName").text();
        let originalPhoneNumber = $("#phoneNumber").text();
        let newFirstName = $("#editFirstName").val();
        let newLastName = $("#editLastName").val();
        let newPhoneNumber = $("#editPhoneNumber").val();

        if (newFirstName !== originalFirstName || newLastName !== originalLastName || newPhoneNumber !== originalPhoneNumber) {
            $("#updateProfileBtn").prop("disabled", false);
        } else {
            $("#updateProfileBtn").prop("disabled", true);
        }
    });

    // Update profile
    $("#updateProfileBtn").click(function() {
        let updatedProfile = {
            firstName: $("#editFirstName").val(),
            lastName: $("#editLastName").val(),
            phoneNumber: $("#editPhoneNumber").val()
        };

        $.ajax({
            url: "/api/customer/edit",
            type: "PUT",
            contentType: "application/json",
            data: JSON.stringify(updatedProfile),
            success: function(response) {
                alert("Profile updated successfully!");
                $("#firstName").text(updatedProfile.firstName);
                $("#lastName").text(updatedProfile.lastName);
                $("#phoneNumber").text(updatedProfile.phoneNumber);
                $("#editProfilePopup").hide();
            },
            error: function(error) {
                alert("Error updating profile: " + error.responseText);
            }
        });
    });
});