<%@ page isErrorPage="true" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="/customtags" %>

<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="/localization/message" var="msg"/>

<fmt:message bundle="${msg}" key="page.management" var="pageTitle"/>

<fmt:message bundle="${msg}" key="form.username" var="username"/>
<fmt:message bundle="${msg}" key="form.email" var="email"/>
<fmt:message bundle="${msg}" key="form.phoneNumber" var="phoneNumber"/>

<fmt:message bundle="${msg}" key="label.fullName" var="fullName"/>

<fmt:message bundle="${msg}" key="label.usersTable" var="usersTable"/>
<fmt:message bundle="${msg}" key="user.ban" var="ban"/>
<fmt:message bundle="${msg}" key="user.unban" var="unban"/>
<fmt:message bundle="${msg}" key="menu.users" var="users"/>

<c:if test="${requestScope.users == null}">
    <jsp:forward page="${pageContext.request.contextPath}/controller">
        <jsp:param name="command" value="load-users"/>
    </jsp:forward>
</c:if>

<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${projectTitle}</title>
    <link rel="icon" href="${pageContext.request.contextPath}/img/ic_gavel_black.png">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/reset.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/w3.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>

<body>

<%@ include file="/jsp/jspf/header.jsp" %>

<main>

    <%@ include file="/jsp/jspf/sidebar.jsp" %>

    <div class="w3-main main-left-margin">

        <div class="content">

            <div class="w3-container w3-margin-top w3-margin-bottom middle-title uppercase">
                ${users}
            </div>
            <div class="pro-margin-bottom">
                <c:forEach var="user" items="${requestScope.users}">
                    <div class="w3-container w3-margin-bottom w3-border-bottom">

                        <div class="w3-row-padding w3-margin">
                            <div class="w3-col m6">
                                <div class="w3-container">
                                        ${username}: ${user.username}
                                </div>
                            </div>
                            <div class="w3-col m6">
                                <div class="w3-container">
                                        ${fullName}: ${user.lastName} ${user.firstName} ${user.middleName}
                                </div>
                            </div>
                        </div>

                        <div class="w3-row-padding w3-margin">
                            <div class="w3-col m6">
                                <div class="w3-container">
                                        ${phoneNumber}: ${user.phoneNumber}
                                </div>
                            </div>
                            <div class="w3-col m6">
                                <div class="w3-container">
                                        ${email}: ${user.email}
                                </div>
                            </div>
                        </div>

                        <div class="w3-container w3-margin">
                            <form action="${pageContext.request.contextPath}/controller" method="post">
                                <input type="hidden" name="userId" value="${user.id}"/>
                                <c:choose>
                                    <c:when test="${!user.isBanned}">
                                        <input type="hidden" name="command" value="ban-user"/>
                                        <button class="w3-button pro-purple w3-ripple">${ban}</button>
                                    </c:when>
                                    <c:otherwise>
                                        <input type="hidden" name="command" value="unban-user"/>
                                        <button class="w3-button pro-purple w3-ripple">${unban}</button>
                                    </c:otherwise>
                                </c:choose>
                            </form>
                        </div>

                    </div>
                </c:forEach>
            </div>

            <div class="page-bar w3-center">
                <div id="pageBar" class="w3-bar w3-small w3-margin-top" data-command="load-users"
                     data-page="${requestScope.page}" data-pages="${requestScope.pages}">
                    <a id="prevLink" class="w3-button">&laquo;</a>

                    <a id="nextLink" class="w3-button">&raquo;</a>
                </div>
            </div>

        </div>

    </div>

</main>

<%@ include file="/jsp/jspf/footer.jsp" %>

<script src="${pageContext.request.contextPath}/js/pagination.js"></script>

</body>