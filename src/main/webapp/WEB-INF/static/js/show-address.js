$(document).ready(function () {
    loadAddresses();

    // Attach the openModal function to the button click event
    $('#addAddressButton').click(function() {
        openModal(false);
    });

    // Handle Address Form Submission
    $("#addressForm").submit(function (event) {
        event.preventDefault();
        let address = {
            addressLine: $("#street").val().trim(),
            city: $("#city").val().trim(),
            state: $("#state").val().trim(),
            postalCode: $("#zipCode").val().trim(),
            label: $("input[name='addressType']:checked").val()
        };
        let addressId = $("#addressId").val();

        if (!address.label) {
            alert("Please select an address type.");
            return;
        }

        if (!address.label || !address.addressLine || !address.city || !address.postalCode || !address.state) {
            alert("Please fill in all the required fields.");
            return;
        }

        // Check if postal code contains only numbers
        if (!/^\d+$/.test(address.postalCode)) {
            alert("Postal code must contain only numeric values.");
            return;
        }

        // Check if city and state do not contain numeric values
        if (/\d/.test(address.city) || /\d/.test(address.state)) {
            alert("City and State should not contain numeric values.");
            return;
        }

        // Check for duplicate address
        if (isDuplicateAddress(address, addressId)) {
            alert("This address already exists.");
            return;
        }

        if (addressId) {
            // Update existing address
            $.ajax({
                type: "PUT",
                url: "/api/user/customer/address/" + addressId,
                contentType: "application/json",
                data: JSON.stringify(address),
                success: function () {
                    alert("Address updated successfully");
                    closeModal();
                    loadAddresses();
                },
                error: function () {
                    alert("Error updating address");
                }
            });
        } else {
            // Add new address
            $.ajax({
                type: "POST",
                url: "/api/user/address/add",
                contentType: "application/json",
                data: JSON.stringify(address),
                success: function () {
                    alert("Address added successfully");
                    $("#addressForm")[0].reset();
                    closeModal();
                    loadAddresses();
                },
                error: function () {
                    alert("Error adding address");
                }
            });
        }
    });

    // Disable confirm button if no changes are made
    $("#addressForm input").on("input change", function () {
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
                        <button class="btn btn-warning btn-sm" onclick="openModal(true, ${address.addressId}, '${address.addressLine}', '${address.city}', '${address.state}', '${address.postalCode}', '${address.label}')">Edit</button>
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

function openModal(isEdit, id = null, addressLine = '', city = '', state = '', postalCode = '', label = 'HOME') {
    if (isEdit) {
        $("#modalTitle").text("Edit Address");
        $("#modalButton").text("Update Address");
        $("#addressId").val(id);
        $("#street").val(addressLine);
        $("#city").val(city);
        $("#state").val(state);
        $("#zipCode").val(postalCode);
        $("input[name='addressType'][value='" + label + "']").prop("checked", true);

        // Store original values
        $("#addressForm").data("original", {
            addressLine: addressLine,
            city: city,
            state: state,
            postalCode: postalCode,
            label: label
        });

        checkForChanges();
    } else {
        $("#modalTitle").text("Add New Address");
        $("#modalButton").text("Add Address");
        $("#addressForm")[0].reset();
        $("#addressId").val('');
    }
    $('#addressModal').modal('show');
}

function closeModal() {
    $('#addressModal').modal('hide');
}

function checkForChanges() {
    let original = $("#addressForm").data("original");
    let current = {
        addressLine: $("#street").val().trim(),
        city: $("#city").val().trim(),
        state: $("#state").val().trim(),
        postalCode: $("#zipCode").val().trim(),
        label: $("input[name='addressType']:checked").val()
    };

    let isChanged = original.addressLine !== current.addressLine ||
                    original.city !== current.city ||
                    original.state !== current.state ||
                    original.postalCode !== current.postalCode ||
                    original.label !== current.label;

    $("#addressForm button[type='submit']").prop("disabled", !isChanged);
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