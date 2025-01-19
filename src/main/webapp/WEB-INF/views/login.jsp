
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Restaurant Owner Login</title>
    <link rel="stylesheet" href="/css/styles.css">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

    <script>
        $(document).ready(function() {
            $("#loginForm").submit(function(event) {
                event.preventDefault(); // Prevent form submission
                loginRestaurantOwner();
            });
        });

        function loginRestaurantOwner() {
            let loginData = {
                email: $("#email").val(),
                password: $("#password").val(),
            };

            $.ajax({
                url: "/user/login", // Your login endpoint here
                method: "POST",
                contentType: "application/json",
                data: JSON.stringify(loginData),
                success: function(response) {
                    // On success, redirect to the owner dashboard or another page
                    window.location.href = "/restaurant/addMenu"; // Redirect to dashboard
                },
                error: function() {
                    alert("Invalid credentials, please try again.");
                }
            });
        }
    </script>
</head>
<body>

    <div class="login-container">
        <h2>Restaurant Owner Login</h2>
        <form id="loginForm">
            <div class="form-group">
                <label for="email">Email:</label>
                <input type="email" id="email" name="email" required>
            </div>

            <div class="form-group">
                <label for="password">Password:</label>
                <input type="password" id="password" name="password" required>
            </div>

            <button type="submit" class="btn">Login</button>
        </form>
    </div>

</body>
</html>

