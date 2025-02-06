document.addEventListener('DOMContentLoaded', function() {
            // Simulate user login status
            var isLoggedIn = true; // Change this to false to simulate a logged-out user

            var authButtons = document.getElementById('authButtons');
            var logoutForm = document.getElementById('logoutForm');
            var orderSummaryLink = document.getElementById('orderSummaryLink');
            var showAddressLink = document.getElementById('showAddressLink');

            if (isLoggedIn) {
                authButtons.style.display = 'none';
                logoutForm.style.display = 'inline';
                orderSummaryLink.style.display = 'inline';
                showAddressLink.style.display= 'inline';
            } else {
                authButtons.style.display = 'flex';
                logoutForm.style.display = 'none';
                orderSummaryLink.style.display = 'none';
                showAddressLink.style.display= 'none';
            }

            // Handle logout without redirect
            logoutForm.addEventListener('submit', function(event) {
                event.preventDefault(); // Prevent form submission

                // Remove JWT token from local storage or cookies
                localStorage.removeItem('jwtToken'); // If stored in local storage
                document.cookie = 'jwtToken=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;'; // If stored in cookies

                // Optionally, inform the server to invalidate the token
                fetch('/logout', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                        'Authorization': 'Bearer ' + localStorage.getItem('jwtToken')
                    }
                }).then(response => {
                    if (response.ok) {
                        alert('You have been logged out successfully');
                        // Simulate logout
                        isLoggedIn = false;
                        authButtons.style.display = 'flex';
                        logoutForm.style.display = 'none';
                        orderSummaryLink.style.display = 'none';
                    } else {
                        alert('Logout failed');
                    }
                }).catch(error => {
                    console.error('Error:', error);
                    alert('An error occurred during logout');
                });
            });
        });