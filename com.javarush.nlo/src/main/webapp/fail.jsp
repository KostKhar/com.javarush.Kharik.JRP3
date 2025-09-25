<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
  <jsp:include page="/WEB-INF/common/header.jsp"/>
<body>
<div class="container">
<p class="d-inline-flex gap-1">
            <button type="submit"  name="answer1" class="btn btn-primary" data-bs-toggle="button">
                <a> Я готов попробовать заново! </a>
            </button>
        </p>
 </form>
        <script src="<c:url value='/webjars/bootstrap/5.3.2/js/bootstrap.bundle.min.js' />"></script>
        <img src="<c:url value='src/main/resources/static/images/main.jpeg'/>" alt="На старт" width="600" height="600"/>
    </div>
</body>
</html>