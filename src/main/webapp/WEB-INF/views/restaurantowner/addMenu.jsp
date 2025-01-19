<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/restaurantowner/base.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Add Menu Item</title>
    <link rel="stylesheet" href="styles.css">
</head>
<body>
    <h1>Add Menu Item</h1>
    <form id="menuItemForm">
        <label for="name">Item Name:</label>
        <input type="text" id="name" name="name" required><br>

        <label for="description">Description:</label>
        <textarea id="description" name="description"></textarea><br>

        <label for="price">Price:</label>
        <input type="number" step="0.01" id="price" name="price" required><br>

        <label for="isAvailable">Available:</label>
        <input type="checkbox" id="isAvailable" name="isAvailable"><br>

        <label for="isVeg">Vegetarian:</label>
        <input type="checkbox" id="isVeg" name="isVeg"><br>

        <label for="categorySelect">Category:</label>
        <select id="categorySelect" name="categoryName" required>
            <option value="">Select a category</option>
        </select><br>

        <button type="submit">Add Menu Item</button>
    </form>

    <script>
    document.addEventListener('DOMContentLoaded', function() {
        fetch('/api/owner/categories/all')
            .then(response => response.json())
            .then(data => {
                const categorySelect = document.getElementById('categorySelect');
                data.forEach(category => {
                    const option = document.createElement('option');
                    option.value = category.name;
                    option.textContent = category.name;
                    categorySelect.appendChild(option);
                });
            })
            .catch(error => console.error('Error fetching categories:', error));

        document.getElementById('menuItemForm').addEventListener('submit', function(event) {
            event.preventDefault();

            const formData = {
                name: document.getElementById('name').value,
                description: document.getElementById('description').value,
                price: document.getElementById('price').value,
                isAvailable: document.getElementById('isAvailable').checked,
                isVeg: document.getElementById('isVeg').checked,
                categoryName: document.getElementById('categorySelect').value
            };

            const token = localStorage.getItem('jwtToken'); // Assuming the token is stored in localStorage

            fetch('/api/owner/menu/add', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': 'Bearer ' + token
                },
                body: JSON.stringify(formData)
            })
            .then(response => {
                if (response.ok) {
                    alert('Menu item added successfully!');
                    document.getElementById('menuItemForm').reset();
                } else {
                    return response.json().then(data => {
                        throw new Error(data.message || 'Failed to add menu item');
                    });
                }
            })
            .catch(error => alert('Error: ' + error.message));
        });
    });
    </script>
</body>
</html>