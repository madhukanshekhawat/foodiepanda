<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/restaurantowner/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>Add Menu Item</title>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <style>
            body {
                font-family: Arial, sans-serif;
                background-color: #f4f4f4;
                margin: 0;
                padding: 0;
            }

            h2 {
                text-align: center;
                color: #333;
            }

            form {
                max-width: 600px;
                margin: 50px auto;
                padding: 20px;
                background-color: #fff;
                border-radius: 8px;
                box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            }

            label {
                display: block;
                margin-bottom: 8px;
                font-weight: bold;
                color: #555;
            }

            input[type="text"],
            input[type="number"],
            input[type="file"],
            textarea,
            select {
                width: 100%;
                padding: 10px;
                margin-bottom: 20px;
                border: 1px solid #ccc;
                border-radius: 4px;
                box-sizing: border-box;
            }

             .checkbox-group {
                        display: flex;
                        align-items: center;
                        margin-bottom: 20px;
             }

            .checkbox-group label {
                margin-right: 10px;
                font-weight: normal;
            }

            button {
                width: 100%;
                padding: 10px;
                background-color: #28a745;
                color: #fff;
                border: none;
                border-radius: 4px;
                cursor: pointer;
                font-size: 16px;
            }

            button:hover {
                background-color: #218838;
            }

            .error {
                color: red;
                font-size: 14px;
                margin-top: -15px;
                margin-bottom: 15px;
            }
        </style>
</head>
<body>
    <h2>Add Menu Item</h2>

    <form id="menuItemForm">
        <!-- Image Upload -->
        <label for="image">Image:</label>
        <input type="file" id="image" name="image" accept="image/*" required><br><br>

        <!-- Item Name -->
        <label for="name">Item Name:</label>
        <input type="text" id="name" name="name" required><br><br>

        <!-- Description -->
        <label for="description">Description:</label>
        <textarea id="description" name="description" required></textarea><br><br>

        <!-- Price -->
        <label for="price">Price:</label>
        <input type="number" id="price" name="price" required><br><br>

        <!-- Available -->
        <label for="available">Available:</label>
        <input type="checkbox" id="available" name="available"><br><br>

        <!-- IsVeg -->
        <label for="isVeg">Vegetarian:</label>
        <input type="checkbox" id="isVeg" name="isVeg"><br><br>

        <!-- Categories -->
        <label for="category">Category:</label>
        <select id="category" name="category" required>
            <option value="" disabled selected>Select a category</option>
            <!-- Categories will be dynamically populated -->
        </select><br><br>

        <button type="submit">Add Menu Item</button>
    </form>

    <script src="/static/js/add-menu.js"></script>
</body>
</html>