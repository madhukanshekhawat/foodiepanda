<!DOCTYPE html>
<html>
<head>
    <title>Add Category</title>
</head>
<body>
   <form id="addCategoryForm">
       <input type="text" id="name" placeholder="Category Name" required>
       <textarea id="description" placeholder="Category Description" required></textarea>
       <button type="submit">Add Category</button>
   </form>
    <script>
        document.getElementById('addCategoryForm').addEventListener('submit', function(e) {
            e.preventDefault();
            const name = document.getElementById('name').value;
            const description = document.getElementById('description').value;

            fetch('/categories/add', {
                method: 'POST',
                headers: {
                    'Authorization': localStorage.getItem('jwt'), // JWT token from localStorage
                    'Content-Type': 'application/x-www-form-urlencoded', // Send data as URL-encoded form
                },
                body: new URLSearchParams({
                    name: name,
                    description: description,
                }),
            })
            .then(response => response.json())  // Expecting a JSON response
            .then(data => alert(data.message || data))  // Alert success message or error
            .catch(error => console.error('Error:', error));
        });
    </script>
</body>
</html>
