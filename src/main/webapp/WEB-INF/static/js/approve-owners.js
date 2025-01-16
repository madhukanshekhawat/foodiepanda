 $(document).ready(function () {
        fetchUnapprovedOwners();
    });

    function fetchUnapprovedOwners() {
        $.ajax({
            url: "/api/admin/unapproved-owner",
            method: "GET",
            success: function (response) {
                let tableData = "";
                response.forEach(restaurantOwner => {
                    tableData += `
                        <tr>
                            <td>${restaurantOwner.first_name} ${restaurantOwner.lastName}</td>
                            <td>${restaurantOwner.lastName}</td>
                            <td>${restaurantOwner.phoneNumber}</td>
                            <td>${restaurantOwner.email}</td>
                            <td>
                            <button onclick="approveOwner(${restaurantOwner.id})">Approve</button>
                            <button onclick="softDeleteOwner(${restaurantOwner.id})">Reject</button>
                            </td>
                        </tr>
                    `;
                });
                $("#owners-table").html(tableData);
            },
            error: function () {
                $("#owners-table").html("<tr><td colspan='3'>Error loading data</td></tr>");
            }
        });
    }
    // Function to handle approve/delete actions with confirmation
                // Approve an owner
                function approveOwner(ownerId) {
                    $.ajax({
                        url: "/api/admin/approve/" + ownerId, // API endpoint to approve the owner
                        method: "PUT", // HTTP method PUT for approving
                        success: function(response) {
                            alert("Owner approved successfully!");
                            location.reload(); // Reload the page to reflect the changes
                        },
                        error: function() {
                            alert("Error approving owner.");
                        }
                    });
                }

                // Soft Delete an owner
                function softDeleteOwner(ownerId) {
                    $.ajax({
                        url: "/api/admin/reject/" + ownerId, // API endpoint to soft delete the owner
                        method: "PATCH", // HTTP method PATCH for soft delete
                        success: function(response) {
                            alert("Owner deleted successfully!");
                            location.reload(); // Reload the page to reflect the changes
                        },
                        error: function() {
                            alert("Error deleting owner.");
                        }
                    });
                }

            // Load owners on page load
            $(document).ready(function() {
                fetchUnapprovedOwners();
            });