
    $(document).ready(function() {
        // Get orderId from local storage
        var orderId = localStorage.getItem("orderId");

        // If orderId exists in local storage
        if (orderId) {
            $.ajax({
                url: "/order/order-status/" + orderId, // Fetch order status using orderId
                method: "GET",
                success: function(response) {
                    // Display the order status
                    if (response.status) {
                        $("#orderStatus").html(`
                            <h4>Whoo! Order Placed Successfully </h4>
                            <p>Status: <strong>${response.status}</strong></p>
                        `);
                    } else {
                        $("#orderStatus").html("<p>Order status not found.</p>");
                    }
                },
                error: function(xhr, status, error) {
                    // Error fetching order status
                    $("#orderStatus").html("<p>Error fetching order status. Please try again later.</p>");
                }
            });
        } else {
            $("#orderStatus").html("<p>No order ID found. Please check your order history.</p>");
        }
    });