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

       <button id="addAddressButton" class="btn btn-primary mt-4">Add New Address</button>

        <!-- Add Address Modal -->
        <div class="modal" id="addAddressModal" tabindex="-1">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title">Add New Address</h5>
                        <button type="button" class="btn-close" onclick="closeAddModal()"></button>
                    </div>
                    <div class="modal-body">
                        <form id="addAddressFormModal">
                            <div class="mb-2">
                                <input type="text" class="form-control" id="modalStreet" placeholder="Street" required>
                            </div>
                            <div class="mb-2">
                                <input type="text" class="form-control" id="modalCity" placeholder="City" required>
                            </div>
                            <div class="mb-2">
                                <input type="text" class="form-control" id="modalState" placeholder="State" required>
                            </div>
                            <div class="mb-2">
                                <input type="text" class="form-control" id="modalZipCode" placeholder="Zip Code" required>
                            </div>
                            <div class="mb-3">
                                <label><b>Address Type:</b></label><br>
                                <input type="radio" name="modalAddressType" value="HOME" checked> Home
                                <input type="radio" name="modalAddressType" value="OFFICE"> Office
                                <input type="radio" name="modalAddressType" value="OTHER"> Other
                            </div>
                            <button type="submit" class="btn btn-primary">Add Address</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>

    <!-- Edit Address Modal -->
    <div class="modal" id="editAddressModal" tabindex="-1">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title">Edit Address</h5>
                    <button type="button" class="btn-close" onclick="closeModal()"></button>
                </div>
                <div class="modal-body">
                    <form id="editAddressForm">
                        <input type="hidden" id="editAddressId">
                        <div class="mb-2">
                            <input type="text" class="form-control" id="editStreet" placeholder="Address Line" required>
                        </div>
                        <div class="mb-2">
                            <input type="text" class="form-control" id="editCity" placeholder="City" required>
                        </div>
                        <div class="mb-2">
                            <input type="text" class="form-control" id="editState" placeholder= "State" required>
                        </div>
                        <div class="mb-2">
                            <input type="text" class="form-control" id="editZipCode" placeholder ="PostalCode" required>
                        </div>
                        <div class="mb-3">
                            <label><b>Address Type:</b></label><br>
                            <input type="radio" name="editAddressType" value="HOME"> Home
                            <input type="radio" name="editAddressType" value="OFFICE"> Office
                            <input type="radio" name="editAddressType" value="OTHER"> Other
                        </div>
                        <button type="submit" class="btn btn-success">Update Address</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
  <script src = "/static/js/show-address.js"></script>
      <script>
         function goBack() {
            window.history.back();
         }
      </script>
</body>
</html>

