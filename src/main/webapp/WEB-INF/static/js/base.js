$(document).ready(function() {
    // Get the current URL path
    var path = window.location.pathname;

    // Remove 'active' class from all links
    $(".nav-link").removeClass("active");

    // Add 'active' class to the current link
    if (path.includes("/admin/dashboard")) {
        $("#homeLink").addClass("active");
    } else if (path.includes("/admin/view-restaurant")) {
        $("#viewRestaurantLink").addClass("active");
    } else if (path.includes("/admin/view-customer")) {
        $("#viewCustomerLink").addClass("active");
    } else if (path.includes("/admin/approve-owners")) {
        $("#approveOwnerLink").addClass("active");
    } else if (path.includes("/admin/create-coupon")) {
        $("#createCouponLink").addClass("active");
    } else if (path.includes("/admin/approved-owners")) {
        $("#viewOwnerLink").addClass("active");
    }
    $("#navbarToggle").click(function() {
            $("#navbarNav").toggleClass("show");
        });
});
