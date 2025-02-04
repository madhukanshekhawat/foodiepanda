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
            margin-bottom: 20px;
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
       <select id="addressDropdown">
           <!-- Addresses will be populated here -->
       </select>
       <button id="addNewAddress">Add New Address</button>
       <div id="newAddressForm" style="display: none;">
           <input type="text" id="newAddress" placeholder="Enter new address">
           <button id="saveNewAddress">Save Address</button>
       </div>
       <button id="proceedButton">Proceed</button>
   </div>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="/static/js/cart.js"></script>
</body>
</html>