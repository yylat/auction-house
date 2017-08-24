<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="customtags" %>

<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="/localization/message" var="msg"/>

<fmt:message bundle="${msg}" key="label.projectTitle" var="projectTitle"/>

<fmt:message bundle="${msg}" key="menu.balanceReplenishment" var="balanceReplenishment"/>

<fmt:message bundle="${msg}" key="balance.cardNumber" var="cardNumber"/>
<fmt:message bundle="${msg}" key="balance.cvc" var="cvc"/>
<fmt:message bundle="${msg}" key="balance.nameOnCard" var="nameOnCard"/>
<fmt:message bundle="${msg}" key="balance.moneyAmount" var="moneyAmount"/>
<fmt:message bundle="${msg}" key="balance.replenish" var="replenish"/>
<fmt:message bundle="${msg}" key="form.priceRule" var="priceRule"/>

<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${projectTitle}|${balanceReplenishment}</title>
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

            <div class="w3-container w3-center">
                <div class="w3-card w3-center">
                    <div class="w3-container w3-margin middle-title uppercase w3-border-bottom">
                        <b>${balanceReplenishment}</b>
                    </div>

                    <form class="w3-container bottom-padding" action="${pageContext.request.contextPath}/controller"
                          method="post">
                        <input type="hidden" name="command" value="balance-replenishment"/>

                        <div class="w3-section">

                            <div class="w3-container bottom-padding">
                                <label><b>${cardNumber}</b></label>
                                <input class="w3-input" placeholder="${cardNumber}" name="cardNumber"
                                       pattern="[0-9]{12}"/>
                            </div>

                            <div class="w3-container bottom-padding">
                                <label><b>${nameOnCard}</b></label>
                                <input class="w3-input" placeholder="${nameOnCard}" name="nameOnCard"
                                       pattern="[A-Za-zА-Яа-яЁё ]{2,40}"/>
                            </div>

                            <div class="w3-cell">
                                <div class="w3-container bottom-padding">
                                    <label><b>${cvc}</b></label>
                                    <input class="w3-input" placeholder="${cvc}" name="cvc"
                                           pattern="[0-9]{3}"/>
                                </div>
                            </div>

                            <div class="w3-cell">
                                <div class="w3-container bottom-padding">
                                    <label><b>${moneyAmount}</b></label>
                                    <input name="moneyAmount" class="w3-input" type="number" step="0.001"
                                           min="0.000"
                                           max="99999999999999999999.999" required
                                           title="${priceRule}"/>
                                </div>
                            </div>

                            <div class="w3-container">
                                <button class="pro-button pro-green-black pro-green-border w3-block w3-padding">
                                    ${replenish}
                                </button>
                            </div>

                        </div>

                    </form>

                </div>
            </div>

        </div>

    </div>


</main>

<%@ include file="/jsp/jspf/message.jsp" %>

<%@ include file="/jsp/jspf/footer.jsp" %>

</body>

</html>