<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/restaurantowner/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>Add Menu Item</title>
    <link rel="stylesheet" href="/static/css/add-menu-item.css">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body>
<div class="form-container">
    <h2>Add Menu Item</h2>
    <form id="menuItemForm">
        <!-- Image Upload -->
        <label for="image">Image</label>
        <input type="file" id="image" name="image" accept="image/*" required><br><br>

        <!-- Item Name -->
        <label for="name">Item Name</label>
        <input type="text" id="name" name="name" required><br><br>

        <!-- Description -->
        <label for="description">Description</label>
        <textarea id="description" name="description" required></textarea><br><br>

        <!-- Price -->
        <div id="priceError" class="error"></div>
        <label for="price">Price</label>
        <input type="number" id="price" name="price" required><br><br>
        <div class="checkboxes">
        <!-- IsVeg -->
        <label for="isVeg">Vegetarian</label>
        <input type="checkbox" id="isVeg" name="isVeg"><br><br>

        </div>

        <!-- Categories -->
        <label for="category">Category</label>
        <select id="category" name="category" required>
            <option value="" disabled selected>Select a category</option>
            <!-- Categories will be dynamically populated -->
        </select><br><br>

        <button type="submit">Add Menu Item</button>
    </form>
</div>
    <script src="/static/js/add-menu.js"></script>
</body>
</html>