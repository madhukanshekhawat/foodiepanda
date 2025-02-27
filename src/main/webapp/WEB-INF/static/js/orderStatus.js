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
                    // Fetch the order details to get the createdAt time
                    $.ajax({
                        url: "/order/get",
                        method: "GET",
                        data: {orderId: orderId},
                        success: function(orderDetails) {
                            console.log("Order details response:", orderDetails);
                            if (orderDetails && orderDetails.length > 0) {
                                var data = orderDetails[0]; // Access the first element of the array
                                var createdAt = new Date(data.orderDetail[0].createdAt).getTime();
                                var currentTime = new Date().getTime();
                                var timeElapsed = currentTime - createdAt;

                                // Display order details
                                displayOrderDetails(data);

                                if (response.status.toLowerCase() === "pending" && timeElapsed > 10 * 60 * 1000) { // 10 minutes in milliseconds
                                    $.ajax({
                                        url: "/order/auto-cancel/" + orderId,
                                        method: "PUT",
                                        data: JSON.stringify({ status: "CANCELLED" }),
                                        contentType: "application/json",
                                        success: function(autoCancelResponse) {
                                            console.log("Auto-cancel order response:", autoCancelResponse);
                                            $("#orderStatus").html("<p style='color: red;'>Order was not accepted within 10 minutes, so it has been automatically cancelled.</p>");
                                            displayOrderDetails(data); // Display order details after auto-cancellation
                                        },
                                        error: function(xhr, status, error) {
                                            console.error("Error auto-cancelling order:", error);
                                            $("#orderStatus").html("<p>Error auto-cancelling order. Please try again later.</p>");
                                        }
                                    });
                                } else if (response.status.toLowerCase() === "pending") {
                                    if (timeElapsed < 2 * 60 * 1000) { // 2 minutes in milliseconds
                                        $("#orderStatus").html(`
                                            <h4>Whoo! Order Placed Successfully </h4>
                                            <p>Status: <strong>${response.status}</strong></p>
                                            <button id="cancelOrderBtn">Cancel Order</button>
                                        `);

                                        // Enable the cancel button for the remaining time
                                        setTimeout(function() {
                                            $("#cancelOrderBtn").prop("disabled", true);
                                            $("#cancelOrderBtn").after("<p>Now you cannot cancel the order.</p>");
                                        }, 2 * 60 * 1000 - timeElapsed); // Remaining time in milliseconds

                                        // Add click event listener to the cancel button
                                        $("#cancelOrderBtn").click(function() {
                                            $.ajax({
                                                url: "/order/cancel/" + orderId,
                                                method: "PUT",
                                                success: function(cancelResponse) {
                                                    console.log("Cancel order response:", cancelResponse);
                                                    $("#orderStatus").html("<p style='color: red;'>Order has been cancelled successfully.</p>");
                                                    displayOrderDetails(data); // Display order details after cancellation
                                                },
                                                error: function(xhr, status, error) {
                                                    console.error("Error cancelling order:", error);
                                                    $("#orderStatus").html("<p>Error cancelling order. Please try again later.</p>");
                                                }
                                            });
                                        });
                                    } else {
                                        $("#orderStatus").html(`
                                            <h4>Whoo! Order Placed Successfully </h4>
                                            <p>Status: <strong>${response.status}</strong></p>
                                            <p>Now you cannot cancel the order.</p>
                                        `);
                                    }
                                } else {
                                    $("#orderStatus").html(`
                                        <h4>Order Status</h4>
                                        <p>Status: <strong>${response.status}</strong></p>
                                    `);
                                }
                            } else {
                                $("#order-data").html("<p>Order details not found.</p>");
                            }
                        },
                        error: function(xhr, status, error) {
                            console.error("Error fetching order details:", error);
                            $("#order-data").html("<p>Error fetching order details. Please try again later.</p>");
                        }
                    });
                } else {
                    $("#orderStatus").html("<p>Order status not found.</p>");
                }
            },
            error: function(xhr, status, error) {
                console.error("Error fetching order status:", error);
                $("#orderStatus").html("<p>Error fetching order status. Please try again later.</p>");
            }
        });
    } else {
        $("#orderStatus").html("<p>No order ID found. Please check your order history.</p>");
    }

    function displayOrderDetails(data) {
        var menuItemsHtml = '';
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

        // Show the button only if the status is "accepted"
            if (data.status != 'PENDING' && data.status != 'CANCELLED') {
                $(".generate-invoice-btn").show();
            } else {
                $(".generate-invoice-btn").hide();
            }

    }

    // Add event listener for generate invoice buttons
        $(".generate-invoice-btn").on("click", function () {
            $.ajax({
                url: "/order/generate-invoice/" + orderId,
                type: "GET",
                xhrFields: {
                    responseType: 'blob'
                },
                success: function (data) {
                    const url = window.URL.createObjectURL(new Blob([data]));
                    const a = document.createElement('a');
                    a.href = url;
                    a.download = `invoice_${orderId}.pdf`;
                    document.body.append(a);
                    a.click();
                    a.remove();
                    window.URL.revokeObjectURL(url);
                    alert("Invoice generated successfully!");
                },
                error: function () {
                    alert("Failed to generate invoice.");
                }
            });
        });
});