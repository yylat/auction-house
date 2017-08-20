<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="customtags" %>

<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="/localization/message" var="msg"/>

<fmt:message bundle="${msg}" key="label.projectTitle" var="projectTitle"/>

<fmt:message bundle="${msg}" key="menu.bids" var="bids"/>

<fmt:message bundle="${msg}" key="message.noBidsYet" var="noBidsYet"/>

<fmt:message bundle="${msg}" key="form.addButton" var="addButton"/>

<fmt:message bundle="${msg}" key="form.itemTitle" var="itemTitle"/>
<fmt:message bundle="${msg}" key="item.actualPrice" var="actualPrice"/>
<fmt:message bundle="${msg}" key="form.yourBid" var="yourBid"/>

<c:if test="${requestScope.bidItemMap == null}">
    <jsp:forward page="${pageContext.request.contextPath}/controller">
        <jsp:param name="command" value="load-user-bids"/>
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
                        ${bids}
                    </div>
                </div>

            </div>

            <div class="w3-row-padding">

                <c:choose>
                    <c:when test="${empty requestScope.bidItemMap}">
                        <p>${noBidsYet}</p>
                    </c:when>
                    <c:otherwise>

                        <div class="w3-responsive">
                            <table>
                                <thead>
                                <tr class="pro-green">
                                    <th>
                                            ${itemTitle}
                                    </th>
                                    <th>
                                            ${actualPrice}
                                    </th>
                                    <th>
                                            ${yourBid}
                                    </th>
                                </tr>
                                </thead>

                                <c:forEach items="${requestScope.itemBidMap}" var="entry">
                                    <tr>
                                        <td>
                                            <form action="${pageContext.request.contextPath}/controller">
                                                <input type="hidden" name="command" value="load-item"/>
                                                <input type="hidden" name="item-id"
                                                       value="${requestScope.itemBidMap.value.id}"/>
                                                    ${requestScope.itemBidMap.value.name}
                                            </form>
                                        </td>
                                        <td>${requestScope.itemBidMap.value.actualPrice}</td>
                                        <td>${requestScope.itemBidMap.key.bidValue}</td>
                                    </tr>

                                </c:forEach>

                            </table>
                        </div>

                    </c:otherwise>
                </c:choose>

            </div>

        </div>

    </div>

</main>

<script src="${pageContext.request.contextPath}/js/load-img.js"></script>

<%@ include file="/jsp/jspf/footer.jsp" %>

<%@ include file="/jsp/jspf/item-modal.jsp" %>

<script src="${pageContext.request.contextPath}/js/event.js"></script>

</body>

</html>