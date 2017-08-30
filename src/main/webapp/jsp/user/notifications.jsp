<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="/customtags" %>

<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="/localization/message" var="msg"/>

<fmt:message bundle="${msg}" key="label.projectTitle" var="projectTitle"/>

<fmt:message bundle="${msg}" key="label.notifications" var="notifications"/>

<fmt:message bundle="${msg}" key="message.noNotificationsYet" var="noNotificationsYet"/>

<fmt:message bundle="${msg}" key="form.addButton" var="addButton"/>

<fmt:message bundle="${msg}" key="notification.item_confirmed" var="item_confirmed"/>
<fmt:message bundle="${msg}" key="notification.item_not_confirmed" var="item_not_confirmed"/>
<fmt:message bundle="${msg}" key="notification.item_sold" var="item_sold"/>
<fmt:message bundle="${msg}" key="notification.no_bids_for_item" var="no_bids_for_item"/>
<fmt:message bundle="${msg}" key="notification.seller_canceled_auction" var="seller_canceled_auction"/>
<fmt:message bundle="${msg}" key="notification.bid_win" var="bid_win"/>
<fmt:message bundle="${msg}" key="notification.bid_beaten" var="bid_beaten"/>


<c:if test="${requestScope.notificationItemMap == null}">
    <jsp:forward page="${pageContext.request.contextPath}/controller">
        <jsp:param name="command" value="load-notifications"/>
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

            <div class="w3-row-padding w3-margin-top">

                <div class="w3-col s6 w3-margin-top">
                    <div class="middle-title uppercase">
                        ${notifications}
                    </div>
                </div>

            </div>

            <div class="w3-container" id="notificationsList">
                <c:choose>
                    <c:when test="${empty requestScope.notificationItemMap}">
                        <p>${noNotificationsYet}</p>
                    </c:when>
                    <c:otherwise>
                        <c:forEach var="entry" items="${requestScope.notificationItemMap}">

                            <div class="w3-container notification w3-margin">
                                <form action="${pageContext.request.contextPath}/controller">
                                    <input type="hidden" name="command" value="load-item"/>
                                    <input type="hidden" name="itemId"
                                           value="${entry.value.id}"/>
                                    <div class="w3-container w3-cell">
                                            ${entry.key.dateTime}
                                    </div>
                                    <div class="w3-container w3-cell">
                                            ${pageScope[entry.key.type.toString().toLowerCase()]}
                                    </div>
                                    <div class="w3-container w3-cell">
                                        <button class="link-button">
                                            (${entry.value.name})
                                        </button>
                                    </div>
                                </form>
                            </div>

                        </c:forEach>

                        <div class="w3-center">
                            <div id="pageBar" class="w3-bar w3-small w3-margin-top" data-command="load-bids"
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

<script src="${pageContext.request.contextPath}/js/pagination.js"></script>

<c:if test="${sessionScope.user == null}">
    <%@ include file="/jsp/jspf/sign_in.jsp" %>
    <%@ include file="/jsp/jspf/sign_up.jsp" %>
    <script src="${pageContext.request.contextPath}/js/sign.js"></script>
</c:if>

</body>

</html>