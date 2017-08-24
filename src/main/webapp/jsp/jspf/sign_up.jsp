<fmt:message bundle="${msg}" key="label.signUp" var="signUp"/>

<fmt:message bundle="${msg}" key="form.signUpTitle" var="signUpTitle"/>
<fmt:message bundle="${msg}" key="form.clearFields" var="clearFields"/>
<fmt:message bundle="${msg}" key="form.username" var="username"/>
<fmt:message bundle="${msg}" key="form.password" var="password"/>
<fmt:message bundle="${msg}" key="form.repeatPassword" var="repeatPassword"/>
<fmt:message bundle="${msg}" key="form.lastName" var="lastName"/>
<fmt:message bundle="${msg}" key="form.firstName" var="firstName"/>
<fmt:message bundle="${msg}" key="form.middleName" var="middleName"/>
<fmt:message bundle="${msg}" key="form.email" var="email"/>
<fmt:message bundle="${msg}" key="form.phoneNumber" var="phoneNumber"/>
<fmt:message bundle="${msg}" key="form.usernameRule" var="usernameRule"/>
<fmt:message bundle="${msg}" key="form.passwordRule" var="passwordRule"/>
<fmt:message bundle="${msg}" key="form.nameRule" var="nameRule"/>
<fmt:message bundle="${msg}" key="form.emailRule" var="emailRule"/>
<fmt:message bundle="${msg}" key="form.phoneNumberRule" var="phoneNumberRule"/>

<fmt:message bundle="${msg}" key="message.successfulRegistration" var="successfulRegistration"/>
<fmt:message bundle="${msg}" key="error.usernameAlreadyExist" var="usernameAlreadyExist"/>
<fmt:message bundle="${msg}" key="error.emailAlreadyExist" var="emailAlreadyExist"/>

<div id="signUpModal" class="w3-modal pro-modal">
    <div class="w3-modal-content w3-card-4 w3-animate-zoom modal-content">

        <div class="w3-center w3-padding-16 pro-green capitalize">
            <span id="signUpCloseButton"
                  class="w3-button w3-large w3-hover-red w3-display-topright" title="close sign in form">
                &times;
            </span>
            <div class="middle-title">${signUpTitle}</div>
        </div>

        <form id="signUpForm" class="w3-container bottom-padding" action="${pageContext.request.contextPath}/controller"
              method="post">
            <input type="hidden" name="command" value="sign_up">

            <div class="w3-container w3-padding w3-right">
                <button type="reset" class="reset-button">${clearFields}</button>
            </div>

            <c:choose>
                <c:when test="${sessionScope.usernameAlreadyExist == true}">
                    <div class="w3-container error-message">${usernameAlreadyExist}</div>
                </c:when>
                <c:when test="${sessionScope.emailAlreadyExist == true}">
                    <div class="w3-container error-message">${emailAlreadyExist}</div>
                </c:when>
            </c:choose>

            <div class="w3-section w3-margin-bottom">
                <div class="w3-container bottom-padding">
                    <label><b>${username}</b></label>
                    <input class="w3-input" placeholder="${username} *" name="username" required
                           pattern="[A-Za-z][A-Za-z0-9.//-]{5,20}"
                           title="${usernameRule}"/>
                </div>

                <div class="w3-row-padding">
                    <div class="w3-col bottom-padding m6">
                        <label><b>${password}</b></label>
                        <input id="password" class="w3-input" type="password"
                               placeholder="${password} *"
                               name="password" required
                               pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{6,30}"
                               title="${passwordRule}"/>
                    </div>
                    <div class="w3-col bottom-padding m6">
                        <label><b>${repeatPassword}</b></label>
                        <input id="repeated-password" class="w3-input" type="password"
                               placeholder="${repeatPassword} *"
                               name="repeatedPassword" required
                               pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{6,30}"
                               title="${passwordRule}"/>
                    </div>
                </div>
            </div>


            <div class="w3-section w3-margin-bottom">
                <div class="w3-container bottom-padding">
                    <label><b>${lastName}</b></label>
                    <input class="w3-input" placeholder="${lastName} *" name="lastName" required
                           pattern="[A-Za-z]{2,45}"
                           title="${nameRule}"/>
                </div>

                <div class="w3-row-padding">
                    <div class="w3-col bottom-padding m6">
                        <label><b>${firstName}</b></label>
                        <input class="w3-input" placeholder="${firstName} *" name="firstName"
                               required
                               pattern="[A-Za-z]{2,45}"
                               title="${nameRule}"/>
                    </div>
                    <div class="w3-col bottom-padding m6">
                        <label><b>${middleName}</b></label>
                        <input class="w3-input" placeholder="${middleName}" name="middleName"
                               pattern="[A-Za-z]{2,45}"
                               title="${nameRule}"/>
                    </div>
                </div>
            </div>

            <div class="w3-section">
                <div class="w3-container bottom-padding">
                    <label><b>${email}</b></label>
                    <input class="w3-input" placeholder="${email} *" name="email" required
                           pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,3}$"
                           title="${emailRule}"/>
                </div>
                <div class="w3-container">
                    <label><b>${phoneNumber}</b></label>
                    <input class="w3-input" placeholder="${phoneNumber} *" name="phoneNumber"
                           required
                           pattern="[+][0-9]{11,12}"
                           title="${phoneNumberRule}"/>
                </div>
            </div>

            <div class="w3-container">
                <button id="signUpSubmitButton"
                        class="pro-button pro-green-black pro-green-border w3-block w3-padding">
                    ${signUp}
                </button>
            </div>

        </form>

    </div>
</div>

<%@ include file="/jsp/jspf/message.jsp" %>

<c:if test="${(sessionScope.usernameAlreadyExist == true) || (sessionScope.emailAlreadyExist == true)}">
    <script>
        document.getElementById("signUpModal").style.display = "block";
    </script>
</c:if>