<%@ include file="/WEB-INF/views/customer/navbar.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>Restaurant Details</title>
    <style>
    /* General Styles */
    body {
        background-color: #F5EFFF;
        font-family: Arial, sans-serif;
        color: #333;
        margin: 0;
        padding: 0;
    }

    /* Container Styles */
    #menuItems {
        display: flex;
        flex-wrap: wrap;
        justify-content: center;
        gap: 20px;
        padding: 20px;
    }

    /* Card Styles */
    .menu-card {
        background-color: #EEE;
        border: 1px solid #E5D9F2;
        border-radius: 10px;
        box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        overflow: hidden;
        width: 350px;
        transition: transform 0.2s, box-shadow 0.2s;
    }

    .menu-card:hover {
        transform: scale(1.05);
        box-shadow: 0 8px 16px rgba(0, 0, 0, 0.2);
    }


    .menu-card img {
       width: 100%;
       max-width: 330px;
       height: 250px;
       object-fit: cover;
    }

    .menu-card h5 {
        color: #A594F9;
        margin: 0;
        padding: 10px;
        text-align: center;
    }

    .menu-card p {
        margin: 10px;
        padding: 0;
    }

    /* Restaurant Info Styles */
    .restaurant-info {
        background-color: #EEE;
        border: 1px solid #E5D9F2;
        border-radius: 10px;
        box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        margin: 20px auto;
        padding: 20px;
        width: 80%;
        animation: fadeIn 1s ease-in-out;
    }

    .restaurant-info h2 {
        color: #A594F9;
        margin: 0;
        padding: 10px;
        text-align: center;
    }

    .restaurant-info p {
        margin: 10px 0;
        padding: 0;
        color: #333;
    }

    #availability {
        color: green;
    }

    #restaurantAddress {
        text-transform: uppercase;
    }

    h3 {
        color: #A594F9;
        text-align: center;
        margin-top: 20px;
        font-size: 24px;
        text-transform: uppercase;
        letter-spacing: 1px;
    }

    /* Modal Styles */
    .modal {
        display: none;
        position: fixed;
        z-index: 1;
        left: 0;
        top: 0;
        width: 100%;
        height: 100%;
        overflow: auto;
        background-color: rgba(0, 0, 0, 0.4);
    }

    .modal-content {
        background-color: #FFF;
        margin: 15% auto;
        padding: 20px;
        border: 1px solid #888;
        width: 80%;
        max-width: 350px;
        border-radius: 10px;
        box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        animation: fadeIn 0.5s ease-in-out;
    }

    .modal-content h2{
       padding-top: 30px;
       color: #A594F9;
    }

    .close {
        color: #aaa;
        float: right;
        font-size: 28px;
        font-weight: bold;
    }

    .close:hover,
    .close:focus {
        color: black;
        text-decoration: none;
        cursor: pointer;
    }

    .quantity-controls {
        display: flex;
        align-items: center;
        gap: 10px;
        margin: 10px 0;
    }

    .quantity-controls button {
        background-color: #A594F9;
        border: none;
        color: #FFF;
        padding: 10px;
        cursor: pointer;
        border-radius: 5px;
    }

    .quantity-controls input {
        width: 50px;
        text-align: center;
    }

    /* Animation */
    @keyframes fadeIn {
        from {
            opacity: 0;
        }
        to {
            opacity: 1;
        }
    }

    /* Animation */
    @keyframes fadeIn {
        from {
            opacity: 0;
        }
        to {
            opacity: 1;
        }
    }
    </style>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body>
    <div class="restaurant-info">
        <h2 id="restaurantName"></h2>
        <p><strong>Address:</strong> <span id="restaurantAddress"></span></p>
        <p><strong>Availability:</strong> <span id="availability"></span></p>
        <p><strong>Start Time:</strong> <span id="startTime"></span></p>
        <p><strong>End Time:</strong> <span id="endTime"></span></p>
    </div>

    <h3>Menu Items</h3>
    <div id="menuItems"></div>

    <div id="menuItemModal" class="modal">
        <div class="modal-content">
            <span class="close">&times;</span>
            <img id="modalImage" src="" alt="" style="width: 100%; height: auto;"/>
            <h2 id="modalName"></h2>
            <p id="modalDescription"></p>
            <p id="modalPrice"></p>
            <div class="quantity-controls">
                <button id="decreaseQuantity">-</button>
                <input type="number" id="quantity" value="1" min="1"/>
                <button id="increaseQuantity">+</button>
            </div>
            <button class="addToCart">Add to Cart</button>
        </div>
    </div>
    <script src="/static/js/customer-dashboard.js"></script>
</body>
</html>

