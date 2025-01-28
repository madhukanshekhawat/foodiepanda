$(document).ready(function () {
  // Populate the time slots for the dropdowns
  populateTimeSlots('#startTime');
  populateTimeSlots('#endTime');

  // Fetch current availability times and populate the fields
  $.ajax({
    url: '/api/restaurant/profile', // Your endpoint to get current availability
    method: 'GET',
    success: function (data) {
      // Assuming data contains `availabilityStartTime` and `availabilityEndTime`
      $('#currentStartTime').val(convertTo12HourFormat(data.availabilityStartTime));
      $('#currentEndTime').val(convertTo12HourFormat(data.availabilityEndTime));
    },
    error: function () {
      showMessage("Error fetching current availability times.", "red");
    }
  });
});

function populateTimeSlots(selector) {
  const select = $(selector);
  const times = [
    "12:00 AM", "01:00 AM", "02:00 AM", "03:00 AM", "04:00 AM", "05:00 AM", "06:00 AM",
    "07:00 AM", "08:00 AM", "09:00 AM", "10:00 AM", "11:00 AM", "12:00 PM", "01:00 PM",
    "02:00 PM", "03:00 PM", "04:00 PM", "05:00 PM", "06:00 PM", "07:00 PM", "08:00 PM",
    "09:00 PM", "10:00 PM", "11:00 PM"
  ];

  select.append($('<option></option>').attr('value', '').text('Please select a time'));
  times.forEach(time => {
    select.append($('<option></option>').attr('value', time).text(time));
  });
}

function changeAvailability() {
  let availabilityStartTime = $('#startTime').val();
  let availabilityEndTime = $('#endTime').val();

  if (!availabilityStartTime || !availabilityEndTime) {
    showMessage("Please fill out all the fields.", "red");
    return;
  }

  let startTime = convertTo24HourFormat(availabilityStartTime.trim());
  let endTime = convertTo24HourFormat(availabilityEndTime.trim());

  let startTimeMoment = moment(startTime, "HH:mm");
  let endTimeMoment = moment(endTime, "HH:mm");

  if (startTimeMoment >= endTimeMoment) {
    showMessage("Store opening time must be before closing time.", "red");
    return;
  }

  $.ajax({
    url: '/api/restaurant/change-availability',
    method: 'PUT',
    contentType: 'application/json',
    data: JSON.stringify({
      availabilityStartTime: startTime,
      availabilityEndTime: endTime,
    }),
    success: function (response) {
      $('#changeAvailabilityForm')[0].reset();
      showMessage("Availability updated successfully.", "green");
    },
    error: function (error) {
      showMessage("Error updating availability.", "red");
    }
  });
}

function showMessage(message, color) {
  $('#message').text(message).css('color', color).show();
  setTimeout(function () {
    $('#message').fadeOut('slow', function () {
      $(this).text('').css('color', '').show();
    });
  }, 5000);
}

function convertTo24HourFormat(time12h) {
  const [time, modifier] = time12h.split(' ');
  let [hours, minutes] = time.split(':');
  if (hours === '12') hours = '00';
  if (modifier === 'PM') hours = parseInt(hours, 10) + 12;
  return `${hours}:${minutes}`;
}

function convertTo12HourFormat(time24h) {
  const [hours, minutes] = time24h.split(':');
  const modifier = hours >= 12 ? 'PM' : 'AM';
  const hour = hours % 12 || 12; // Convert 24-hour to 12-hour format
  return `${hour}:${minutes} ${modifier}`;
}
