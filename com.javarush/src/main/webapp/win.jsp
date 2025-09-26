<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Квест</title>
    <jsp:include page="/WEB-INF/common/header.jsp"/>
</head>
<body>
    <div class="container">
        <h1><%= request.getAttribute("name") %></h1>

        <p><%= request.getAttribute("description") %></p>

        <%
            String error = (String) request.getAttribute("error");
            if (error != null && !error.isEmpty()) {
        %>
            <div class="alert alert-danger"><%= error %></div>
        <%
            }
        %>

        <form action="start" method="post">
            <div class="d-inline-flex gap-3 mt-4">
                 <button type="submit"  name="answer" value="yes" class="btn btn-primary" data-bs-toggle="button">
                                <a> Я готов попробовать заново! </a>
                    </button>
            </div>
        </form>
 <img src="/static/images/fatDev.jpeg" alt="Winner" class="mt-4" width="400">
    </div>
</body>
</html>
