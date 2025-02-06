// Fetch coupons on page load
$(document).ready(function() {
    loadRestaurantCoupons();
});
function loadRestaurantCoupons() {
    $.ajax({
        url: '/coupon/all',
        method: 'GET',
        success: function(coupons) {
            const container = $('#couponsContainer');
            container.empty(); // Clear existing content

            if (coupons.length === 0) {
                container.append("<p class='no-coupons'>No coupons found.</p>");
                return;
            }

            // Create table
            const table = `
                <table border="1" id="couponsTable">
                    <thead>
                        <tr>
                            <th>Code</th>
                            <th>Discount %</th>
                            <th>Valid From</th>
                            <th>Valid To</th>
                            <th>Min Order Value</th>
                            <th>Usage Limit</th>
                            <th>Applicable To</th>
                            <th>Action</th>
                        </tr>
                    </thead>
                    <tbody id="couponsTableBody">
                    </tbody>
                </table>
            `;
            container.append(table);

            // Populate table with coupons
            const tbody = $('#couponsTableBody');
            coupons.forEach(coupon => {
                const row = `
                    <tr>
                        <td>${coupon.code}</td>
                        <td>${coupon.discountPercentage}</td>
                        <td>${coupon.validFrom}</td>
                        <td>${coupon.validTo}</td>
                        <td>${coupon.minOrderValue}</td>
                        <td>${coupon.usageLimit}</td>
                        <td>${coupon.applicableTo}</td>
                        <td>
                            <select onChange="confirmStatusChange(${coupon.couponId}, this.value)">
                                <option value="ACTIVE" ${coupon.status === 'ACTIVE' ? 'selected' : ''}>ACTIVE</option>
                                <option value="INACTIVE" ${coupon.status === 'INACTIVE' ? 'selected' : ''}>INACTIVE</option>
                                <option value="EXPIRED" ${coupon.status === 'EXPIRED' ? 'selected' : ''}>EXPIRED</option>
                            </select>
                        </td>
                    </tr>
                `;
                tbody.append(row);
            });
        },
        error: function(xhr) {
            alert('Error fetching coupons');
        }
    });
}

function confirmStatusChange(couponId, newStatus) {
    const confirmed = confirm("Are you sure you want to change the status to " + newStatus + "?");
    if (confirmed) {
        $.ajax({
            url: "/coupon/update/" + couponId,
            method: 'PUT',
            data: { status: newStatus },
            success: function() {
                alert("Status updated successfully!");

                // Optimistic update: Change status directly in the frontend
                $(`#couponsTableBody tr`).each(function () {
                    const row = $(this);
                    if (row.find('td:first').text() == couponId) {
                        row.find('selected').val(newStatus);
                    }
                });
            },
            error: function(xhr) {
                alert("Error updating status: " + xhr.responseText);
            }
        });
    }
}
