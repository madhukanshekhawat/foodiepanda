<%@ include file="/WEB-INF/views/customer/navbar.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Customer Profile</title>
    <link rel="stylesheet" type="text/css" href="/static/css/profile.css">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="/static/js/profile.js"></script>
</head>
<body>
    <div class="main-div">
    <h1>Customer Profile</h1>
    <div id="profile">
        <p class="email ">Email Id: <span id="email"></span></p>
        <p>First Name: <span id="firstName"></span></p>
        <p>Last Name: <span id="lastName"></span></p>
        <p>Phone Number: <span id="phoneNumber"></span></p>
        <button id="editProfileBtn">Edit Profile</button>
    </div>
    <div id="editProfilePopup" class="popup">
        <div class="popup-content">
            <h2>Edit Profile</h2>
            <label for="editFirstName">First Name:</label>
            <input type="text" id="editFirstName"><br>
            <label for="editLastName">Last Name:</label>
            <input type="text" id="editLastName"><br>
            <label for="editPhoneNumber">Phone Number:</label>
            <input type="text" id="editPhoneNumber"><br>
            <button id="updateProfileBtn" disabled>Update Profile</button>
            <button id="closePopupBtn">Close</button>
        </div>
    </div>
    </div>
    <script>
                $(document).ready(function() {
                    // Hide the search input and button
                    $("#searchInput").hide();
                    $("#nav-searchBtn").hide();
                });
    </script>
</body>
</html>