//
//    $(document).ready(function() {
//
//
//
//        $.ajax({
//            url: "/api/restaurant/detail/" + restaurantId,
//            method: "GET",
//            success: function(data) {
//                $("#restaurantName").text(data.name);
//                $("#restaurantAddress").text(data.address);
//                $("#availability").text(data.isAvailable ? "Open" : "Closed");
//                $("#startTime").text(data.startTime);
//                $("#endTime").text(data.endTime);
//
//                let menuHtml = "<ul>";
//                data.menuItems.forEach(item => {
//                    menuHtml += `<li style="color: ${item.isAvailable ? 'black' : 'gray'}">
//                        ${item.name} - ${item.price} RS
//                    </li>`;
//                });
//                menuHtml += "</ul>";
//
//                $("#menuItems").html(menuHtml);
//            },
//            error: function() {
//                alert("Error loading restaurant details.");
//            }
//        });
//    });
