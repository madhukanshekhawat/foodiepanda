<!DOCTYPE html>
<html>
<head>
    <title>Add Menu Item</title>
    <style>
        body { font-family: Arial, sans-serif; }
        .container { width: 300px; margin: 0 auto; padding: 20px; border: 1px solid #ccc; border-radius: 5px; box-shadow: 0 0 10px rgba(0, 0, 0, 0.1); }
        .form-group { margin-bottom: 15px; }
        .form-group label { display: block; margin-bottom: 5px; }
        .form-group input, .form-group textarea { width: 100%; padding: 8px; box-sizing: border-box; }
        .form-group button { width: 100%; padding: 10px; background-color: #007bff; border: none; color: white; cursor: pointer; }
        .form-group button:hover { background-color: #0056b3; }
    </style>
</head>
<body>
    <div class="container">
        <h2>Add Menu Item</h2>
        <form id="menuItemForm">
            <div class="form-group">
                <label for="name">Name:</label>
                <input type="text" id="name" name="name" required>
            </div>
            <div class="form-group">
                <label for="description">Description:</label>
                <textarea id="description" name="description" required></textarea>
            </div>
            <div class="form-group">
                <label for="price">Price:</label>
                <input type="number" id="price" name="price" step="0.01" required>
            </div>
            <div class="form-group">
                <button type="submit">Add Menu Item</button>
            </div>
        </form>
    </div>
    <script src="script/js/menu-item-add.js"></script>
</body>
</html>
