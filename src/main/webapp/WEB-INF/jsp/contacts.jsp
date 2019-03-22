<%@ page isErrorPage="true" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="/localization/message" var="msg"/>

<fmt:message bundle="${msg}" key="label.contacts" var="contacts"/>
<fmt:message bundle="${msg}" key="label.projectTitle" var="projectTitle"/>
<fmt:message bundle="${msg}" key="label.ourEmail" var="ourEmail"/>
<fmt:message bundle="${msg}" key="label.ourPhone" var="ourPhone"/>
<fmt:message bundle="${msg}" key="label.gitHub" var="gitHub"/>


<html>

<%@ include file="/WEB-INF/jspf/head.jsp" %>

<body>

<%@ include file="/WEB-INF/jspf/header.jsp" %>

<main>
    <%@ include file="/WEB-INF/jspf/sidebar.jsp" %>

    <div class="w3-main main-left-margin">
        <div class="content">


            <div class="big-margin-top">

                <div class="w3-container w3-margin middle-title uppercase">
                    <div class="w3-center">
                        <b>${contacts}</b>
                    </div>
                </div>

                <div class="w3-container w3-margin">
                    <div class="w3-center small-title">
                        <span class="capitalize">${ourEmail}:</span> auction.house.web@gmail.com
                    </div>
                </div>

                <div class="w3-container w3-margin">
                    <div class="w3-center small-title">
                        <span class="capitalize">${ourPhone}:</span> +375-29-711-34-47
                    </div>
                </div>

                <div class="w3-container w3-margin">
                    <div class="w3-center small-title">
                        <span class="capitalize">${gitHub}:</span> <a href="https://github.com/lool21/AuctionHouse">AuctionHouse</a>
                    </div>
                </div>
            </div>
        </div>

    </div>

</main>

<%@ include file="/WEB-INF/jspf/footer.jsp" %>

<%@ include file="/WEB-INF/jspf/sign_in.jsp" %>

<%@ include file="/WEB-INF/jspf/sign_up.jsp" %>

<script src="${pageContext.request.contextPath}/js/controller/sign.controller.js"></script>

<c:set scope="session" var="currentPage" value="/WEB-INF/jsp/contacts.jsp"/>

</body>
</html>