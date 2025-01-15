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
                            <td>${restaurantOwner.phoneNumber}</td>
                            <td>${restaurantOwner.email}</td>
                            <td>
                            <button onclick="confirmAction('approve', ${restaurantOwner.id})">Approve</button>
                            <button onclick="confirmAction('reject', ${restaurantOwner.id})">Reject</button>
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
            function confirmAction(action, ownerId) {
                let confirmationMessage = (action === 'approve') ? 'Are you sure you want to approve this owner?' : 'Are you sure you want to Reject this owner?';

                if (confirm(confirmationMessage)) {
                    $.ajax({
                        url: '/api/admin/' + ownerId + '/' + action,  // The endpoint to handle approve/delete
                        method: 'POST',
                        success: function(response) {
                            alert(action.charAt(0).toUpperCase() + action.slice(1) + ' action successful!');
                            fetchUnapprovedOwners();  // Reload owners list after successful action
                        },
                        error: function(error) {
                            alert('Error performing the action.');
                        }
                    });
                }
            }

            // Load owners on page load
            $(document).ready(function() {
                fetchUnapprovedOwners();
            });