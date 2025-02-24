<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/restaurantowner/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>Add Category</title>
    <link rel="stylesheet" href="/static/css/add-category.css">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body>
<div class="main-content">
<button id="proceedButton" onclick="goBack()">Back</button>
    <h2>Add Category</h2>
    <form id="addCategoryForm">
        <div id="nameError" class="error"></div>
        <label for="name">Category Name:</label>
        <input type="text" id="name" name="name" required><br><br>

        <div id="descriptionError" class="error"></div>
        <label for="description">Description:</label>
        <textarea id="description" name="description" required></textarea><br><br>

        <button type="button" onclick="addCategory()">Add Category</button>
    </form>

    <p id="responseMessage"></p>
    </div>

    <script>
    function showError(elementId, message) {
        $("#" + elementId).text(message);
    }

    function clearError(elementId) {
        $("#" + elementId).text("");
    }
        function addCategory() {
            const name = $('#name').val();
            const description = $('#description').val();
             let isValid = true;

                if (!name) {
                    showError("nameError", "Name is required.");
                    isValid = false;
                }

                if (!description) {
                    showError("descriptionError", "Description is required.");
                    isValid = false;
                }
                 if (!isValid) {
                        return;
                 }

                  $("input, select").on("input change", function() {
                         clearError($(this).attr("id") + "Error");
                     });

            // Retrieve the JWT token from cookies
            const token = document.cookie.split('; ').find(row => row.startsWith('JWT-TOKEN=')).split('=')[1];

            $.ajax({
                url: '/categories/add',
                type: 'POST',
                contentType: 'application/json',
                data: JSON.stringify({
                    name: name,
                    description: description
                }),
                headers: {
                    'Authorization': 'Bearer ' + token
                },
                success: function(response) {
                    $('#responseMessage').text('Category added successfully!');
                    $('#responseMessage').css('color', 'green');
                    $('#addCategoryForm')[0].reset();
                },
                error: function(xhr) {
                    $('#responseMessage').text('Error: ' + xhr.responseText);
                    $('#responseMessage').css('color', 'red');
                }
            });
        }
        function goBack() {
            window.history.back();
        }
    </script>
</body>
</html>