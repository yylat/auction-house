<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="/customtags" %>

<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="/localization/message" var="msg"/>

<fmt:message bundle="${msg}" key="label.projectTitle" var="projectTitle"/>

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
                        <b>sellerContactInfo</b>
                    </div>
                </div>

                <div class="w3-container w3-margin">
                    <div class="w3-center small-title">
                        <span class="capitalize">fullName:</span> ${requestScope.profile.lastName} ${requestScope.profile.middleName} ${requestScope.profile.firstName}
                    </div>
                </div>

                <div class="w3-container w3-margin">
                    <div class="w3-center small-title">
                        <span class="capitalize">phone:</span> ${requestScope.profile.phoneNumber}
                    </div>
                </div>

                <div class="w3-container w3-margin">
                    <div class="w3-center small-title">
                        <span class="capitalize">email:</span> ${requestScope.profile.email}
                    </div>
                </div>
            </div>
        </div>

    </div>

</main>

<%@ include file="/WEB-INF/jspf/footer.jsp" %>

<c:set scope="session" var="currentPage" value="/WEB-INF/jsp/user/profile.jsp"/>

</body>

</html>