        // Fetch all menu items for the logged-in restaurant owner
        function loadMenuItems() {
            $.ajax({
                url: "/menu/all", // Backend API endpoint
                method: "GET",
                success: function (data) {
                    const container = $("#menuItemsContainer");
                    container.empty(); // Clear existing data

                    if (data.length === 0) {
                        container.append("<p>No menu items found.</p>");
                        return;
                    }

                    // Dynamically add menu items
                    data.forEach(item => {
                        const menuItem = `
                            <div class="menu-item">
                                <h3>${item.name}</h3>
                                <p>Description: ${item.description}</p>
                                <p>Price: ${item.price ? item.price : 'N/A'} RS.</p>
                                <p>Category: ${item.categoryName ? item.categoryName : 'Uncategorized'}</p>
                                <img src="data:image/jpeg;base64,${item.image}" alt="${item.name}" style="width: 150px; height: 100px;"/>
                                <div>
                                    <label for="availability-${item.id}">Change Availability:</label>
                                    <select id="availability-${item.id}" onchange="confirmAvailabilityChange(${item.id}, this.value)">
                                        <option value="true" ${item.available ? 'selected' : ''}>Available</option>
                                        <option value="false" ${!item.available ? 'selected' : ''}>Unavailable</option>
                                    </select>
                                </div>
                            </div>
                        `;
                        container.append(menuItem);
                    });
                },
                error: function () {
                    alert("Error fetching menu items.");
                }
            });
        }

        // Confirm and change availability status
        function confirmAvailabilityChange(menuItemId, newValue) {
            const confirmed = confirm("Are you sure you want to change the availability status?");
            $.ajax({
                        url: "/menu/" + menuItemId + "/availability",
                        method: "PUT",
                        data: {available : newValue},
                        success: function () {
                            alert("Availability status updated successfully.");
                            loadMenuItems(); // Reload the menu items
                        },
                        error: function () {
                            alert("Error updating availability status.");
                            dropdown.value=previousValue;
                        }
                    });

        }

        // Load menu items on page load
        $(document).ready(function () {
            loadMenuItems();
        });
