<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<head>
    <meta charset="UTF-8">
    <style>
        .container {
            width: 80%;
            margin: 0 auto;
            text-align: center;
        }
        .btn-custom {
            margin: 10px;
            padding: 15px 30px;
            font-size: 18px;
        }
        .quest-title {
            color: #2c3e50;
            margin-bottom: 30px;
        }
    </style>
    <title><c:out value="${pageTitle}" default="Квест программиста"/></title>
   <link href="/webjars/bootstrap/5.3.2/css/bootstrap.min.css" rel="stylesheet">
    <script src="/webjars/bootstrap/5.3.2/js/bootstrap.min.js"></script>
</head>