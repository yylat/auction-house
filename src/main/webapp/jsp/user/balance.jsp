<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="/customtags" %>

<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="/localization/message" var="msg"/>

<fmt:message bundle="${msg}" key="menu.balanceReplenishment" var="balanceReplenishment"/>

<fmt:message bundle="${msg}" key="balance.cardNumber" var="cardNumber"/>
<fmt:message bundle="${msg}" key="balance.cvc" var="cvc"/>
<fmt:message bundle="${msg}" key="balance.nameOnCard" var="nameOnCard"/>
<fmt:message bundle="${msg}" key="balance.moneyAmount" var="moneyAmount"/>
<fmt:message bundle="${msg}" key="balance.replenish" var="replenish"/>
<fmt:message bundle="${msg}" key="form.priceRule" var="priceRule"/>

<html>

<%@ include file="/WEB-INF/jspf/head.jsp" %>

<body>

<%@ include file="/WEB-INF/jspf/header.jsp" %>

<main>

    <%@ include file="/WEB-INF/jspf/sidebar.jsp" %>

    <div class="w3-main main-left-margin">

        <div class="content">

            <div class="w3-container w3-center">
                <div class="w3-card w3-center card-in-center">
                    <div class="w3-container w3-margin w3-padding middle-title uppercase w3-border-bottom">
                        <b>${balanceReplenishment}</b>
                    </div>

                    <form id="balanceForm" class="w3-container bottom-padding"
                          action="${pageContext.request.contextPath}/controller" method="post">
                        <input type="hidden" name="command" value="replenish-balance"/>

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
                                    <input name="moneyAmount" class="w3-input" type="number" step="1"
                                           min="1" max="99999999999999999999" required
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

<%@ include file="/WEB-INF/jspf/message.jsp" %>
<%@ include file="/WEB-INF/jspf/footer.jsp" %>

<script src="${pageContext.request.contextPath}/js/controller/balance.controller.js"></script>

<c:set scope="session" var="currentPage" value="/jsp/user/balance.jsp"/>

</body>

</html>