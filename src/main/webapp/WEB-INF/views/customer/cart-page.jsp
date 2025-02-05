<%@ include file="/WEB-INF/views/customer/navbar.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Cart</title>
</head>
<body>
  <div class="cart-container">
      <h2>Your Cart</h2>
      <div id="cartItems"></div>
      <div id="addressContainer">
          <!-- Addresses will be populated here -->
      </div>
      <button id="addNewAddress">Add New Address</button>
      <div id="newAddressForm" style="display: none;">
          <div class="address-labels">
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
          </div>
          <input type="text" id="newAddressLine" placeholder="Address Line">
          <input type="text" id="newAddressCity" placeholder="City">
          <input type="text" id="newAddressPostalCode" placeholder="Postal Code">
          <input type="text" id="newAddressState" placeholder="State">
          <button id="saveNewAddress">Save Address</button>
      </div>
      <div id="totalAmount" style="font-weight: bold; margin-top: 20px;"></div>
      <button id="proceedButton" onclick ="proceedToCheckout()">Place Order</button>
  </div>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="/static/js/cart.js"></script>
</body>
</html>