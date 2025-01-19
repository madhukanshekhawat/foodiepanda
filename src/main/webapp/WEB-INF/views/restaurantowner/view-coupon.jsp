<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/restaurantowner/base.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>View All Coupons</title>
    <link rel="stylesheet" href="static/css/view-coupon.css">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

    <script>
        $(document).ready(function() {
            fetchCoupons();
        });

        function fetchCoupons() {
            $.ajax({
                url: "/api/owner/coupon", // API endpoint
                method: "GET",
                success: function(response) {
                    let tableBody = $("#couponTable tbody");
                    tableBody.empty(); // Clear existing data

                    if (response.length === 0) {
                        tableBody.append("<tr><td colspan='7'>No Coupons Available</td></tr>");
                        return;
                    }

                    response.forEach(coupon => {
                        let row = `<tr>
                            <td>${coupon.couponId}</td>
                            <td>${coupon.code}</td>
                            <td>${coupon.discountPercentage}%</td>
                            <td>${coupon.validFrom}</td>
                            <td>${coupon.validTo}</td>
                            <td>${coupon.minOrderValue}</td>
                            <td>${coupon.usageLimit}</td>
                            <td>${coupon.applicableTo}</td>
                        </tr>`;
                        tableBody.append(row);
                    });
                },
                error: function() {
                    alert("Error fetching coupons.");
                }
            });
        }
    </script>
</head>
<body>
    <div class="container">
        <h2>All Coupons</h2>
        <table border="1" id="couponTable">
            <thead>
                <tr>
                    <th>Coupon ID</th>
                    <th>Code</th>
                    <th>Discount (%)</th>
                    <th>Valid From</th>
                    <th>Valid To</th>
                    <th>Min Order Value</th>
                    <th>Usage Limit</th>
                    <th>Applicable To</th>
                </tr>
            </thead>
            <tbody>
                <!-- Coupons will be dynamically loaded here -->
            </tbody>
        </table>
    </div>

</body>
</html>
