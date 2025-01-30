let currentPage = 0;
const pageSize = 6;

function fetchRestaurants(page) {
    $.ajax({
        url: "/api/customer/restaurant/page/" + page + "/size/" + pageSize,
        type: "GET",
        success: function (response) {
            $("#restaurant-cards").empty();

            response.content.forEach(restaurant => {
                let cardClass = restaurant.available ? 'restaurant-card' : 'restaurant-card gray';
                let card = `<div class='${cardClass}' ${restaurant.available ? 'onclick="window.location.href=\'/restaurant/main-page\'"' : ''}>
                                <img src="data:image/jpeg;base64,${restaurant.image}" alt="${restaurant.name}" style="width: 350px; height: 250px;"/>
                                <p class="restaurant-name">${restaurant.name}</p>
                                <p class="restaurant-address">${restaurant.address}</p>
                                <p class="availability-status">${restaurant.available ? 'Available' : 'Not Available'}</p>
                            </div>`;
                $("#restaurant-cards").append(card);
            });

            // Update pagination buttons
            $("#currentPage").text(page + 1);
            $("#prevPage").prop("disabled", page === 0);
            $("#nextPage").prop("disabled", response.last);
        }
    });
}

$("#prevPage").click(function () {
    if (currentPage > 0) {
        currentPage--;
        fetchRestaurants(currentPage);
    }
});

$("#nextPage").click(function () {
    currentPage++;
    fetchRestaurants(currentPage);
});

$(document).ready(function () {
    fetchRestaurants(currentPage);
});