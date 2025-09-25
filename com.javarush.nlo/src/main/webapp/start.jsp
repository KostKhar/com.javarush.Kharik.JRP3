<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
  <jsp:include page="/WEB-INF/common/header.jsp"/>
<<body>
     <div class="container">
         <h1>${header}</h1>

         <c:if test="${not empty error}">
             <div class="error">${error}</div>
         </c:if>

         <form action="start" method="post">
             <div class="d-inline-flex gap-3 mt-4">
                 <button type="submit" name="answer" value="yes" class="btn btn-primary btn-lg">
                     ${answer1}
                 </button>
                 <button type="submit" name="answer" value="no" class="btn btn-secondary btn-lg">
                     ${answer2}
                 </button>
             </div>
         </form>

         <img src="/static/images/main.jpeg" alt="Программирование" class="mt-4" width="400">
     </div>
 </body>
</html>