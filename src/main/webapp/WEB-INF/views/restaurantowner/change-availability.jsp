<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/restaurantowner/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>Change Availability</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/change_availability.css">
</head>
<body>
  <div class="container">
    <h1 class="text-center">Change Availability</h1>
       <div class="card mr-5 ml-5">
      <div class="card-body">
        <div class="container-fluid mt-3 mr-3">
          <div class="row">
            <div class="col-md-8 offset-md-2">
             <label for="currentStartTime">Current Opening Time:</label>
                 <input type="text" id="currentStartTime" readonly>

                <label for="currentEndTime">Current Closing Time:</label>
                <input type="text" id="currentEndTime" readonly>
              <form id="changeAvailabilityForm">
                <!--Availability Start Time-->
                <label class="form-label" for="startTime">Restaurant Opening Time<span>*</span></label>
                <div class="input-group mb-3">
                  <select id="startTime" name="startTime" class="form-control"></select>
                </div>
                <div class="error" id="startTimeError"></div>

                <!--Availability End Time-->
                <label class="form-label" for="endTime">Restaurant Closing Time<span>*</span></label>
                <div class="input-group mb-3">
                  <select id="endTime" name="endTime" class="form-control"></select>
                </div>
                <div class="error" id="endTimeError"></div>

                <div id="message" class="text-center mb-2"></div>

                <div class="text-center my-2">
                  <button type="button" onclick="changeAvailability()" class="btn btn-success mr-3">Update</button>
                </div>
              </form>
            </div>
          </div>
        </div>
      </div>
    </div>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.29.1/moment.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/change_availability.js"></script>
  </div>
</body>
</html>
