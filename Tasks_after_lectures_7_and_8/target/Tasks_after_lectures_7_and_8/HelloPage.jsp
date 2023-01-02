<%--
  Created by IntelliJ IDEA.
  User: Nastya
  Date: 01.01.2023
  Time: 15:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Hello Page</title>
    <style>
        <%@include file="/WEB-INF/css/style.css"%>
    </style>
</head>
<body>
    <div class="login-card-container">
        <div class="login-card">
            <div class="login-card-header">
                <h1>Select an action</h1>
            </div>
            <form action = "${path}/helloPage" method = "POST" class="login-card-form">
                <a class="myBtn" href="${path}/usersPage">Show users</a>
                <button type="submit">Log out</button>
            </form>
        </div>
    </div>
</body>
</html>
