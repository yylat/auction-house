<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="customtags" %>

<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="/localization/message" var="msg"/>

<fmt:message bundle="${msg}" key="label.projectTitle" var="projectTitle"/>

<fmt:message bundle="${msg}" key="menu.items" var="items"/>

<fmt:message bundle="${msg}" key="message.noItemsYet" var="noItemsYet"/>

<fmt:message bundle="${msg}" key="label.approve" var="approve"/>

<fmt:message bundle="${msg}" key="form.itemTitle" var="itemTitle"/>
<fmt:message bundle="${msg}" key="form.description" var="description"/>
<fmt:message bundle="${msg}" key="form.startPrice" var="startPrice"/>
<fmt:message bundle="${msg}" key="form.blitzPrice" var="blitzPrice"/>
<fmt:message bundle="${msg}" key="form.startDate" var="startDate"/>
<fmt:message bundle="${msg}" key="form.closeDate" var="closeDate"/>
<fmt:message bundle="${msg}" key="form.category" var="category"/>
<fmt:message bundle="${msg}" key="form.photos" var="photos"/>

<fmt:message bundle="${msg}" key="item.actualPrice" var="actualPrice"/>
<fmt:message bundle="${msg}" key="item.prices" var="prices"/>
<fmt:message bundle="${msg}" key="item.dates" var="dates"/>
<fmt:message bundle="${msg}" key="item.startPrice" var="itemStartPrice"/>
<fmt:message bundle="${msg}" key="item.blitzPrice" var="itemBlitzPrice"/>
<fmt:message bundle="${msg}" key="item.startDate" var="itemStartDate"/>
<fmt:message bundle="${msg}" key="item.closeDate" var="itemCloseDate"/>

<fmt:message bundle="${msg}" key="form.priceRule" var="priceRule"/>

<fmt:message bundle="${msg}" key="message.noPhotosForItem" var="noPhotosForItem"/>

<fmt:message bundle="${msg}" key="label.makeBid" var="makeBid"/>

<c:if test="${requestScope.item == null}">
    <jsp:forward page="${pageContext.request.contextPath}/controller">
        <jsp:param name="command" value="load-active-items"/>
    </jsp:forward>
</c:if>

<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${projectTitle}|${requestScope.item.name}</title>
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

                <div class="w3-container w3-right">

                    <c:if test="${sessionScope.user!=null}">
                        <c:choose>
                            <c:when test="${(requestScope.item.status.id == 1) && (sessionScope.user.role.id==1)}">
                                <form action="${pageContext.request.contextPath}/controller">
                                    <input type="hidden" name="command" value="approve-item"/>
                                    <input type="hidden" name="item-id" value="${requestScope.item.id}"/>
                                    <button class="w3-button w3-round-xlarge pro-green w3-ripple">
                                            ${approve}
                                    </button>
                                </form>
                            </c:when>
                            <c:when test="${(requestScope.item.status.id == 3) && (sessionScope.user.id != requestScope.item.sellerId)}">
                                <form action="">
                                    <input type="hidden" name="command" value=""/>
                                    <button class="w3-button w3-round-xlarge pro-green w3-ripple">
                                            ${makeBid}
                                    </button>
                                </form>
                            </c:when>
                            <c:when test="${sessionScope.user.id == requestScope.item.sellerId}">
                                <form action="">
                                    <input type="hidden" name="command" value=""/>
                                    <button class="w3-button w3-round-xlarge pro-green w3-ripple">
                                        edit
                                    </button>
                                </form>
                            </c:when>
                        </c:choose>
                    </c:if>
                </div>

            </div>

            <div class="w3-container w3-margin middle-title uppercase">
                <b>${requestScope.item.name}</b>
            </div>

            <div class="w3-container w3-margin middle-title">
                ${actualPrice}:
                <div class="text-on-color money">${requestScope.item.actualPrice}</div>
            </div>

            <c:if test="${(requestScope.item.status.id == 3) && (sessionScope.user.id != requestScope.item.sellerId)}">
                <div class="w3-container w3-margin">
                    <form action="${pageContext.request.contextPath}/controller" method="post">
                        <input type="hidden" name="command" value="make-bid"/>
                        <input name="bid-value" class="w3-input back-back-color w3-col m3 s4" type="number" step="0.001"
                               min="${requestScope.item.actualPrice}"
                               max="99999999999999999999.999" value="${requestScope.item.actualPrice + 1}" required
                               title="${priceRule}"/>
                        <button class="w3-margin-left w3-button pro-red">
                                ${makeBid}
                        </button>
                    </form>
                </div>
            </c:if>

            <div class="w3-row-padding">

                <div class="w3-col m7">

                    <div class="w3-card w3-padding w3-margin-top w3-margin-bottom photos-container" id="photos"
                         error-message="${noPhotosForItem}"
                         item-id="${requestScope.item.id}">
                    </div>

                </div>

                <div class="w3-col m5">

                    <div class="w3-card w3-margin-top">
                        <div class="w3-container w3-padding pro-lightgrey">${prices}</div>
                        <div class="w3-container w3-padding">
                            ${itemStartPrice}:
                            <div class="text-on-color money">${requestScope.item.startPrice}</div>
                        </div>
                        <div class="w3-container w3-padding">
                            ${itemBlitzPrice}:
                            <div class="text-on-color money">${requestScope.item.blitzPrice}</div>
                        </div>
                    </div>

                    <div class="w3-card w3-margin-top">
                        <div class="w3-container w3-padding pro-lightgrey">${dates}</div>
                        <div class="w3-container w3-padding">
                            ${itemStartDate}: ${requestScope.item.startDate}
                        </div>
                        <div class="w3-container w3-padding">
                            ${itemCloseDate}: ${requestScope.item.closeDate}
                        </div>
                    </div>

                </div>

            </div>

            <div class="w3-card w3-margin">
                <div class="w3-container pro-grey capitalize w3-padding w3-leftbar">
                    ${description}
                </div>
                <div class="w3-container w3-padding">
                    ${requestScope.item.description}
                </div>
            </div>

        </div>

    </div>


</main>

<script src="${pageContext.request.contextPath}/js/load-img.js"></script>

<%@ include file="/jsp/jspf/footer.jsp" %>

<script src="${pageContext.request.contextPath}/js/event.js"></script>

<script src="${pageContext.request.contextPath}/js/slideshow.js"></script>

</body>

</html>