$(document).ready(function() {

            $('#table-item').DataTable({
                pageLength: 10,
                lengthChange: false,
                ordering: true,
                autoWidth: false,
                searching: false});
    // Fetch and populate the menu items
    loadMenuItems();

    // Search input event for dynamic filtering
    $("#searchMenuItem").on("input", function() {
        const searchTerm = $(this).val().toLowerCase();
        filterMenuItems(searchTerm);
    });
});

function loadMenuItems() {
    $.ajax({
        url: "/menu/all", // Backend API endpoint
        method: "GET",
        success: function (data) {
            const container = $("#menuItemsContainer");
            container.empty(); // Clear existing data

            if (data.length === 0) {
                container.append("<p class='no-item'>No menu items found.</p>");
                return;
            }

            // Create table
            const table = `
                <table id = "table-item">
                    <thead>
                        <tr>
                            <th>Name</th>
                            <th>Description</th>
                            <th>Price (RS)</th>
                            <th>Category</th>
                            <th>Image</th>
                            <th>Availability</th>
                            <th>Action</th>
                        </tr>
                    </thead>
                    <tbody id="menuItemsTableBody">
                    </tbody>
                </table>
            `;
            container.append(table);

            // Populate table with menu items
            const tableBody = $("#menuItemsTableBody");
            data.forEach(item => {
                const menuItem = `
                    <tr class="menu-item">
                        <td>${item.name}</td>
                        <td>${item.description}</td>
                        <td>${item.price ? item.price : 'N/A'}</td>
                        <td>${item.categoryName ? item.categoryName : 'Uncategorized'}</td>
                        <td><img src="data:image/jpeg;base64,${item.image}" alt="${item.name}" style="width: 150px; height: 100px;"/></td>
                        <td>
                            <select id="availability-${item.id}" onchange="confirmAvailabilityChange(${item.id}, this.value)">
                                <option value="true" ${item.available ? 'selected' : ''}>Available</option>
                                <option value="false" ${!item.available ? 'selected' : ''}>Unavailable</option>
                            </select>
                        </td>

                        <td>
                            <button class="btn btn-danger btn-sm" onclick="deleteMenuItem(${item.id})">Delete</button>
                            <button class="btn btn-primary btn-sm" onclick="openEditPopup(${item.id})">Edit</button>
                        </td>
                    </tr>
                `;
                tableBody.append(menuItem);
            });
        },
        error: function () {
            alert("Error fetching menu items.");
        }
    });
}

function filterMenuItems(searchTerm) {
    // Fetch all menu items
    $.ajax({
        url: "/menu/all", // API endpoint to fetch all menu items
        method: "GET",
        success: function(data) {
            const container = $("#menuItemsContainer");
            container.empty(); // Clear the container

            // Filter menu items based on the search term
            const filteredData = data.filter(item => item.name.toLowerCase().includes(searchTerm));

            if (filteredData.length === 0) {
                container.append(`
                    <p class="no-item">No menu items found matching "${searchTerm}".</p>
                `);
                return;
            }

            // Create table
            const table = `
                <table>
                    <thead>
                        <tr>
                            <th>Name</th>
                            <th>Description</th>
                            <th>Price (RS)</th>
                            <th>Category</th>
                            <th>Image</th>
                            <th>Availability</th>
                            <th>Action</th>
                        </tr>
                    </thead>
                    <tbody id="menuItemsTableBody">
                    </tbody>
                </table>
            `;
            container.append(table);

            // Populate table with filtered menu items
            const tableBody = $("#menuItemsTableBody");
            filteredData.forEach(item => {
                const menuItem = `
                    <tr class="menu-item">
                        <td>${item.name}</td>
                        <td>${item.description}</td>
                        <td>${item.price ? item.price : 'N/A'}</td>
                        <td>${item.categoryName ? item.categoryName : 'Uncategorized'}</td>
                        <td><img src="data:image/jpeg;base64,${item.image}" alt="${item.name}" style="width: 150px; height: 100px;"/></td>
                        <td>
                            <select id="availability-${item.id}" onchange="confirmAvailabilityChange(${item.id}, this.value)">
                                <option value="true" ${item.available ? 'selected' : ''}>Available</option>
                                <option value="false" ${!item.available ? 'selected' : ''}>Unavailable</option>
                            </select>
                        </td>
                        <td>
                            <button class="btn btn-danger btn-sm" onclick="deleteMenuItem(${item.id})">Delete</button>
                            <button class="btn btn-primary btn-sm" onclick="openEditPopup(${item.id})">Edit</button>
                        </td>
                    </tr>
                `;
                tableBody.append(menuItem);
            });
        },
        error: function() {
            alert("Error fetching menu items.");
        }
    });
}

        function openEditPopup(menuItemId) {
            $.ajax({
                url: "/menu/" + menuItemId,
                type: 'GET',
                success: function(item) {
                    $('#menuItemId').val(item.id);
                    $('#menuItemName').val(item.name);
                    $('#menuItemDescription').val(item.description);
                    $('#menuItemPrice').val(item.price);
                    $('#editMenuItemModal').modal('show');
                },
                error: function(error) {
                    console.error(error);
                }
            });
        }

        function submitEditMenuItem() {
            const menuItemId = $('#menuItemId').val();
            const updatedData = {
                name: $('#menuItemName').val(),
                description: $('#menuItemDescription').val(),
                price: $('#menuItemPrice').val(),
                available: $('#menuItemAvailability').val() === 'true'
            };

            $.ajax({
                url: "/menu/update/" +menuItemId ,
                type: 'PUT',
                contentType: 'application/json',
                data: JSON.stringify(updatedData),
                success: function(response) {
                    $('#editMenuItemModal').modal('hide');
                    loadMenuItems();
                    alert('Menu item updated successfully!');
                },
                error: function(error) {
                    console.error(error);
                }
            });
        }


function confirmAvailabilityChange(menuItemId, newValue) {
    const confirmed = confirm("Are you sure you want to change the availability status?");
    if (confirmed) {
        $.ajax({
            url: "/menu/" + menuItemId + "/availability",
            method: "PUT",
            data: { available: newValue },
            success: function () {
                alert("Availability status updated successfully.");
                loadMenuItems();
            },
            error: function () {
                alert("Error updating availability status.");
            }
        });
    }
}

function deleteMenuItem(menuItemId) {
    if (confirm("Are you sure you want to delete this menu item?")) {
        $.ajax({
            url: "/menu/" + menuItemId,
            method: "DELETE",
            success: function () {
                alert("Menu item deleted successfully.");
                loadMenuItems(); // Reload menu items
            },
            error: function () {
                alert("Failed to delete menu item.");
            }
        });
    }
}