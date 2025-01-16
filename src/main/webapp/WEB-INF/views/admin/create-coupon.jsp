<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/admin/base.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Create Coupon</title>
    <link rel="stylesheet" href="/static/css/create-coupon.css">
</head>
<body>
<div class="main">
    <h1>Create Coupon</h1>
    <div class="couponDiv">
        <form id="couponForm">
            <div id="restaurantIdError" class="error"></div>
            <label for="restaurantId">Restaurant</label>
            <select id="restaurantId" name="restaurantId" required>
                <option value="">Select a restaurant</option>
            </select><br>

            <div id="codeError" class="error"></div>
            <label for="code">Coupon Code</label>
            <input type="text" id="code" name="code" maxlength="10" required><br>

            <div id="discountPercentageError" class="error"></div>
            <label for="discountPercentage">Discount Percentage</label>
            <input type="number" id="discountPercentage" name="discountPercentage" min="0" required><br>

            <div id="validFromError" class="error"></div>
            <label for="validFrom">Valid From</label>
            <input type="date" id="validFrom" name="validFrom" required><br>

            <div id="validToError" class="error"></div>
            <label for="validTo">Valid To</label>
            <input type="date" id="validTo" name="validTo" required><br>

            <div id="minOrderValueError" class="error"></div>
            <label for="minOrderValue">Min Order Value</label>
            <input type="number" id="minOrderValue" name="minOrderValue" min="0" required><br>

            <div id="usageLimitError" class="error"></div>
            <label for="usageLimit">Usage Limit</label>
            <input type="number" id="usageLimit" name="usageLimit" required><br>

            <div id="applicableToError" class="error"></div>
            <label for="applicableTo">Applicable To</label>
            <select id="applicableTo" name="applicableTo" required>
                <option value="ALL">All</option>
                <option value="NEW_USER">New Users</option>
                <option value="EXISTING_USER">Existing Users</option>
            </select><br>

            <button type="submit">Create Coupon</button>
        </form>
    </div>
</div>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="/static/js/create-coupon.js"></script>
</body>
</html>