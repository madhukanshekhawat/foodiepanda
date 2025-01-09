<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/admin/base.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Approved Owners</title>
    <link rel="stylesheet" href="/static/css/approved-owners.css">

</head>
<body>

<div class="content">
    <h1>Approved Restaurant Owners</h1>

<div class="table-container">
    <table>
        <thead>
            <tr>
                <th>Owner Name</th>
                <th>Email</th>
                <th>Phone Number</th>
            </tr>
        </thead>
        <tbody id="ownersTable">
            <!-- Data will be loaded dynamically -->
        </tbody>
    </table>
    </div>
    </div>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="/static/js/view-owners.js"></script>
</body>

</html>
