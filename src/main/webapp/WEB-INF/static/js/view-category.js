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
                            <button onclick="deleteCategory(${category.categoryId})">Delete</button>
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

// Function to delete a category
function deleteCategory(id) {
    if (confirm('Are you sure you want to delete this category?')) {
        $.ajax({
            url: "/categories/delete/" + id,
            method: 'DELETE',
            success: function(response) {
                alert("Category deleted successfully!");
                loadCategories(); // Reload the table to reflect changes
            },
            error: function() {
                alert('Error deleting category!');
            }
        });
    }
}