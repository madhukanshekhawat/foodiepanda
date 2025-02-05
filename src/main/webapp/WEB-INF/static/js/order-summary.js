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

            let table = `<table class="table table-bordered">
                            <thead>
                                <tr>
                                    <th>Total Amount</th>
                                    <th>Restaurant Name</th>
                                    <th>Status</th>
                                    <th>Delivery Address</th>
                                    <th>Action</th>
                                </tr>
                            </thead>
                            <tbody>`;

            orders.forEach(order => {
                table += `<tr>
                            <td>₹${order.totalAmount}</td>
                            <td>${order.restaurantName}</td>
                            <td>${order.status}</td>
                            <td>${order.deliveryAddress}</td>
                            <td>${order.status !== 'DELIVERED' ? `<button class="check-status-btn" data-order-id="${order.orderId}">Check Status</button>` : 'NOT ALLOWED'}</td>
                          </tr>`;
            });

            table += "</tbody></table>";
            $("#ordersContainer").html(table);

            // Add event listener for check status buttons
            $(".check-status-btn").on("click", function () {
                const orderId = $(this).data("order-id");
                $.ajax({
                    url:  "/order/order-status/" + orderId,
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