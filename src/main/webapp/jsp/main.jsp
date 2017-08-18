<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="/localization/message" var="msg"/>

<fmt:message bundle="${msg}" key="label.projectTitle" var="projectTitle"/>

<fmt:message bundle="${msg}" key="auction.hotBids" var="hotBids"/>

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
            <div class="w3-row-padding">

                <div class="w3-col w3-margin-top w3-center">
                    <div class="middle-title uppercase">
                        ${hotBids}
                    </div>
                </div>

            </div>

            <div class="w3-row-padding">

                <div class="w3-col w3-margin-top l6 m6">
                    <div class="w3-card item-wrapper">
                        <div class="w3-container item-back">
                            <div class="w3-container img-container">
                                <img src="/img/items/katana.jpg"/>
                            </div>
                            <div class="w3-container w3-center">
                                <div class="item-title uppercase">KATANA</div>
                            </div>
                            <div class="w3-container w3-center">
                                <div class="text-on-color money">
                                    500
                                </div>
                            </div>
                            <div class="w3-container">
                                <div class="w3-left w3-padding bids">14</div>
                                <div class="w3-right w3-padding more-link"></div>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="w3-col w3-margin-top l6 m6">
                    <div class="w3-card item-wrapper">
                        <div class="w3-container item-back">
                            <div class="w3-container img-container">
                                <img src="/img/items/book.jpg">
                            </div>
                            <div class="w3-container w3-center">
                                <div class="item-title uppercase">first harry potter book</div>
                            </div>
                            <div class="w3-container w3-center">
                                <div class="text-on-color money">
                                    380
                                </div>
                            </div>
                            <div class="w3-container">
                                <div class="w3-left w3-padding bids">14</div>
                                <div class="w3-right w3-padding more-link"></div>
                            </div>
                        </div>
                    </div>
                </div>

            </div>

            <div class="w3-row-padding">

                <div class="w3-col w3-margin-top l6 m6">
                    <div class="w3-card item-wrapper">
                        <div class="w3-container item-back">
                            <div class="w3-container img-container">
                                <img src="/img/items/coin.jpg">
                            </div>
                            <div class="w3-container w3-center">
                                <div class="item-title uppercase">ancient coin</div>
                            </div>
                            <div class="w3-container w3-center">
                                <div class="text-on-color money">
                                    120
                                </div>
                            </div>
                            <div class="w3-container">
                                <div class="w3-left w3-padding bids">12</div>
                                <div class="w3-right w3-padding more-link"></div>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="w3-col w3-margin-top l6 m6">
                    <div class="w3-card item-wrapper">
                        <div class="w3-container item-back">
                            <div class="w3-container img-container">
                                <img src="/img/items/guitar.jpg">
                            </div>
                            <div class="w3-container w3-center">
                                <div class="item-title uppercase">david bowie's guitar</div>
                            </div>
                            <div class="w3-container w3-center">
                                <div class="text-on-color money">
                                    3100
                                </div>
                            </div>
                            <div class="w3-container">
                                <div class="w3-left w3-padding bids">11</div>
                                <div class="w3-right w3-padding more-link"></div>
                            </div>
                        </div>
                    </div>
                </div>

            </div>


            <div class="w3-center w3-margin-top">
                <div class="w3-bar w3-large">
                    <a href="#" class="w3-bar-item w3-button">&laquo;</a>
                    <a href="#" class="w3-button">1</a>
                    <a href="#" class="w3-button">2</a>
                    <a href="#" class="w3-button">3</a>
                    <a href="#" class="w3-button">4</a>
                    <a href="#" class="w3-button">&raquo;</a>
                </div>
            </div>

        </div>

    </div>

</main>


<%@ include file="/jsp/jspf/footer.jsp" %>

<%@ include file="/jsp/jspf/sign-in.jsp" %>

<%@ include file="/jsp/jspf/sign-up.jsp" %>

<script src="${pageContext.request.contextPath}/js/event.js"></script>

<script src="${pageContext.request.contextPath}/js/sign.js"></script>

</body>

</html>