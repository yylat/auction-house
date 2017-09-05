<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="/customtags" %>

<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="/localization/message" var="msg"/>

<fmt:message bundle="${msg}" key="label.projectTitle" var="projectTitle"/>

<fmt:message bundle="${msg}" key="menu.activeItems" var="activeItems"/>

<fmt:message bundle="${msg}" key="message.noItemsYet" var="noItemsYet"/>

<fmt:message bundle="${msg}" key="form.addButton" var="addButton"/>

<c:if test="${requestScope.items == null}">
    <jsp:forward page="${pageContext.request.contextPath}/controller">
        <jsp:param name="command" value="load-active-items"/>
    </jsp:forward>
</c:if>

<html>

<%@ include file="/jsp/jspf/head.jsp" %>

<body>

<%@ include file="/jsp/jspf/header.jsp" %>

<main>
    <%@ include file="/jsp/jspf/sidebar.jsp" %>
    <div class="w3-main main-left-margin">

        <div class="content">
            <ctg:items command="load-active-items" title="${activeItems}">
                <%@include file="/jsp/jspf/items.jsp" %>
            </ctg:items>
        </div>

    </div>
</main>

<%@ include file="/jsp/jspf/footer.jsp" %>

<c:if test="${sessionScope.user == null}">
    <%@ include file="/jsp/jspf/sign_in.jsp" %>
    <%@ include file="/jsp/jspf/sign_up.jsp" %>
    <script src="${pageContext.request.contextPath}/js/controller/sign.controller.js"></script>
</c:if>

</body>

</html>