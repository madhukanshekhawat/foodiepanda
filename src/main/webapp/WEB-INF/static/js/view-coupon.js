function loadRestaurantCoupons() {
    $.ajax({
        url: '/coupon/all',
        method: 'GET',
        success: function(coupons) {
            const tbody = $('#couponsTable tbody');
            tbody.empty(); // Clear existing rows
            coupons.forEach(coupon => {
                tbody.append(`
                    <tr>
                        <td>${coupon.code}</td>
                        <td>${coupon.discountPercentage}%</td>
                        <td>${coupon.validFrom}</td>
                        <td>${coupon.validTo}</td>
                        <td>${coupon.minOrderValue}</td>
                        <td>${coupon.usageLimit}</td>
                        <td>
                          <select onChange = "confirmStatusChange(${coupon.couponId},this.value)">
                           <option value="ACTIVE" ${coupon.status === 'ACTIVE' ? 'selected' : ''}>ACTIVE</option>
                           <option value="INACTIVE" ${coupon.status === 'INACTIVE' ? 'selected' : ''}>INACTIVE</option>
                           <option value="EXPIRED" ${coupon.status === 'EXPIRED' ? 'selected' : ''}>EXPIRED</option>
                           </select>
                           <td>
                    </tr>
                `);
            });
        },
        error: function(xhr) {
            alert('Error fetching coupons: ' + xhr.responseText);
        }
    });
}
function confirmStatusChange(couponId, newStatus) {
            const confirmed = confirm("Are you sure you want to change the status to " + newStatus + "?");
            if (confirmed) {
                $.ajax({
                    url: "/coupon/update/" + couponId,
                    method: 'PATCH',
                    data: {status : newStatus},
                    success: function() {
                        alert("Status updated successfully!");
                        loadCoupons(); // Reload the table to reflect changes
                    },
                    error: function(xhr) {
                        alert("Error updating status: " + xhr.responseText);
                    }
                });
            } else {
                loadCoupons(); // Reload table to reset the dropdown
            }
        }

// Fetch coupons on page load
$(document).ready(function() {
    loadRestaurantCoupons();
});
