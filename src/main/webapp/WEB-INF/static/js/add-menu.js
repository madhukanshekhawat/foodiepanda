 $(document).ready(function() {
            // Fetch and populate the category dropdown for the specific restaurant
            $.ajax({
                url: "/categories/allCategory",
                method: "GET",
                success: function(data) {
                    let categoryDropdown = $("#category");
                    categoryDropdown.empty();
                    data.forEach(category => {
                         categoryDropdown.append(`<option value="${category.categoryId}">${category.name}</option>`);
                    });
                },
                error: function(xhr) {
                    showError("categoryError", "Error fetching categories.");
                }


            });

            $("#menuItemForm").submit(function(event) {
                event.preventDefault(); // Prevent form submission
                addMenuItem();
            });

            // Clear error messages when user starts typing
            $("input, select").on("input change", function() {
                clearError($(this).attr("id") + "Error");
            });
        });

        function showError(elementId, message) {
            $("#" + elementId).text(message);
        }

        function clearError(elementId) {
            $("#" + elementId).text("");
        }

        function convertImageToBase64(file, callback) {
            const reader = new FileReader();
            reader.onload = function(event) {
                callback(event.target.result.split(',')[1]);
            };
            reader.readAsDataURL(file);
        }

        function addMenuItem() {
            let fileInput = $("#image")[0].files[0];

            // Convert image to Base64 and submit
            convertImageToBase64(fileInput, function(base64Image) {
                let formData = {
                    name: $("#name").val(),
                    description: $("#description").val(),
                    price: $("#price").val(),
                    available: $("#available").is(":checked"),
                    veg: $("#isVeg").is(":checked"),
                    category: $("#category").val(),
                    image: base64Image
                };

                // Validation checks
                let isValid = true;

                if (!formData.category) {
                    showError("categoryError", "Category is required.");
                    isValid = false;
                }

                if (!isValid) {
                    return;
                }

                $.ajax({
                    url: "/menu/add",
                    method: "POST",
                    contentType: "application/json",
                    data: JSON.stringify(formData),
                    success: function(response) {
                        alert("Menu item added successfully!");
                        $("#menuItemForm")[0].reset();
                    },
                    error: function(xhr) {
                        showError("errorMessages", "Error adding menu item.");
                    }
                });
            });
        }
