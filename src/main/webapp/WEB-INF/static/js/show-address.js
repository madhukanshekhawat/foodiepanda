$(document).ready(function () {
    loadAddresses();

    // Handle Add Address Form Submission
    $("#addAddressForm").submit(function (event) {
        event.preventDefault();
        let newAddress = {
            addressLine: $("#street").val().trim(),
            city: $("#city").val().trim(),
            state: $("#state").val().trim(),
            postalCode: $("#zipCode").val().trim(),
            label: $("input[name='addressType']:checked").val()
        };

        if (!newAddress.label) {
            alert("Please select an address type.");
            return;
        }

        // Check for duplicate address
        if (isDuplicateAddress(newAddress)) {
            alert("This address already exists.");
            return;
        }

        $.ajax({
            type: "POST",
            url: "/api/user/address/add",
            contentType: "application/json",
            data: JSON.stringify(newAddress),
            success: function () {
                alert("Address added successfully");
                $("#addAddressForm")[0].reset();
                loadAddresses();
            },
            error: function () {
                alert("Error adding address");
            }
        });
    });

    // Handle Edit Address Form Submission
    $("#editAddressForm").submit(function (event) {
        event.preventDefault();
        let updatedAddress = {
            addressLine: $("#editStreet").val().trim(),
            city: $("#editCity").val().trim(),
            state: $("#editState").val().trim(),
            postalCode: $("#editZipCode").val().trim(),
            label: $("input[name='editAddressType']:checked").val()
        };
        let addressId = $("#editAddressId").val();

        if (!updatedAddress.label) {
            alert("Please select an address type.");
            return;
        }

        // Check for duplicate address
        if (isDuplicateAddress(updatedAddress, addressId)) {
            alert("This address already exists.");
            return;
        }

        $.ajax({
            type: "PUT",
            url: "/api/user/customer/address/" + addressId,
            contentType: "application/json",
            data: JSON.stringify(updatedAddress),
            success: function () {
                alert("Address updated successfully");
                closeModal();
                loadAddresses();
            },
            error: function () {
                alert("Error updating address");
            }
        });
    });

    // Disable confirm button if no changes are made
    $("#editAddressForm input").on("input change", function () {
        checkForChanges();
    });
});

function loadAddresses() {
    $.ajax({
        type: "GET",
        url: "/api/user/addresses",
        success: function (addresses) {
            let container = $("#addressContainer");
            container.empty();

            if (addresses.length === 0) {
                $("#noAddressesMessage").text("No addresses found.");
                return;
            } else {
                $("#noAddressesMessage").text("");
            }

            let table = $("<table>").addClass("table table-bordered mt-4");
            let thead = $("<thead>").append(`
                <tr>
                    <th>Street</th>
                    <th>City</th>
                    <th>State</th>
                    <th>Zip Code</th>
                    <th>Type</th>
                    <th>Actions</th>
                </tr>
            `);
            let tbody = $("<tbody>");

            addresses.forEach(function (address) {
                let row = $("<tr>").append(`
                    <td>${address.addressLine}</td>
                    <td>${address.city}</td>
                    <td>${address.state}</td>
                    <td>${address.postalCode}</td>
                    <td>${address.label}</td>
                    <td>
                        <button class="btn btn-warning btn-sm" onclick="editAddress(${address.addressId}, '${address.addressLine}', '${address.city}', '${address.state}', '${address.postalCode}', '${address.label}')">Edit</button>
                    </td>
                `);
                tbody.append(row);
            });

            table.append(thead).append(tbody);
            container.append(table);

            // Store addresses for duplicate check
            $("#addressContainer").data("addresses", addresses);
        },
        error: function () {
            alert("Error fetching addresses");
        }
    });
}

function editAddress(id, addressLine, city, state, postalCode, label) {
    $("#editAddressId").val(id);
    $("#editStreet").val(addressLine);
    $("#editCity").val(city);
    $("#editState").val(state);
    $("#editZipCode").val(postalCode);
    $("input[name='editAddressType'][value='" + label + "']").prop("checked", true);

    // Store original values
    $("#editAddressForm").data("original", {
        addressLine: addressLine,
        city: city,
        state: state,
        postalCode: postalCode,
        label: label
    });

    checkForChanges();
    $("#editAddressModal").show();
}

function checkForChanges() {
    let original = $("#editAddressForm").data("original");
    let current = {
        addressLine: $("#editStreet").val().trim(),
        city: $("#editCity").val().trim(),
        state: $("#editState").val().trim(),
        postalCode: $("#editZipCode").val().trim(),
        label: $("input[name='editAddressType']:checked").val()
    };

    let isChanged = original.addressLine !== current.addressLine ||
                    original.city !== current.city ||
                    original.state !== current.state ||
                    original.postalCode !== current.postalCode ||
                    original.label !== current.label;

    $("#editAddressForm button[type='submit']").prop("disabled", !isChanged);
}

function isDuplicateAddress(address, excludeId) {
    let addresses = $("#addressContainer").data("addresses") || [];
    return addresses.some(function (existingAddress) {
        return existingAddress.addressLine.toLowerCase() === address.addressLine.toLowerCase() &&
               existingAddress.city.toLowerCase() === address.city.toLowerCase() &&
               existingAddress.state.toLowerCase() === address.state.toLowerCase() &&
               existingAddress.postalCode === address.postalCode &&
               existingAddress.label.toLowerCase() === address.label.toLowerCase() &&
               existingAddress.addressId !== excludeId;
    });
}

function closeModal() {
    $("#editAddressModal").hide();
}