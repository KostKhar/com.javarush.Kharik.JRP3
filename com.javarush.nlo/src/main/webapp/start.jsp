<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
 <style>
        .container {
            width: 80%;
            margin: 0 auto;
            text-align: center;
        }
    </style>
    <title>Как стать программистом</title>
    <link rel="stylesheet" href="<c:url value='/webjars/bootstrap/5.3.2/css/bootstrap.min.css' />">
</head>
<body>
 <div class="container">
    <h1> Привет, Амиго! </h1>
    <h2> Хочешь стать программистом? </h2>
<p class="d-inline-flex gap-1">
  <button type="button" class="btn" data-bs-toggle="button">Конечно хочу</button>
  <button type="button" class="btn active" data-bs-toggle="button" aria-pressed="true">Active toggle button</button>
  <button type="button" class="btn" disabled data-bs-toggle="button">Disabled toggle button</button>
</p>
<script src="<c:url value='/webjars/bootstrap/5.3.2/js/bootstrap.bundle.min.js' />"></script>
<img src="/static/images/main.jpeg" width="600" height"600"/>
    </div>
</body>
</html>