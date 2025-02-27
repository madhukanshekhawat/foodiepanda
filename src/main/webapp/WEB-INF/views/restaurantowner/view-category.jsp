<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/restaurantowner/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>View Categories</title>
    <link rel="stylesheet" href="/static/css/view-category.css">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body>
<div class="table-container">
    <h2>All Categories</h2>
    <div class="search-container">
        <input type="text" id="searchCategory" placeholder="Search categories...">
        <button onclick="window.location.href='/restaurant/add-category'" class="btn btn-primary">Create Category</button>
    </div>
    <div id="categoriesContainer">
        <!-- Dynamic table will be injected here -->
    </div>
</div>

<!-- Delete Modal Structure -->
<div id="deleteModal" class="modal">
    <div class="modal-content">
        <h4>Confirm Delete</h4>
        <p>Are you sure you want to delete this category?</p>
        <div class="modal-footer">
            <button id="confirmDelete" class="btn btn-danger">Delete</button>
            <button id="cancelDelete" class="btn btn-secondary">Cancel</button>
        </div>
    </div>
</div>

<!-- Edit Modal Structure -->
<div id="editModal" class="modal">
    <div class="modal-content">
        <h4>Edit Category</h4>
        <form id="editCategoryForm">
            <div>
                <label for="editCategoryName">Name:</label>
                <input type="text" id="editCategoryName" required>
            </div>
            <div>
                <label for="editCategoryDescription">Description:</label>
                <input type="text" id="editCategoryDescription" required>
            </div>
            <div class="modal-footer">
                <button type="button" id="updateCategory" class="btn btn-primary" disabled>Update Category</button>
                <button type="button" id="cancelEdit" class="btn btn-secondary">Cancel</button>
            </div>
        </form>
    </div>
</div>

<script src="/static/js/view-category.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>