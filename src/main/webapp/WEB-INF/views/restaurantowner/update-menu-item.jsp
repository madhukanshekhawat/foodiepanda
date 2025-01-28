<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Update Menu Item</title>
    <link rel="stylesheet" href="/static/css/update-menu-item.css">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body>
    <div class="container">
        <h1>Update Menu Item</h1>
        <form id="updateMenuItemForm">
            <input type="hidden" id="menuItemId">
            <div>
                <label for="name">Name:</label>
                <input type="text" id="name" name="name">
            </div>
            <div>
                <label for="description">Description:</label>
                <input type="text" id="description" name="description">
            </div>
            <div>
                <label for="price">Price (RS):</label>
                <input type="number" id="price" name="price">
            </div>
            <div>
                <label for="category">Category:</label>
                <input type="text" id="category" name="category">
            </div>
            <div>
                <label for="image">Image:</label>
                <input type="text" id="image" name="image">
            </div>
            <div>
                <label for="available">Availability:</label>
                <select id="available" name="available">
                    <option value="true">Available</option>
                    <option value="false">Unavailable</option>
                </select>
            </div>
            <div>
                <label for="veg">Veg:</label>
                <select id="veg" name="veg">
                    <option value="true">Yes</option>
                    <option value="false">No</option>
                </select>
            </div>
            <button type="button" onclick="updateMenuItem()">Update Menu Item</button>
        </form>
    </div>
    <script src="/static/js/update-menu-item.js"></script>
</body>
</html>