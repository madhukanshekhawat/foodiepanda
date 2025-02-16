//document.addEventListener('DOMContentLoaded', function() {
//    // Function to get a cookie by name
//    function getCookie(name) {
//        let cookieArr = document.cookie.split(";");
//        for (let i = 0; i < cookieArr.length; i++) {
//            let cookiePair = cookieArr[i].split("=");
//            if (name === cookiePair[0].trim()) {
//                return decodeURIComponent(cookiePair[1]);
//            }
//        }
//        return null;
//    }
//
//    // Check user login status from local storage or cookies
//    var isLoggedIn = localStorage.getItem('isLoggedIn') === 'true' || getCookie('jwtToken') !== null;
//
//    var authButtons = document.getElementById('authButtons');
//    var logoutForm = document.getElementById('logoutForm');
//    var orderSummaryLink = document.getId('showAddressLink');
//
//    function updateUI() {
//        if (isLoggedIn) {
//            authButtons.style.display = 'none';
//            logoutForm.style.display = 'inline';
//            orderSummaryLink.style.display = 'inline';
//            showAddressLink.style.display = 'inline';
//        } else {
//            authButtons.style.display = 'flex';
//            logoutForm.style.display = 'none';
//            orderSummaryLink.style.display = 'none';
//            showAddressLink.style.display = 'none';
//        }
//    }
//
//    updateUI();
//
//    // Handle logout without redirect
//    logoutForm.addEventListener('submit', function(event) {
//        event.preventDefault(); // Prevent form submission
//
//        // Remove JWT token from local storage or cookies
//        localStorage.removeItem('jwtToken'); // If stored in local storage
//        document.cookie = 'jwtToken=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;'; // If stored in cookies
//
//        // Optionally, inform the server to invalidate the token
//        fetch('/logout', {
//            method: 'POST',
//            headers: {
//                'Content-Type': 'application/json',
//                'Authorization': 'Bearer ' + localStorage.getItem('jwtToken')
//            }
//        }).then(response => {
//            if (response.ok) {
//                alert('You have been logged out successfully');
//                // Simulate logout
//                isLoggedIn = false;
//                localStorage.setItem('isLoggedIn', 'false');
//                updateUI();
//            } else {
//                alert('Logout failed');
//            }
//        }).catch(error => {
//            console.error('Error:', error);
//            alert('An error occurred during logout');
//        });
//    });
//
//    // Simulate login for demonstration purposes
//    document.getElementById('loginButton').addEventListener('click', function() {
//        isLoggedIn = true;
//        localStorage.setItem('isLoggedIn', 'true');
//        updateUI();
//    });
//});