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

        <form action="start" method="post">
            <div class="d-inline-flex gap-3 mt-4">
                <button type="submit" name="answer" value = "yes" class="btn btn-primary btn-lg">
                    <%= request.getAttribute("yes") %>
                </button>
                <button type="submit" name="answer" value="no" class="btn btn-secondary btn-lg">
                    <%= request.getAttribute("no") %>
                </button>
            </div>
        </form>

        <img src="/static/images/main.jpeg" alt="Программирование" class="mt-4" width="400">
    </div>
</body>
</html>