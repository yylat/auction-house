<%@ page isErrorPage="true" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="/localization/message" var="msg"/>


<html>

<%@ include file="/WEB-INF/jspf/head.jsp" %>

<body>

<%@ include file="/WEB-INF/jspf/header.jsp" %>

<main>
    <%@ include file="/WEB-INF/jspf/sidebar.jsp" %>

    <div class="w3-main main-left-margin w3-container w3-center">

        <div class="w3-card-4 card-in-center">
            <img src="${pageContext.request.contextPath}img/exception.png" class="w3-image" alt="exception"/>
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

<%@ include file="/WEB-INF/jspf/footer.jsp" %>

<%@ include file="/WEB-INF/jspf/sign_in.jsp" %>

<%@ include file="/WEB-INF/jspf/sign_up.jsp" %>

<script src="${pageContext.request.contextPath}/js/controller/sign.controller.js"></script>


</body>
</html>