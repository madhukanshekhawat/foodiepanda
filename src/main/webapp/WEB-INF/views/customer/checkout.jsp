<%@ include file="/WEB-INF/views/customer/navbar.jsp" %>
<html>
<head>
    <title>Order Status</title>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f8f9fa;
            margin: 0;
            padding: 0;
        }
        .order-container {
            max-width: 600px;
            margin: 50px auto;
            padding: 20px;
            background-color: #fff;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }
        .order-header {
            text-align: center;
            color: #333;
        }
        .order-status {
            font-size: 18px;
            color: #555;
            text-align: center;
        }
        .order-status h4 {
            color: #28a745;
        }
        .order-status p {
            margin: 10px 0;
        }
        .order-status strong {
            color: #333;
        }
    </style>
</head>
<body>
    <div class="order-container">
        <div id="orderStatus" class="order-status">
            <p>Loading your order status...</p>
        </div>
         <div id="order-data" class="order-status">
           <p>Loading your data...</p>
         </div>
         <button class="generate-invoice-btn" style="display: none;">Generate Invoice</button>
         <button id="proceedButton" onclick="goBack()">Back</button>
    </div>
    <script src="/static/js/orderStatus.js"></script>
    <script>
       function goBack() {
          window.history.back();
       }
    </script>
</body>
</html>
