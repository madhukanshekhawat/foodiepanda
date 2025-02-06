document.addEventListener('DOMContentLoaded', function() {
    const form = document.getElementById('addCategoryForm');
    const messageDiv = document.getElementById('message');

    form.addEventListener('submit', function(event) {
        event.preventDefault();
        const categoryName = document.getElementById('categoryName').value;
        const description = document.getElementById('description').value;

        // Retrieve the JWT token from cookies
        const token = document.cookie.split('; ').find(row => row.startsWith('JWT-TOKEN=')).split('=')[1];

        fetch('/categories/add', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + token
            },
            body: JSON.stringify({
                name: categoryName,
                description: description
            })
        })
        .then(response => response.json())
        .then(data => {
            messageDiv.textContent = 'Category added successfully!';
            messageDiv.style.color = 'green';
        })
        .catch(error => {
            messageDiv.textContent = 'Error adding category: ' + error.message;
            messageDiv.style.color = 'red';
        });
    });
});