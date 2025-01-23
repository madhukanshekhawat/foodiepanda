    // Fetch categories on page load
    $(document).ready(function() {
        loadCategories();
    });

function loadCategories() {
        $.ajax({
            url: '/categories/allCategory',
            method: 'GET',
            success: function(categories) {
              let tableData = "";
              categories.forEach (category =>{
              tableData += `<tr>
              <td>${category.name}</td>
              <td>${category.description}</td>
              <td>
              <button onclick="deleteCategory(${category.categoryId})">Delete</button>
              </td>
              </tr>`;
              });
              $("#categoriesData").html(tableData)
            },
            error: function() {
               $("#categoriesData").html("<tr><td colspan='3'>Error loading data</td></tr>");
            }
        });
    }

    // Function to delete a category
    function deleteCategory(id) {
        if (confirm('Are you sure you want to delete this category?')) {
            $.ajax({
                url: "/categories/delete/"+ id,
                method: 'DELETE',
                success: function(response) {
                    alert("Category Deleted successfully!");
                    location.reload();
                },
                error: function() {
                    alert('Error deleting category ! ');
                }
            });
        }
    }
      $(document).ready(function() {
            loadCategories();
        });