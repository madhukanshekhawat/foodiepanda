$(document).ready(function() {
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
        document.cookie = "JWT-TOKEN=" + token + ";  path=/;"
        // Redirect based on the user's role
        $.ajax({
          url: '/api/authenticated-user',
          type: 'GET',
          success: function(user) {
            if (user.role === 'ADMIN') {
              window.location.href = '/api/admin/dashboard';
            } else if (user.role === 'RESTAURANT_OWNER') {
              if (user.approved) {
                window.location.href = '/api/restaurant/dashboard';
                } else {
                    window.location.href = '/api/approval-pending';
                }
            } else if (user.role === 'CUSTOMER') {
              window.location.href = '/api/customer/dashboard';
            }
          },
          error: function(xhr) {
            $('#errorMessage').text("Bad Credentials");
            $('#errorMessage').fadeIn(3000);
            $('#errorMessage').fadeOut(5000);
          }
        });
      },
      error: function(xhr) {
        $('#errorMessage').text("Bad Credentials").show();
        //Hide the message after 3 second
        setTimeout(function(){
          $('#errorMessage').fadeOut('slow',function(){
            $('#errorMessage').hide();
          });
        },7000);
      }
    });
  });

  setTimeout(function() {
      var message = document.getElementById("logoutMessage");
      if (message) {
          message.style.display = "none";
      }
  }, 5000);
});