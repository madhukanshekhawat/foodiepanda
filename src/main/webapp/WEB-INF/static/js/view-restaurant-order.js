const statuses = ["PENDING", "CONFIRMED", "PREPARING", "OUT_FOR_DELIVERY", "DELIVERED"];
const autoDeliveryTime = 1*60*1000;

// Function to load all orders
function loadOrders(statusFilter = "") {
    $.ajax({
        url: "/api/restaurant/order/all",
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

            // Create a flex container for orders
            const ordersContainer = $('<div class="orders-container"></div>');
            container.append(ordersContainer);

            // Display each order
            filteredData.forEach(order => {
                const orderDetailsRows = order.orderDetails.map(detail => `
                    <tr>
                        <td><img src="data:image/jpeg;base64,${detail.image}" alt="${detail.menuItem}" style="width: 50px; height: 50px;"/></td>
                        <td>${detail.menuItem}</td>
                        <td>${detail.price} RS.</td>
                        <td>${detail.quantity}</td>
                    </tr>
                `).join('');

                const orderElement = `
                    <div class="order-item">
                        <h3>Orders for ${order.userName}</h3>
                        <table class="order-details-table">
                            <thead>
                                <tr>
                                    <th>Image</th>
                                    <th>Item Name</th>
                                    <th>Price</th>
                                    <th>Quantity</th>
                                </tr>
                            </thead>
                            <tbody>
                                ${orderDetailsRows}
                                <tr>
                                    <td colspan="3" style="text-align: right;"><strong>Total Amount:</strong></td>
                                    <td>${order.totalAmount} RS.</td>
                                </tr>
                                <tr>
                                    <td colspan="3" style="text-align: right;"><strong>Delivery Address:</strong></td>
                                    <td>${order.deliveryAddress}</td>
                                </tr>
                                <tr>
                                    <td colspan="3" style="text-align: right;"><strong>Schedule Time:</strong></td>
                                    <td>${order.scheduledTime}</td>
                                </tr>
                                <tr>
                                    <td colspan="3" style="text-align: right;"><strong>Status:</strong></td>
                                    <td id="status-${order.orderId}">${order.orderStatus}</td>
                                </tr>
                            </tbody>
                        </table>
                        <div id="actions-${order.orderId}" class="actions">
                            ${generateActionButtons(order.orderId, order.orderStatus)}
                        </div>
                    </div>
                `;
                ordersContainer.append(orderElement);

                // If the order is OUT_FOR_DELIVERY, start the auto-delivery timer
                if (order.orderStatus === "OUT_FOR_DELIVERY") {
                    startAutoDelivery(order.orderId);
                }
            });
        },
        error: function () {
            alert("Error fetching orders.");
        }
    });
}

// Function to generate action buttons based on current status
function generateActionButtons(orderId, currentStatus) {
    const currentIndex = statuses.indexOf(currentStatus);
    if (currentIndex === -1 || currentIndex >= statuses.length - 2) {
        return ''; // No buttons for invalid status or if status is DELIVERED
    }

    const nextStatus = statuses[currentIndex + 1];
    return `
        <button class="action-btn" onclick="updateOrderStatus(${orderId}, '${nextStatus}')">${nextStatus}</button>
    `;
}

// Function to update the order status
function updateOrderStatus(orderId, newStatus) {
    $.ajax({
        url: "/api/restaurant/" +orderId+ "/status", // API endpoint for updating status
        method: "PUT",
        contentType: "application/json",
        data: JSON.stringify({ status: newStatus }),
        success: function () {
            // Update status on the frontend
            $(`#status-${orderId}`).text(newStatus);
            $(`#actions-${orderId}`).html(generateActionButtons(orderId, newStatus));

            // If the new status is OUT_FOR_DELIVERY, start the auto-delivery timer
            if (newStatus === "OUT_FOR_DELIVERY") {
                startAutoDelivery(orderId);
            }
        },
        error: function () {
            alert("Not able to change order status")
        }
    });
}

// Function to start the auto-delivery timer
function startAutoDelivery(orderId) {
    setTimeout(() => {
        updateOrderStatus(orderId, "DELIVERED");
    }, 3000);
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
