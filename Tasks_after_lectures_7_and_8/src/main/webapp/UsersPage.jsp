<%--
  Created by IntelliJ IDEA.
  User: Nastya
  Date: 01.01.2023
  Time: 22:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Users Page</title>
    <style>
        <%@include file="/WEB-INF/css/style.css"%>
    </style>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.4/css/bootstrap.min.css" />
</head>
<body>
    <div class="login-card-container">
        <div class="login-card">
            <div class="login-card-header">
                <h1>Registered users</h1>
            </div>
            <table class="table table-bordered table-hover">
                <thead><td>Email</td><td>Username</td></thead>
                <c:forEach var="user" items="${usersList}">
                    <tr>
                        <td>${user.getEmail()}</td>
                        <td>${user.getUsername()}</td>
                    </tr>
                </c:forEach>
            </table>
            <a class="btn" href="${path}/helloPage">Back to Hello Page</a>
        </div>
    </div>
</body>
</html>
