        // Fetch all menu items for the logged-in restaurant owner
       function loadMenuItems() {
           $.ajax({
               url: "/menu/all", // Backend API endpoint
               method: "GET",
               success: function (data) {
                   const container = $("#menuItemsContainer");
                   container.empty(); // Clear existing data

                   if (data.length === 0) {
                       container.append("<p class='no-item'>No menu items found.</p>");
                       return;
                   }

                   // Create table
                   const table = `
                       <table>
                           <thead>
                               <tr>
                                   <th>Name</th>
                                   <th>Description</th>
                                   <th>Price (RS)</th>
                                   <th>Category</th>
                                   <th>Image</th>
                                   <th>Availability</th>
                                   <th>Action</th>
                               </tr>
                           </thead>
                           <tbody id="menuItemsTableBody">
                           </tbody>
                       </table>
                   `;
                   container.append(table);

                   // Populate table with menu items
                   const tableBody = $("#menuItemsTableBody");
                   data.forEach(item => {
                       const menuItem = `
                           <tr class="menu-item">
                               <td>${item.name}</td>
                               <td>${item.description}</td>
                               <td>${item.price ? item.price : 'N/A'}</td>
                               <td>${item.categoryName ? item.categoryName : 'Uncategorized'}</td>
                               <td><img src="data:image/jpeg;base64,${item.image}" alt="${item.name}" style="width: 150px; height: 100px;"/></td>
                               <td>
                                   <select id="availability-${item.id}" onchange="confirmAvailabilityChange(${item.id}, this.value)">
                                       <option value="true" ${item.available ? 'selected' : ''}>Available</option>
                                       <option value="false" ${!item.available ? 'selected' : ''}>Unavailable</option>
                                   </select>
                               </td>
                               <td>
                               <button class="btn btn-danger btn-sm" onclick="deleteMenuItem(${item.id})">Delete</button>
                               </td>
                           </tr>
                       `;
                       tableBody.append(menuItem);
                   });
               },
               error: function () {
                   alert("Error fetching menu items.");
               }
           });
       }

       // Confirm and change availability status
       function confirmAvailabilityChange(menuItemId, newValue) {
           const confirmed = confirm("Are you sure you want to change the availability status?");
           if (confirmed) {
               $.ajax({
                   url: "/menu/" + menuItemId + "/availability",
                   method: "PUT",
                   data: { available: newValue },
                   success: function () {
                       alert("Availability status updated successfully.");
                       loadMenuItems(); // Reload the menu items
                   },
                   error: function () {
                       alert("Error updating availability status.");
                   }
               });
           }
       }

       function deleteMenuItem(menuItemId) {
                   if (confirm("Are you sure you want to delete this menu item?")) {
                       $.ajax({
                           url: "/menu/" + menuItemId,
                           method: "DELETE",
                           success: function () {
                               alert("Menu item deleted successfully.");
                               loadMenuItems(); // Reload menu items
                           },
                           error: function () {
                               alert("Failed to delete menu item.");
                           }
                       });
                   }
               }

       // Load menu items on page load
       $(document).ready(function () {
           loadMenuItems();
       });