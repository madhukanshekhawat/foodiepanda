
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!doctype html>
<html lang="en">
  <head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Foodie Panda</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/form.css">
   </head>
  <body>

    <section class="vh-100 bg-image">

      <div class="mask d-flex align-items-center h-100 gradient-custom-3">
        <div class="container h-100">
          <div class="row d-flex justify-content-center align-items-center h-100">
            <div class="col-12 col-md-9 col-lg-7 col-xl-6">
              <div class="card mt-3 mb-3" style="border-radius: 20px;">
                <div class="card-body p-5 ">
                  <h2 class="text-uppercase text-center mb-5">Login here</h2>

                  <form method="post" id="loginForm">
                    <div data-mdb-input-init class="form-outline mb-4">
                      <label class="form-label" for="form3Example3cg">Your Email</label>
                      <input type="email" id="username" name="username" required class="form-control form-control-lg" placeholder="Enter email id here" />
                    </div>

                    <div data-mdb-input-init class="form-outline mb-4">
                      <label class="form-label" for="form3Example4cg">Password</label>
                      <input type="password" id="password" name="password" required class="form-control form-control-lg" placeholder="Enter password here"/>
                    </div>

                      <c:if test="${param.error != null}">
                        <div class="text-center alert alert-danger "> Invalid username or password.. </div>
                      </c:if>
                      <c:if test="${param.logout != null}">
                        <div id="logoutMessage" class="text-center alert alert-success"> You have been logged out.. </div>
                      </c:if>
                      <div id="errorMessage" class="text-center alert alert-danger" style="display: none;"></div>

                    <div class="d-flex justify-content-center">
                      <button  type="submit" data-mdb-button-init
                        data-mdb-ripple-init class="btn btn-success btn-block btn-lg gradient-custom-4 text-body">Login</button>
                    </div>
                    <p class="text-right text-muted mt-2 mb-0"><a href="/forgot-password"
                                            class="fw-bold text-body"><u>Forgot Password?</u></a></p>

                    <p class="text-center text-muted mt-3 mb-0">Create an account? <a href="/api/registration"
                        class="fw-bold text-body"><u>SignUp Here</u></a></p>

                  </form>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </section>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/login.js"></script>
  </body>
</html>
