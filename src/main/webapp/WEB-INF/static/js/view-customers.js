
$(document).ready(function() {
    loadCustomers();
});

function loadCustomers() {
    $.ajax({
        url: "/api/customer",
        method: "GET",
        success: function(response) {
            let html = "";
            response.forEach(customer => {
                html += `<tr>
                    <td>${customer.firstName} ${customer.lastName}</td>
                    <td>${customer.phoneNumber}</td>
                </tr>`;
            });
            $("#customer-data").html(html);
        },
        error: function() {
            $("#customer-data").html("<tr><td colspan='2' class='error'>Error loading Customers. Please try again.</td></tr>");
        }
    });
}
