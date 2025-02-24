$(document).ready(function() {
  var userRole = null;

  function checkUserRole() {
    $.ajax({
      url: '/api/authenticated-user',
      type: 'GET',
      success: function(user) {
        userRole = user.role;
        redirectToDashboard(user);
      },
      error: function(xhr) {
        $('#errorMessage').text("Bad Credentials");
        $('#errorMessage').fadeIn(3000);
        $('#errorMessage').fadeOut(5000);
      }
    });
  }

  function redirectToDashboard(user) {
    if (user.role === 'ADMIN') {
      if (window.location.pathname !== '/api/admin/dashboard') {
        window.location.href = '/api/admin/dashboard';
      }
    } else if (user.role === 'RESTAURANT_OWNER') {
      if (user.approved) {
        if (window.location.pathname !== '/restaurant/dashboard') {
          window.location.href = '/restaurant/dashboard';
        }
      } else {
        if (window.location.pathname !== '/api/approval-pending') {
          window.location.href = '/api/approval-pending';
        }
      }
    } else if (user.role === 'CUSTOMER') {
      if (window.location.pathname !== '/api/customer/dashboard') {
        window.location.href = '/api/customer/dashboard';
      }
    } else {
      if (window.location.pathname !== '/api/user-login') {
        window.location.href = '/api/user-login';
      }
    }
  }

  $('#loginForm').submit(function(event) {
    event.preventDefault();
    var username = $('#username').val();
    var password = $('#password').val();

    $.ajax({
      url: '/auth/login',
      type: 'POST',
      contentType: 'application/json',
      data: JSON.stringify({username: username, password: password}),
      success: function(response) {
        var token = response.token;
        document.cookie = "JWT-TOKEN=" + token + "; path=/;";
        checkUserRole();
      },
      error: function(xhr) {
        $('#errorMessage').text("Bad Credentials").show();
        setTimeout(function() {
          $('#errorMessage').fadeOut('slow', function() {
            $('#errorMessage').hide();
          });
        }, 7000);
      }
    });
  });

  checkUserRole();

  window.addEventListener('popstate', function(event) {
    if (userRole) {
      redirectToDashboard({ role: userRole });
    } else {
      checkUserRole();
    }
  });
  setTimeout(function() {
    var message = document.getElementById("logoutMessage");
    if (message) {
      message.style.display = "none";
    }
  }, 5000);
});