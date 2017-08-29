<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="/customtags" %>

<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="/localization/message" var="msg"/>

<fmt:message bundle="${msg}" key="label.projectTitle" var="projectTitle"/>
<fmt:message bundle="${msg}" key="page.account" var="account"/>

<fmt:message bundle="${msg}" key="form.usernameRule" var="usernameRule"/>
<fmt:message bundle="${msg}" key="account.changeUsername" var="changeUsername"/>

<fmt:message bundle="${msg}" key="error.usernameAlreadyExist" var="usernameAlreadyExist"/>
<fmt:message bundle="${msg}" key="error.emailAlreadyExist" var="emailAlreadyExist"/>

<fmt:message bundle="${msg}" key="account.changeEmail" var="changeEmail"/>
<fmt:message bundle="${msg}" key="form.emailRule" var="emailRule"/>

<fmt:message bundle="${msg}" key="account.oldPassword" var="oldPassword"/>
<fmt:message bundle="${msg}" key="account.newPassword" var="newPassword"/>
<fmt:message bundle="${msg}" key="account.repeatNewPassword" var="repeatNewPassword"/>
<fmt:message bundle="${msg}" key="account.changePassword" var="changePassword"/>
<fmt:message bundle="${msg}" key="form.passwordRule" var="passwordRule"/>
<fmt:message bundle="${msg}" key="error.repeatedPassword" var="repeatedPasswordError"/>
<fmt:message bundle="${msg}" key="error.wrongPassword" var="wrongPassword"/>

<fmt:message bundle="${msg}" key="account.updateProfile" var="updateProfile"/>
<fmt:message bundle="${msg}" key="form.nameRule" var="nameRule"/>
<fmt:message bundle="${msg}" key="form.phoneNumberRule" var="phoneNumberRule"/>
<fmt:message bundle="${msg}" key="form.lastName" var="lastName"/>
<fmt:message bundle="${msg}" key="form.firstName" var="firstName"/>
<fmt:message bundle="${msg}" key="form.middleName" var="middleName"/>
<fmt:message bundle="${msg}" key="form.phoneNumber" var="phoneNumber"/>

<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${account}</title>
    <link rel="icon" href="${pageContext.request.contextPath}/img/ic_gavel_black.png">
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

            <div class="w3-container w3-margin-top w3-margin-bottom middle-title uppercase">
                ${account}
            </div>

            <div class="w3-row-padding w3-margin-top w3-margin-bottom w3-border-bottom">
                <div class="w3-col m6">
                    <form action="${pageContext.request.contextPath}/controller">
                        <input type="hidden" name="command" value="change-username"/>
                        <div class="capitalize w3-margin-bottom"><b>${changeUsername}</b></div>

                        <input class="w3-input w3-margin-bottom" value="${sessionScope.user.username}" name="username"
                               required
                               pattern="[A-Za-z][A-Za-z0-9.//-]{5,20}"
                               title="${usernameRule}"/>
                        <button class="w3-button pro-green w3-margin-bottom">${changeUsername}</button>
                        <c:if test="${sessionScope.usernameAlreadyExist == true}">
                            <div class="w3-container w3-margin-bottom error-message">${usernameAlreadyExist}</div>
                        </c:if>
                    </form>
                </div>
                <div class="w3-col m6">
                    <form action="${pageContext.request.contextPath}/controller">
                        <input type="hidden" name="command" value="change-email"/>
                        <div class="capitalize w3-margin-bottom"><b>${changeEmail}</b></div>

                        <input class="w3-input w3-margin-bottom" value="${sessionScope.user.email}" name="email"
                               required
                               pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,3}$"
                               title="${emailRule}"/>
                        <button class="w3-button pro-green w3-margin-bottom">${changeEmail}</button>
                        <c:if test="${sessionScope.emailAlreadyExist == true}">
                            <div class="w3-container w3-margin-bottom error-message">${emailAlreadyExist}</div>
                        </c:if>
                    </form>
                </div>
            </div>

            <div class="w3-container w3-margin-top w3-margin-bottom w3-border-bottom">
                <form action="${pageContext.request.contextPath}/controller">
                    <input type="hidden" name="command" value="change-password"/>
                    <div class="capitalize w3-margin-bottom"><b>${changePassword}</b></div>

                    <label>${oldPassword}</label>
                    <div class="w3-row-padding w3-margin-bottom">
                        <div class="w3-col m6">
                            <input class="w3-input" type="password"
                                   placeholder="${oldPassword}"
                                   name="old-password" required
                                   pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{6,30}"
                                   title="${passwordRule}"/>
                        </div>
                    </div>

                    <label>${newPassword}</label>
                    <div class="w3-row-padding">
                        <div class="w3-col m6">
                            <input id="password" class="w3-input" type="password"
                                   placeholder="${newPassword}"
                                   name="new-password" required
                                   pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{6,30}"
                                   title="${passwordRule}"/>
                        </div>
                        <div class="w3-col m6">
                            <input id="repeated-password" class="w3-input" type="password"
                                   placeholder="${repeatNewPassword}"
                                   name="repeated-password" required
                                   pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{6,30}"
                                   data-repeated-error="${repeatedPasswordError}"
                                   title="${passwordRule}"/>
                        </div>
                    </div>

                    <button class="w3-button pro-green w3-margin-bottom w3-margin-top">${changePassword}</button>
                    <c:if test="${sessionScope.wrongPassword == true}">
                        <div class="w3-container w3-margin-bottom error-message">${wrongPassword}</div>
                    </c:if>
                </form>
            </div>

            <div class="w3-container w3-margin-top w3-margin-bottom">
                <form action="${pageContext.request.contextPath}/controller">
                    <input type="hidden" name="command" value="update-profile"/>
                    <div class="capitalize w3-margin-bottom"><b>${updateProfile}</b></div>

                    <label>${lastName}</label>
                    <div class="w3-row-padding w3-margin-bottom">
                        <div class="w3-col m6">
                            <input class="w3-input" value="${sessionScope.user.lastName}" name="lastName"
                                   required pattern="[A-Za-zА-Яа-яЁё]{2,45}"
                                   title="${nameRule}"/>
                        </div>
                    </div>

                    <label>${firstName}</label>
                    <div class="w3-row-padding w3-margin-bottom">
                        <div class="w3-col m6">
                            <input class="w3-input" value="${sessionScope.user.firstName}" name="firstName"
                                   required pattern="[A-Za-zА-Яа-яЁё]{2,45}"
                                   title="${nameRule}"/>
                        </div>
                    </div>

                    <label>${middleName}</label>
                    <div class="w3-row-padding w3-margin-bottom">
                        <div class="w3-col m6">
                            <input class="w3-input" value="${sessionScope.user.middleName}" name="middleName"
                                   pattern="[A-Za-zА-Яа-яЁё]{2,45}"
                                   title="${nameRule}"/>
                        </div>
                    </div>

                    <label>${phoneNumber}</label>
                    <div class="w3-row-padding w3-margin-bottom">
                        <div class="w3-col m6">
                            <input class="w3-input" value="${sessionScope.user.phoneNumber}" name="phoneNumber"
                                   required pattern="[+][0-9]{11,12}"
                                   title="${phoneNumberRule}"/>
                        </div>
                    </div>
                    <button class="w3-button pro-green w3-margin-bottom w3-margin-top">${updateProfile}</button>
                </form>
            </div>

        </div>

    </div>


</main>

<%@ include file="/jsp/jspf/message.jsp" %>

<%@ include file="/jsp/jspf/footer.jsp" %>

<script src="${pageContext.request.contextPath}/js/repeat-password.js"></script>

</body>

</html>