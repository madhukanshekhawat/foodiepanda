const statuses = ["PLACED", "PREPARING", "OUT_FOR_DELIVERY", "DELIVERED"]; // Statuses in order
const statusUpdateInterval = 5000; // 10 minutes in milliseconds[10 * 60 * 1000]

// Function to load all orders
function loadOrders(statusFilter = "") {
    $.ajax({
        url: "/order/all", // API endpoint to fetch orders
        method: "GET",
        success: function (data) {
            const container = $("#ordersContainer");
            container.empty(); // Clear the container

            // Filter orders by status if a filter is provided
            const filteredData = statusFilter ? data.filter(order => order.orderStatus.includes(statusFilter)) : data;

            if (filteredData.length === 0) {
                container.append(`
                    <p class="no-orders">No orders found matching the status "${statusFilter}".</p>
                `);
                return;
            }

            // Display each order
            filteredData.forEach(order => {
                const orderDetailsRows = order.orderDetails.map(detail => `
                    <tr>
                        <td><img src="data:image/jpeg;base64,${detail.image}" alt="${detail.menuItem}" style="width: 50px; height: 50px;"/></td>
                        <td>${detail.menuItem}</td>
                        <td>${detail.description}</td>
                        <td>${detail.price} RS.</td>
                        <td>${detail.quantity}</td>
                        <td>${order.totalAmount} RS.</td>
                        <td>${order.deliveryAddress}</td>
                        <td>${order.scheduledTime}</td>
                        <td>${order.orderStatus}</td>
                    </tr>
                `).join('');

                const orderElement = `
                    <div class="order-item" style="margin-bottom: 30px;">
                        <table class="order-details-table">
                            <thead>
                                <tr>
                                    <th>Image</th>
                                    <th>Item Name</th>
                                    <th>Description</th>
                                    <th>Price</th>
                                    <th>Quantity</th>
                                    <th>Total Amount</th>
                                    <th>Delivery Address</th>
                                    <th>Schedule Time</th>
                                    <th>Status</th>
                                </tr>
                            </thead>
                            <tbody>
                                ${orderDetailsRows}
                            </tbody>
                        </table>
                    </div>
                `;
                container.append(orderElement);

                // Start automatic status updates for each order
                startAutoStatusUpdate(order.orderId, order.orderStatus);
            });
        },
        error: function () {
            alert("Error fetching orders.");
        }
    });
}

// Function to start auto-updating the status of an order
function startAutoStatusUpdate(orderId, currentStatus) {
    let currentIndex = statuses.indexOf(currentStatus);

    // Update status every 10 minutes
    setInterval(() => {
        if (currentIndex < statuses.length - 1) {
            currentIndex++;
            const newStatus = statuses[currentIndex];

            // Update status on the server
            $.ajax({
                url: "/order/" + orderId + "/status", // API endpoint for updating status
                method: "PUT",
                contentType: "application/json",
                data: JSON.stringify({ status: newStatus }),
                success: function () {
                    // Update status on the frontend
                    $(`#status-${orderId}`).text(newStatus);
                    console.log(`Order ${orderId} status updated to ${newStatus}`);
                },
                error: function () {
                    console.error(`Error updating status for order ${orderId}`);
                }
            });
        }
    }, statusUpdateInterval);
}

// Load orders on page load
$(document).ready(function () {
    loadOrders();

    // Search input event for dynamic filtering
    $("#searchStatus").on("input", function() {
        const statusFilter = $(this).val().toUpperCase();
        loadOrders(statusFilter);
    });
});