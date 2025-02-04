<%@ include file="/WEB-INF/views/customer/navbar.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Cart</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css">
    <style>
        body {
            background-color: #F5EFFF;
            font-family: Arial, sans-serif;
            color: #333;
            margin: 0;
            padding: 0;
        }
        .cart-container {
            padding: 20px;
        }
        .cart-item {
            background-color: #EEE;
            border: 1px solid #E5D9F2;
            border-radius: 10px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
           px;
            padding: 20px;
            display: flex;
            align-items: center;
            justify-content: space-between;
        }
        .cart-item img {
            width: 100px;
            height: 100px;
            object-fit: cover;
            border-radius: 10px;
        }
        .cart-item-details {
            flex-grow: 1;
            margin-left: 20px;
        }
        .cart-item-details h5 {
            margin: 0;
            color: #A594F9;
        }
        .cart-item-details p {
            margin: 5px 0;
        }
    </style>
</head>
<body>
  <div class="cart-container">
      <h2>Your Cart</h2>
      <div id="cartItems"></div>
      <h3>Select Address</h3>
      <div id="addressContainer">
          <!-- Addresses will be populated here -->
      </div>
      <button id="addNewAddress">Add New Address</button>
      <div id="newAddressForm" style="display: none;">
          <label>Address Label:</label>
          <div>
              <input type="radio" name="addressLabel" value="HOME" id="label-home">
              <label for="label-home">HOME</label>
          </div>
          <div>
              <input type="radio" name="addressLabel" value="OFFICE" id="label-office">
              <label for="label-office">OFFICE</label>
          </div>
          <div>
              <input type="radio" name="addressLabel" value="OTHER" id="label-other">
              <label for="label-other">OTHER</label>
          </div>
          <input type="text" id="newAddressLine" placeholder="Address Line">
          <input type="text" id="newAddressCity" placeholder="City">
          <input type="text" id="newAddressPostalCode" placeholder="Postal Code">
          <input type="text" id="newAddressState" placeholder="State">
          <button id="saveNewAddress">Save Address</button>
      </div>
      <button id="proceedButton">Proceed</button>
  </div>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="/static/js/cart.js"></script>
</body>
</html>