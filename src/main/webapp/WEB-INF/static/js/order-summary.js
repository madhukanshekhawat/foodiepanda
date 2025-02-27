$(document).ready(function() {
         // Hide the search input and button
         $("#searchInput").hide();
         $("#nav-searchBtn").hide();
     });

$(document).ready(function () {
    $.ajax({
        url: "/order/customer/all",
        type: "GET",
        dataType: "json",
        success: function (orders) {
            if (orders.length === 0) {
                $("#ordersContainer").html("<p class='text-center text-muted'>No orders yet.</p>");
                return;
            }

            const groupedOrders = orders.reduce((acc, order) => {
                const date = new Date(order.createdAt).toLocaleDateString('en-GB', {
                    day: '2-digit',
                    month: 'short',
                    year: 'numeric'
                }).replace(/ /g, ' '); // Ensure single space between day, month, and year

                if (!acc[date]) {
                    acc[date] = [];
                }
                acc[date].push(order);
                return acc;
            }, {});

            let content = '';

            // Iterate over grouped orders and create tables for each date
            for (const [date, orders] of Object.entries(groupedOrders)) {
                content += `<h3 class="text-center">${date}</h3>`;
                content += `<table class="table table-bordered">
                                <thead>
                                    <tr>
                                        <th>Total Amount ₹</th>
                                        <th>Restaurant Name</th>
                                        <th>Status</th>
                                        <th>Delivery Address</th>
                                        <th>Action</th>
                                    </tr>
                                </thead>
                                <tbody>`;

                orders.forEach(order => {
                    content += `<tr>
                                    <td>${order.totalAmount}</td>
                                    <td>${order.restaurantName}</td>
                                    <td>${order.orderStatus}</td>
                                    <td>${order.deliveryAddress}</td>
                                    <td><button class="check-status-btn" data-order-id="${order.orderId}">Check Status</button></td>
                                </tr>`;
                });

                content += `</tbody></table>`;
            }

            $("#ordersContainer").html(content);

            // Add event listener for check status buttons
            $(".check-status-btn").on("click", function () {
                const orderId = $(this).data("order-id");
                localStorage.setItem("orderId", orderId);

                $.ajax({
                    url: "/order/order-status/" + orderId,
                    type: "GET",
                    dataType: "json",
                    success: function (statusResponse) {
                        if (statusResponse.status !== 'Delivered') {
                            window.location.href = "/api/customer/order-status";
                        } else {
                            alert(`Order Status: ${statusResponse.status}`);
                        }
                    },
                    error: function () {
                        alert("Failed to fetch order status.");
                    }
                });
            });
        },
        error: function () {
            $("#ordersContainer").html("<p class='text-center text-danger'>Failed to fetch orders.</p>");
        }
    });
});
