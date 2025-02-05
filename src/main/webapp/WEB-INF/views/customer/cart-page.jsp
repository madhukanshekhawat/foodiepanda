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
            max-width: 800px;
            margin: 0 auto;
        }

        h2 {
            text-align: center;
            color: #A594F9;
            margin-bottom: 20px;
        }

        .cart-item {
            background-color: #EEE;
            border: 1px solid #E5D9F2;
            border-radius: 10px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            padding: 20px;
            display: flex;
            align-items: center;
            justify-content: space-between;
            margin-bottom: 15px;
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
            display: flex;
            align-items: center;
            justify-content: space-between;
        }

        .cart-item-details h5 {
            margin: 0;
            color: #A594F9;
            flex: 1;
        }

        .cart-item-controls {
            display: flex;
            align-items: center;
        }

        .cart-item-controls p {
            margin: 0 10px;
        }

        .cart-item-controls button {
            margin-left: 10px;
        }
        button {
            background-color: #A594F9;
            color: white;
            border: none;
            border-radius: 5px;
            padding: 10px 15px;
            cursor: pointer;
            transition: background-color 0.3s;
        }

        button:hover {
            background-color: #8A74D9;
        }

        #addNewAddress, #proceedButton {
            display: block;
            width: 100%;
            margin: 10px 0;
        }

        #newAddressForm {
            background-color: #EEE;
            border: 1px solid #E5D9F2;
            border-radius: 10px;
            padding: 20px;
            margin-top: 20px;
        }

        #newAddressForm label {
            display: block;
            margin-bottom: 5px;
            color: #A594F9;
        }

        #newAddressForm input[type="text"] {
            width: calc(100% - 20px);
            padding: 10px;
            margin-bottom: 10px;
            border: 1px solid #E5D9F2;
            border-radius: 5px;
        }

        #newAddressForm input[type="radio"] {
            margin-right: 5px;
        }

        #addressContainer h3 {
            color: #A594F9;
            margin-bottom: 10px;
        }

        #addressContainer div {
            margin-bottom: 10px;
        }

        .address-labels {
            display: flex;
            align-items: center;
            gap: 10px; /* Adjust the gap between labels as needed */
        }

        .address-labels div {
            display: flex;
            align-items: center;
        }

        .address-labels input[type="radio"] {
            margin-right: 5px;
        }
    </style>
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
      <button id="proceedButton">Proceed</button>
  </div>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="/static/js/cart.js"></script>
</body>
</html>