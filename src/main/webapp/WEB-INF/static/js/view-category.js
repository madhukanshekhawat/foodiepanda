$(document).ready(function() {
    loadCategories();

    // Search input event for dynamic filtering
    $("#searchCategory").on("input", function() {
        const categoryFilter = $(this).val().toUpperCase();
        loadCategories(categoryFilter);
    });
});

function loadCategories(categoryFilter = "") {
    $.ajax({
        url: '/categories/allCategory',
        method: 'GET',
        success: function(categories) {
            const container = $('#categoriesContainer');
            container.empty(); // Clear existing content

            // Filter categories if a filter is provided
            const filteredData = categoryFilter ? categories.filter(item => item.name.toUpperCase().includes(categoryFilter)) : categories;

            if (filteredData.length === 0) {
                container.append(`
                    <p class="no-categories">No categories found matching "${categoryFilter}".</p>
                `);
                return;
            }

            // Create table
            const table = `
                <table border="1" id="categoriesTable">
                    <thead>
                        <tr>
                            <th>Name</th>
                            <th>Description</th>
                            <th>Action</th>
                        </tr>
                    </thead>
                    <tbody id="categoriesTableBody">
                    </tbody>
                </table>
            `;
            container.append(table);

            // Populate table with categories
            const tbody = $('#categoriesTableBody');
            filteredData.forEach(category => {
                const row = `
                    <tr>
                        <td>${category.name}</td>
                        <td>${category.description}</td>
                        <td>
                            <button onclick="showEditModal(${category.categoryId})">Edit</button>
                            <button onclick="showDeleteModal(${category.categoryId})">Delete</button>
                        </td>
                    </tr>
                `;
                tbody.append(row);
            });
        },
        error: function() {
            const container = $('#categoriesContainer');
            container.empty();
            container.append("<p class='no-categories'>Error loading data</p>");
        }
    });
}

function showDeleteModal(categoryId) {
    const modal = $('#deleteModal');
    modal.show();

    $('#confirmDelete').off('click').on('click', function() {
        deleteCategory(categoryId);
        modal.hide();
    });

    $('#cancelDelete').off('click').on('click', function() {
        modal.hide();
    });
}

function showEditModal(id) {
    $.ajax({
        url: "/categories/edit/" + id,
        method: 'GET',
        success: function(category) {
            $('#editCategoryName').val(category.name);
            $('#editCategoryDescription').val(category.description);
            $('#updateCategory').prop('disabled', true); // Disable update button initially

            const originalData = {
                name: category.name,
                description: category.description
            };

            $('#editModal').show();

            $('#editCategoryName, #editCategoryDescription').on('input', function() {
                const currentData = {
                    name: $('#editCategoryName').val(),
                    description: $('#editCategoryDescription').val()
                };

                const isChanged = currentData.name !== originalData.name || currentData.description !== originalData.description;
                $('#updateCategory').prop('disabled', !isChanged);
            });

            $('#updateCategory').off('click').on('click', function() {
                updateCategory(id);
                $('#editModal').hide();
            });

            $('#cancelEdit').off('click').on('click', function() {
                $('#editModal').hide();
            });
        },
        error: function() {
            alert('Error loading category data');
        }
    });
}

  function updateCategory(id) {
    const updatedCategory = {
        name: $('#editCategoryName').val(),
        description: $('#editCategoryDescription').val()
    };

    $.ajax({
        url: "/categories/edit/" + id,
        method: 'PUT',
        contentType: 'application/json',
        data: JSON.stringify(updatedCategory),
        success: function(response) {
            loadCategories(); // Reload the table to reflect changes
        },
        error: function() {
            alert('Error updating category');
        }
    });
}

function deleteCategory(id) {
    $.ajax({
        url: "/categories/delete/" + id,
        method: 'DELETE',
        success: function(response) {
            loadCategories(); // Reload the table to reflect changes
        },
        error: function() {
            alert('We cannot delete category until it contains items!');
        }
    });
}