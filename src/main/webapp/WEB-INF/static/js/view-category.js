$(document).ready(function() {
    loadCategories();
});

function loadCategories() {
    $.ajax({
        url: '/categories/allCategory',
        method: 'GET',
        success: function(categories) {
            const container = $('#categoriesContainer');
            container.empty(); // Clear existing content

            if (categories.length === 0) {
                container.append("<p class='no-categories'>No categories found.</p>");
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
            categories.forEach(category => {
                const row = `
                    <tr>
                        <td>${category.name}</td>
                        <td>${category.description}</td>
                        <td>
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

// Function to delete a category
function deleteCategory(id) {
    $.ajax({
        url: "/categories/delete/" + id,
        method: 'DELETE',
        success: function(response) {
            loadCategories(); // Reload the table to reflect changes
        },
        error: function() {
            alert('Error deleting category!');
        }
    });
}