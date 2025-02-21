$(document).ready(function() {
    // Get orderId from local storage
    var orderId = localStorage.getItem("orderId");

    if (orderId) {
        $.ajax({
            url: "/order/order-status/" + orderId,
            method: "GET",
            success: function(response) {
                console.log("Order status response:", response);
                if (response.status) {
                    $("#orderStatus").html(`
                        <h4>Whoo! Order Placed Successfully </h4>
                        <p>Status: <strong>${response.status}</strong></p>
                    `);
                } else {
                    $("#orderStatus").html("<p>Order status not found.</p>");
                }

                // Fetch additional order details
                $.ajax({
                    url: "/order/get",
                    method: "GET",
                    data: {orderId: orderId},
                    success: function(orderDetails) {
                        console.log("Order details response:", orderDetails);
                        if (orderDetails && orderDetails.length > 0) {
                            var data = orderDetails[0]; // Access the first element of the array
                            var menuItemsHtml = '';

                            // Iterate over the orderDetail array to get menu items
                            data.orderDetail.forEach(function(detail) {
                                menuItemsHtml += `
                                    <p>Menu Item: <strong>${detail.menuItems.name || 'N/A'}</strong></p>
                                    <p>Quantity: <strong>${detail.quantity || 'N/A'}</strong></p>
                                    <p>Unit Price: <strong>${detail.price || 'N/A'}</strong></p>
                                    <hr>
                                `;
                            });

                            $("#order-data").html(`
                                <p>Customer Name: <strong>${data.customerFullName || 'N/A'}</strong></p>
                                <p>Restaurant Name: <strong>${data.restaurantName || 'N/A'}</strong></p>
                                <p>Address: <strong>${data.address || 'N/A'}</strong></p>
                                <p>Total Amount: <strong>${data.totalAmount || 'N/A'}</strong></p>
                                <h4>Menu Items:</h4>
                                ${menuItemsHtml}
                            `);
                        } else {
                            $("#order-data").html("<p>Order details not found.</p>");
                        }
                    },
                    error: function(xhr, status, error) {
                        console.error("Error fetching order details:", error);
                        $("#order-data").html("<p>Error fetching order details. Please try again later.</p>");
                    }
                });
            },
            error: function(xhr, status, error) {
                console.error("Error fetching order status:", error);
                $("#orderStatus").html("<p>Error fetching order status. Please try again later.</p>");
            }
        });
    } else {
        $("#orderStatus").html("<p>No order ID found. Please check your order history.</p>");
    }
});