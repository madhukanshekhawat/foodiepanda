<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Register</title>
    <link rel="stylesheet" href="/static/css/registration.css">
</head>
<body>
<div class="main">
    <h1>Register to Our Website</h1>
    <div class="registerDiv">
        <form id="registerForm">
            <div id="errorMessages" style="color: red;"></div>

            <div id="firstNameError" style="color: red;"></div>
            <label for="firstName">First Name</label>
            <input type="text" id="firstName" name="firstName" required><br>

            <div id="lastNameError" style="color: red;"></div>
            <label for="lastName">Last Name</label>
            <input type="text" id="lastName" name="lastName" required><br>

            <div id="emailError" style="color: red;"></div>
            <label for="email">Email</label>
            <input type="email" id="email" name="email" required><br>

            <div id="phoneNumberError" style="color: red;"></div>
            <label for="phoneNumber">Phone Number</label>
            <input type="text" id="phoneNumber" name="phoneNumber" required><br>

            <div id="passwordError" style="color: red;"></div>
            <label for="password">Password</label>
            <input type="password" id="password" name="password" required><br>

            <div id="confirmPasswordError" style="color: red;"></div>
            <label for="confirmPassword">Confirm Password</label>
            <input type="password" id="confirmPassword" name="confirmPassword" required><br>

            <div id="roleError" style="color: red;"></div>
            <label for="role">Role</label>
            <input type="radio" id="restaurantOwner" name="role" value="RESTAURANT_OWNER" required> Restaurant Owner
            <input type="radio" id="customer" name="role" value="CUSTOMER" required> Customer<br>

            <button type="submit">Register</button>
        </form>
    </div>
    </div>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="/static/js/registration.js"></script>
</body>
</html>