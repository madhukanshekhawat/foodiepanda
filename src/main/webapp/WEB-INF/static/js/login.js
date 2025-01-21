document.addEventListener("DOMContentLoaded", function() {
    const loginForm = document.querySelector("#loginForm");
    loginForm.addEventListener("submit", function(event) {
        event.preventDefault();

        const formData = new FormData(loginForm);
        const userEmail = formData.get("username");
        const userPassword = formData.get("password");

        fetch("/authenticate", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({ email: userEmail, password: userPassword })
        })
        .then(response => response.json())
        .then(data => {
            localStorage.setItem("jwtToken", data.jwt); // Store the token
            alert("Login successful!");

            // Redirect based on user role
            switch (data.role) {
                case 'ADMIN':
                    window.location.href = "/api/admin/dashboard";
                    break;
                case 'RESTAURANT_OWNER':
                    window.location.href = "/api/restaurant/dashboard";
                    break;
                case 'CUSTOMER':
                    window.location.href = "/customer/dashboard";
                    break;
                default:
                    window.location.href = "/";
                    break;
            }
        })
        .catch(error => {
            console.error("Error:", error);
            alert("Login failed.");
        });
    });
});