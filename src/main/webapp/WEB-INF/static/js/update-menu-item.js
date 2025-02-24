$(document).ready(function() {
    const menuItemId = getMenuItemIdFromURL(); // Implement this function to get the ID from the URL
    loadMenuItem(menuItemId);
});

function loadMenuItem(menuItemId) {
    $.ajax({
        url: `/menu/${menuItemId}`,
        method: "GET",
        success: function(data) {
            $("#menuItemId").val(data.id);
            $("#name").val(data.name);
            $("#description").val(data.description);
            $("#price").val(data.price);
            $("#category").val(data.categoryName);
            $("#image").val(data.image);
            $("#available").val(data.available);
            $("#veg").val(data.veg);
        },
        error: function() {
            alert("Error fetching menu item details.");
        }
    });
}

function updateMenuItem() {
    const menuItemId = $("#menuItemId").val();
    const updatedData = {
        name: $("#name").val(),
        description: $("#description").val(),
        price: $("#price").val(),
        category: $("#category").val(),
        image: $("#image").val(),
        available: $("#available").val(),
        veg: $("#veg").val()
    };

    $.ajax({
        url: "/menu/" + menuItemId,
        method: "PUT",
        contentType: "application/json",
        data: JSON.stringify(updatedData),
        success: function() {
            alert("Menu item updated successfully.");
            window.location.href = "/menu"; // Redirect to the menu items page
        },
        error: function() {
            alert("Error updating menu item.");
        }
    });
}