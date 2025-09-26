<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
  <jsp:include page="/WEB-INF/common/header.jsp"/>
<body>
<div class="container">
<p class="d-inline-flex gap-1">
            <button type="submit"  name="answer" class="btn btn-primary" data-bs-toggle="button">
                <%= request.getParameter("answer1") != null ? request.getParameter("answer1") : "Конечно хочу!" %>
            </button>
            <button type="submit" name="answer" class="btn btn-primary" data-bs-toggle="button" aria-pressed="true">
                <%= request.getParameter("answer2") != null ? request.getParameter("answer2") : "Пока сомневаюсь" %>
            </button>
 </p>

        <script src="<c:url value='/webjars/bootstrap/5.3.2/js/bootstrap.bundle.min.js' />"></script>
        <img src="<c:url value='src/main/resources/static/images/main.jpeg'/>" alt="Программирование" width="600" height="600"/>
    </div>
</body>
</html>