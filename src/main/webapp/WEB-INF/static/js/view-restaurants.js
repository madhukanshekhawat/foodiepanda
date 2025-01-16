
$(document).ready(function() {
    loadRestaurants();
});

function loadRestaurants() {
    $.ajax({
        url: "/api/restaurant",
        method: "GET",
        success: function(response) {
            let html = "";
            response.forEach(restaurant => {
                html += `<tr>
                    ${restaurant.name}</td>
                    <td>${restaurant.name}</td>
                    <td>${restaurant.ownerDetails.firstName} ${restaurant.ownerDetails.lastName}</td>
                    <td>${restaurant.address}</td>
                    <td>${restaurant.phoneNumber}</td>
                </tr>`;
            });
            $("#restaurant-data").html(html);
        },
        error: function() {
            $("#restaurant-data").html("<tr><td colspan='2' class='error'>Error loading restaurants. Please try again.</td></tr>");
        }
    });
}
