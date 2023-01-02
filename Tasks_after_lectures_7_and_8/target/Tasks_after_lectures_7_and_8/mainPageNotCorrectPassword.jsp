<%--
  Created by IntelliJ IDEA.
  User: Nastya
  Date: 02.01.2023
  Time: 13:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Login Page</title>
  <style>
    <%@include file="/WEB-INF/css/style.css"%>
    ::placeholder {
    color: black;
    opacity: 1;
  </style>
  <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Material+Symbols+Rounded:opsz,wght,FILL,GRAD@48,300,0,0" />
</head>
<body>
<div class="login-card-container">
  <div class="login-card">
    <div class="login-card-logo">
      <img src="https://i.imgur.com/pcKbo0F.png" alt="logo">
    </div>
    <div class="login-card-header">
      <h1>Sign In</h1>
      <div>Please login to use platform</div>
    </div>
    <form action = "${path}/" method = "POST" class="login-card-form">
      <div class="form-item">
        <span class="form-item-icon material-symbols-rounded">mail</span>
        <input type="text" name="email" placeholder="Enter Email" required autofocus>
      </div>
      <div class="form-item">
        <span class="form-item-icon material-symbols-rounded">lock</span>
        <input style="background:rgb(255, 36, 0)" type="password" name="password" placeholder="Password is not correct" required>
      </div>
      <div class="form-item-other">
        <div class="checkbox">
          <input type="checkbox" id="rememberMeCheckbox">
          <label for="rememberMeCheckbox">Remember me</label>
        </div>
        <a href="#">Forgot password</a>
      </div>
      <button type="submit">Sign In</button>
    </form>
    <div class="login-card-footer">
      Don`t have account? <a href="#">Create a free account</a>
    </div>
  </div>
  <div class="login-card-social">
    <div>Other Sign-in platform</div>
    <div class="login-card-social-btn">
      <a href="#">
        <svg xmlns="http://www.w3.org/2000/svg" class="icon icon-tabler icon-tabler-brand-facebook" width="44" height="44" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" fill="none" stroke-linecap="round" stroke-linejoin="round">
          <path stroke="none" d="M0 0h24v24H0z" fill="none"/>
          <path d="M7 10v4h3v7h4v-7h3l1 -4h-4v-2a1 1 0 0 1 1 -1h3v-4h-3a5 5 0 0 0 -5 5v2h-3" />
        </svg>
      </a>
      <a href="#">
        <svg xmlns="http://www.w3.org/2000/svg" class="icon icon-tabler icon-tabler-brand-google" width="44" height="44" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" fill="none" stroke-linecap="round" stroke-linejoin="round">
          <path stroke="none" d="M0 0h24v24H0z" fill="none"/>
          <path d="M17.788 5.108a9 9 0 1 0 3.212 6.892h-8" />
        </svg>
      </a>
    </div>
  </div>
</div>
</body>
</html>
