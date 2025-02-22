<%@ include file="/WEB-INF/views/customer/navbar.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Customer Orders</title>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<style>
body {
    font-family: Arial, sans-serif;
    background-color: #f8f9fa;
    margin: 0;
    padding: 0;
}

.container {
    max-width: 800px;
    margin: 0 auto;
    padding: 20px;
    background-color: #ffffff;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
    border-radius: 8px;
}

h2 {
    text-align: center;
    color: #343a40;
}

.table {
    width: 100%;
    margin-top: 20px;
    border-collapse: collapse;
}

.table th, .table td {
    padding: 12px;
    text-align: left;
    border: 1px solid #dee2e6;
}

.table th {
    background-color: #A294F9;
    color: white;
}

.table tbody tr:nth-child(even) {
    background-color: #f2f2f2;
}

.text-center {
    text-align: center;
}

.text-muted {
    color: #6c757d;
}

.text-danger {
    color: #dc3545;
}

.mt-3 {
    margin-top: 1rem;
}

.mt-5 {
    margin-top: 3rem;
}
</style>
</head>
<body>
    <div class="container mt-5">
         <button id="proceedButton" onclick="goBack()">Back</button>
        <h2>Your Orders</h2>
        <div id="ordersContainer" class="mt-3"></div>
    </div>
<script src="/static/js/order-summary.js"></script>
    <script>
       function goBack() {
          window.history.back();
       }
    </script>
</body>
</html>