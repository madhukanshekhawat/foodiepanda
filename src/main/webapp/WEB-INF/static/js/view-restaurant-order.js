const statuses = ["PLACED", "PREPARING", "OUT_FOR_DELIVERY", "DELIVERED"]; // Statuses in order
        const statusUpdateInterval = 5000; // 10 minutes in milliseconds[10 * 60 * 1000]

        // Function to load all orders
 function loadOrders() {
     $.ajax({
         url: "/order/all", // API endpoint to fetch orders
         method: "GET",
         success: function (data) {
             const container = $("#ordersContainer");
             container.empty(); // Clear the container

             if (data.length === 0) {
                 container.append("<p>No orders found.</p>");
                 return;
             }

             // Display each order
             data.forEach(order => {
                 const orderDetailsHtml = order.orderDetails.map(detail => `
                     <div class="order-detail-item" style="margin-left: 20px;">
                         <p>Name: ${detail.menuItem}</p>
                         <p>Description: ${detail.description}</p>
                         <p>Price: ${detail.price} RS.</p>
                         <p>Quantity: ${detail.quantity}</p>
                         <img src="data:image/jpeg;base64,${detail.image}" alt="${detail.menuItem}" style="width: 150px; height: 100px;"/>
                     </div>
                 `).join('');

                 const orderElement = `
                     <div class="order-item" style="border: 1px solid #ddd; padding: 10px; margin-bottom: 15px;">
                         <p>Status: <strong id="status-${order.orderId}">${order.orderStatus}</strong></p>
                         <p>Total Amount: ${order.totalAmount} RS.</p>
                         <p>Delivery Address: ${order.deliveryAddress}</p>
                         <p>Schedule Time: ${order.scheduledTime}</p>
                         <h4>Menu Details:</h4>
                         ${orderDetailsHtml}
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
        });