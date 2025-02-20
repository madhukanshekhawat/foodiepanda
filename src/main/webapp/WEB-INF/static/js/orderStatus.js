
    $(document).ready(function() {
        // Get orderId from local storage
        var orderId = localStorage.getItem("orderId");

        if (orderId) {
            $.ajax({
                url: "/order/order-status/" + orderId,
                method: "GET",
                success: function(response) {
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
                    $("#orderStatus").html("<p>Error fetching order status. Please try again later.</p>");
                }
            });
        } else {
            $("#orderStatus").html("<p>No order ID found. Please check your order history.</p>");
        }
    });