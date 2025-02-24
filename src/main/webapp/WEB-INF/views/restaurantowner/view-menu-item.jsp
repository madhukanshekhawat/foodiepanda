<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/restaurantowner/base.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Menu Items</title>
    <link rel="stylesheet" href="/static/css/view-menu-item.css">
    <link rel="stylesheet" href="//cdn.datatables.net/2.2.2/css/dataTables.dataTables.min.css">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body>
    <div class="container">
        <h1>Menu Items</h1>
        <div class="search-container">
            <input type="text" id="searchMenuItem" placeholder="Search menu items...">
            <button id="searchButton">Search</button>
            <button id="newDishButton" onclick="window.location.href='/restaurant/addMenu'" class="btn btn-primary">New Dish</button>
        </div>
        <div id="menuItemsContainer">
            <!-- Dynamic data will be injected here -->
        </div>
           <!-- Edit Menu Item Popup -->
           <div class="modal fade" id="editMenuItemModal" tabindex="-1" role="dialog" aria-labelledby="editMenuItemModalLabel" aria-hidden="true">
               <div class="modal-dialog" role="document">
                   <div class="modal-content">
                       <div class="modal-header">
                           <h5 class="modal-title" id="editMenuItemModalLabel">Edit Menu Item</h5>
                           <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                               <span aria-hidden="true">&times;</span>
                           </button>
                       </div>
                       <div class="modal-body">
                           <form id="editMenuItemForm">
                               <input type="hidden" id="menuItemId" name="menuItemId">
                               <div class="form-group">
                                   <label for="menuItemName">Name</label>
                                   <input type="text" class="form-control" id="menuItemName" name="menuItemName" required>
                               </div>
                               <div class="form-group">
                                   <label for="menuItemDescription">Description</label>
                                   <textarea class="form-control" id="menuItemDescription" name="menuItemDescription" required></textarea>
                               </div>
                               <div class="form-group ">
                                   <label for="menuItemPrice">Price</label>
                                   <input type="number" class="form-control" id="menuItemPrice" name="menuItemPrice" required>
                               </div>
                               <div class="text-center">
                                   <button type="button" class="btn btn-primary" onclick="submitEditMenuItem()">Save Changes</button>
                               </div>
                           </form>
                       </div>
                   </div>
               </div>
           </div>
<script src="/static/js/view-menu-item.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
<script src="//cdn.datatables.net/2.2.2/js/dataTables.min.js"></script>
</body>
</html>