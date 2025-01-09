
        $(document).ready(function() {
            loadApprovedOwners();
        });

        function loadApprovedOwners() {
            $.ajax({
                url: "/admin/approved-owner", // API endpoint
                method: "GET",
                dataType: "json", // Ensure the response is parsed as JSON
                success: function(response) {
                    console.log("API Response:", response); // Debugging

                    response.forEach(restaurantOwner => {
                    let row = document.createElement('tr');
                       row.innerHTML =
                           `<td>${restaurantOwner.firstName}</td>
                            <td>${restaurantOwner.email}</td>
                            <td>${restaurantOwner.phoneNumber}</td>
                        `;
                        $("#ownersTable").append(row);
                    });

                },
                error: function(xhr, status, error) {
                    console.error("Error loading approved owners:", xhr.responseText);
                    $("#ownersTable tbody").html("<tr><td colspan='4'>Error loading data</td></tr>");
                }
            });
        }

