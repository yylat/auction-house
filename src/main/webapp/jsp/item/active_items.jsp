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

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${projectTitle}</title>
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

            <div class="w3-row-padding w3-margin-top">

                <div class="w3-col s6 w3-margin-top">
                    <div class="middle-title uppercase">
                        ${activeItems}
                    </div>
                </div>

            </div>

            <div class="w3-row-padding" id="itemsList">

                <c:choose>
                    <c:when test="${empty requestScope.items}">
                        <p>${noItemsYet}</p>
                    </c:when>
                    <c:otherwise>
                        <%@ include file="/jsp/jspf/items_list.jsp" %>

                        <div class="w3-center">
                            <div id="pageBar" class="w3-bar w3-small w3-margin-top" data-command="load-active-items"
                                 data-page="${requestScope.page}" data-pages="${requestScope.pages}">
                                <a id="prevLink" class="w3-button">&laquo;</a>

                                <a id="nextLink" class="w3-button">&raquo;</a>
                            </div>
                        </div>

                    </c:otherwise>
                </c:choose>

            </div>

        </div>

    </div>

</main>

<%@ include file="/jsp/jspf/footer.jsp" %>

<script src="${pageContext.request.contextPath}/js/load-img.js"></script>

<script src="${pageContext.request.contextPath}/js/pagination.js"></script>

<c:if test="${sessionScope.user == null}">
    <%@ include file="/jsp/jspf/sign_in.jsp" %>
    <%@ include file="/jsp/jspf/sign_up.jsp" %>
    <script src="${pageContext.request.contextPath}/js/sign.js"></script>
</c:if>

</body>

</html>