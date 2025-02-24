<%@ include file="/WEB-INF/views/customer/navbar.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css">
    <title>Cart</title>
    <style>
       body {
           font-family: Arial, sans-serif;
           background-color: #f8f9fa;
           margin: 0;
           padding: 0;
       }

       .cart-container {
           max-width: 800px;
           margin: 50px auto;
           padding: 20px;
           background-color: #fff;
           border-radius: 8px;
           box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
       }

       h2, h3 {
           text-align: center;
           color: #A294F9;
       }

       #cartItems .cart-item {
           display: flex;
           justify-content: space-between;
           align-items: center;
           padding: 10px 0;
           border-bottom: 1px solid #ddd;
       }

       .cart-item-details h5 {
           margin: 0;
           font-size: 18px;
           color: #333;
       }

       .cart-item-controls p {
           text-align: start;
           margin: 5px 0;
           font-size: 16px;
           color: #555;
       }

       .cart-item-controls button {
           background-color: #A294F9;
           color: #fff;
           border: none;
           padding: 5px 10px;
           cursor: pointer;
           border-radius: 4px;
           margin-bottom: 10px;
       }

       .cart-item-controls button:hover {
           background-color: #CDC1FF;
       }

       #addNewAddress, #saveNewAddress, #proceedButton {
           display: block;
           width: 100%;
           padding: 10px;
           margin: 10px 0;
           background-color: #A294F9;
           color: #fff;
           border: none;
           cursor: pointer;
           border-radius: 4px;
           font-size: 16px;
       }

       #addNewAddress:hover, #saveNewAddress:hover, #proceedButton:hover {
           background-color: #CDC1FF;
       }

       #newAddressForm {
           margin: 20px 0;
           padding: 20px;
           background-color: #F7F7F7;
           border-radius: 8px;
       }

       #newAddressForm input[type="text"] {
           width: calc(100% - 20px);
           padding: 10px;
           margin: 10px 0;
           border: 1px solid #ccc;
           border-radius: 4px;
       }

       .address-labels div {
           margin: 10px 0;
       }

       .address-labels input[type="radio"] {
           margin-right: 10px;
       }

       #totalAmount {
           text-align: right;
           font-size: 18px;
           color: #333;
           font-weight: bold;
           margin-top: 20px;
       }

       p {
           color: grey;
           text-align: center;
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
      <div id="totalAmount"></div>
      <button id="proceedButton">Place Order</button>
      <button id="proceedButton" onclick="goBack()">Back</button>
  </div>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="/static/js/cart.js"></script>
    <script>
            function goBack() {
                window.history.back();
            }
        </script>
</body>
</html>