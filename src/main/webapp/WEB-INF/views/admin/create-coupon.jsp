<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/admin/base.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Create Coupon</title>
    <style>
            body {
                background-color: #F5EFFF;
                height:100vh;
                font-family: Arial, sans-serif;
            }
            .main {
                display: flex;
                justify-content: center;
                align-items: center;
                height: 100vh;
                margin-top: 50px;
            }
            .couponDiv {
                background-color: white;
                padding: 20px 30px;
                border-radius: 8px;
                box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
                width: 90%;
                max-width: 500px;
            }
            h1 {
                text-align: center;
                color: #4A3F8C;
            }
            label {
                display: block;
                margin-top: 10px;
                color: #4A3F8C;
            }
            input{
                width: 98%;
                padding: 10px;
                margin-top: 5px;
                border: 1px solid #CDC1FF;
                border-radius: 4px;
                background-color: #fff;
            }

            select {
                width: 510px;
                padding: 10px;
                margin-top: 5px;
                border: 1px solid #CDC1FF;
                border-radius: 4px;
                background-color: #fff;
            }
            .create {
                width: 510px;
                padding: 10px;
                margin-top: 20px;
                background-color: #A294F9;
                color: white;
                border: none;
                border-radius: 4px;
                cursor: pointer;
                transition: background-color 0.3s ease;
            }
            .create:hover {
                background-color: #6B5DFB;
            }
            .error {
                color: red;
                font-size: 12px;
            }
        </style>
</head>
<body>
<div class="main">
    <div class="couponDiv">
        <h1>Create Coupon</h1>
        <form id="couponForm">
            <div id="restaurantIdError" class="error"></div>
            <label for="restaurantId">Restaurant</label>
            <select id="restaurantId" name="restaurantId" required>
                <option value=" ">Select a restaurant</option>
            </select><br>

            <label for="code">Coupon Code <div id="codeError" class="error"></div> </label>
            <input type="text" id="code" name="code" maxlength="10" required><br>

            <label for="discountPercentage">Discount Percentage <div id="discountPercentageError" class="error"></div></label>
            <input type="number" id="discountPercentage" name="discountPercentage" min="0" required><br>

            <label for="validFrom">Valid From <div id="validFromError" class="error"></div></label>
            <input type="date" id="validFrom" name="validFrom" required><br>

            <label for="validTo">Valid To <div id="validToError" class="error"></div></label>
            <input type="date" id="validTo" name="validTo" required><br>

            <label for="minOrderValue">Min Order Value <div id="minOrderValueError" class="error"></div></label>
            <input type="number" id="minOrderValue" name="minOrderValue" min="0" required><br>

            <label for="usageLimit">Usage Limit <div id="usageLimitError" class="error"></div></label>
            <input type="number" id="usageLimit" name="usageLimit" required><br>

            <label for="applicableTo">Applicable To <div id="applicableToError" class="error"></div></label>
            <select id="applicableTo" name="applicableTo" required>
                <option value="ALL">All</option>
                <option value="NEW_USER">New Users</option>
                <option value="EXISTING_USER">Existing Users</option>
            </select><br>

            <button type="submit" class="create">Create Coupon</button>
        </form>
    </div>
</div>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="/static/js/create-coupon.js"></script>
</body>
</html>