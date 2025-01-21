document.addEventListener("DOMContentLoaded", function() {
    const form = document.querySelector("#menuItemForm");
    if (form) {
        form.addEventListener("submit", function(event) {
            event.preventDefault();

            const formData = new FormData(form);
            const data = {
                name: formData.get("name"),
                description: formData.get("description"),
                price: formData.get("price")
            };

            const jwtToken = localStorage.getItem("jwtToken"); // Retrieve the token

            fetch("/menu/add", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                    "Authorization": "Bearer " + jwtToken // Include the token in the header
                },
                body: JSON.stringify(data)
            })
            .then(response => response.json())
            .then(data => {
                alert("Menu item added successfully!");
            })
            .catch(error => {
                console.error("Error:", error);
                alert("Failed to add menu item.");
            });
        });
    } else {
        console.error("Form element not found");
    }
});