<%@ include file="/WEB-INF/views/customer/navbar.jsp" %>
<html>
<head>
    <title>Manage Addresses</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body>
    <div class="container mt-4">
        <button id="proceedButton" onclick="goBack()">Back</button>
        <h2 class="text-center">Manage Your Addresses</h2>

        <!-- Address List -->
        <div id="addressContainer">
            <p id="noAddressesMessage" class="text-center text-danger"></p>
        </div>

        <button id="addAddressButton" class="btn btn-primary mt-4" onclick="openModal(false)">Add New Address</button>

        <!-- Combined Address Modal -->
        <div class="modal" id="addressModal" tabindex="-1">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="modalTitle">Manage Address</h5>
                        <button type="button" class="btn-close" onclick="closeModal()"></button>
                    </div>
                    <div class="modal-body">
                        <form id="addressForm">
                            <input type="hidden" id="addressId">
                            <div class="mb-2">
                                <label for="label-AddressLine">Address Line</label>
                                <input type="text" class="form-control" id="street" placeholder="Address Line" required>
                            </div>
                            <div class="mb-2">
                                <label for="label-city">City</label>
                                <input type="text" class="form-control" id="city" placeholder="City" required>
                            </div>
                            <div class="mb-2">
                                <label for="label-state">State</label>
                                <input type="text" class="form-control" id="state" placeholder="State" required>
                            </div>
                            <div class="mb-2">
                                <label for="label-postalCode">Postal Code</label>
                                <input type="text" class="form-control" id="zipCode" placeholder="Postal Code" required>
                            </div>
                            <div class="mb-3">
                                <label><b>Address Type:</b></label><br>
                                <input type="radio" name="addressType" value="HOME" checked> Home
                                <input type="radio" name="addressType" value="OFFICE"> Office
                                <input type="radio" name="addressType" value="OTHER"> Other
                            </div>
                            <button type="submit" class="btn btn-primary" id="modalButton">Save Address</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
      <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
      <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js"></script>
      <script src="/static/js/show-address.js"></script>
      <script>
         function goBack() {
            window.history.back();
         }
      </script>
</body>
</html>

