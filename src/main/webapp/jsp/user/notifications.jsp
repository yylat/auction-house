<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="/customtags" %>

<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="/localization/message" var="msg"/>

<fmt:message bundle="${msg}" key="label.notifications" var="notifications"/>

<fmt:message bundle="${msg}" key="message.noNotificationsYet" var="noNotificationsYet"/>

<fmt:message bundle="${msg}" key="form.addButton" var="addButton"/>

<fmt:message bundle="${msg}" key="notification.item_confirmed" var="item_confirmed"/>
<fmt:message bundle="${msg}" key="notification.item_not_confirmed" var="item_not_confirmed"/>
<fmt:message bundle="${msg}" key="notification.item_sold" var="item_sold"/>
<fmt:message bundle="${msg}" key="notification.no_bids_for_item" var="no_bids_for_item"/>
<fmt:message bundle="${msg}" key="notification.seller_canceled_auction" var="seller_canceled_auction"/>
<fmt:message bundle="${msg}" key="notification.bid_won" var="bid_won"/>
<fmt:message bundle="${msg}" key="notification.bid_beaten" var="bid_beaten"/>


<c:if test="${requestScope.notifications == null}">
    <jsp:forward page="${pageContext.request.contextPath}/controller">
        <jsp:param name="command" value="load-notifications"/>
    </jsp:forward>
</c:if>

<html>

<%@ include file="/WEB-INF/jspf/head.jsp" %>

<body>

<%@ include file="/WEB-INF/jspf/header.jsp" %>

<main>

    <%@ include file="/WEB-INF/jspf/sidebar.jsp" %>

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
                    <c:when test="${empty requestScope.notifications}">
                        <p>${noNotificationsYet}</p>
                    </c:when>
                    <c:otherwise>
                        <div class="w3-margin-top pro-margin-bottom">

                            <div class="w3-responsive">
                                <table class="w3-table w3-striped">

                                    <c:forEach var="notification" items="${requestScope.notifications}">
                                        <tr>
                                            <td>
                                                <ctg:date date="${notification.dateTime}"
                                                          locale="${sessionScope.locale}"/>
                                            </td>
                                            <td>
                                                    ${pageScope[notification.type.toString().toLowerCase()]}
                                            </td>
                                            <td>
                                                <form action="${pageContext.request.contextPath}/controller">
                                                    <input type="hidden" name="command" value="load-item"/>
                                                    <input type="hidden" name="itemId"
                                                           value="${requestScope.items[notification.itemId].id}"/>
                                                    <button class="empty-design-button">
                                                        <span class="link-button">
                                                                ${requestScope.items[notification.itemId].name}
                                                        </span>
                                                    </button>
                                                </form>
                                            </td>
                                        </tr>
                                    </c:forEach>

                                </table>
                            </div>
                        </div>

                        <div class="page-bar w3-center">
                            <div id="pageBar" class="w3-bar w3-small w3-margin-top" data-command="load-bids"
                                 data-page="${requestScope.page}" data-pages="${sessionScope.pages}">
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

<%@ include file="/WEB-INF/jspf/footer.jsp" %>

<script src="${pageContext.request.contextPath}/js/pagination.js"></script>

<c:if test="${sessionScope.user == null}">
    <%@ include file="/WEB-INF/jspf/sign_in.jsp" %>
    <%@ include file="/WEB-INF/jspf/sign_up.jsp" %>
    <script src="${pageContext.request.contextPath}/js/controller/sign.controller.js"></script>
</c:if>

<c:set scope="session" var="currentPage" value="/jsp/user/notifications.jsp"/>

</body>

</html>