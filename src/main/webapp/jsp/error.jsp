<%@ page isErrorPage="true" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="/localization/message" var="msg"/>

<fmt:message bundle="${msg}" key="page.error" var="pageTitle"/>

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

    <div class="w3-main main-left-margin w3-container w3-center">

        <div class="w3-card-4 card-in-center">
            <img src="img/exception.png" class="w3-image" alt="exception"/>
            <div class="w3-container w3-justify w3-padding pro-red">
                <div class="small-title">
                    Details:
                </div>
                <c:choose>
                    <c:when test="${pageContext.exception == null}">
                        <p>
                            Sorry! Unexpected error occur.
                        </p>
                    </c:when>
                    <c:otherwise>
                        <p>${pageContext.exception}</p>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>

    </div>

</main>

<%@ include file="/jsp/jspf/footer.jsp" %>

<%@ include file="/jsp/jspf/sign_in.jsp" %>

<%@ include file="/jsp/jspf/sign_up.jsp" %>

<script src="${pageContext.request.contextPath}/js/event.js"></script>

<script src="${pageContext.request.contextPath}/js/sign.js"></script>


</body>
</html>