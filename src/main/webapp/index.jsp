<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head></head>
<body>
<jsp:forward page="${pageContext.request.contextPath}/controller">
    <jsp:param name="command" value="load-active-items"/>
</jsp:forward>
</body>
</html>