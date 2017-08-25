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

<c:if test="${requestScope.users == null}">
    <jsp:forward page="${pageContext.request.contextPath}/controller">
        <jsp:param name="command" value="load-users"/>
    </jsp:forward>
</c:if>

<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${pageTitle}</title>
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
            <div class="w3-container w3-padding">
                <div class="w3-responsive">
                    <table class="w3-table w3-bordered w3-hoverable">
                        <caption class="capitalize w3-margin">${usersTable}</caption>
                        <thead>
                        <tr class="pro-green">
                            <th>
                                ${username}
                            </th>
                            <th>
                                ${fullName}
                            </th>
                            <th>
                                ${email}
                            </th>
                            <th>
                                ${phoneNumber}
                            </th>
                        </tr>
                        </thead>

                        <c:forEach var="user" items="${requestScope.users}">

                            <tr>
                                <td>${user.username}</td>
                                <td>
                                    <ctg:initials user="${user}"/>
                                </td>
                                <td>${user.email}</td>
                                <td>${user.phoneNumber}</td>
                            </tr>

                        </c:forEach>

                    </table>
                </div>
            </div>
        </div>

    </div>

</main>

<%@ include file="/jsp/jspf/footer.jsp" %>

<script src="${pageContext.request.contextPath}/js/event.js"></script>

</body>