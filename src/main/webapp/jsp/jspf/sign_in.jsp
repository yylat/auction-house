<fmt:message bundle="${msg}" key="label.signIn" var="signIn"/>

<fmt:message bundle="${msg}" key="form.signInTitle" var="signInTitle"/>
<fmt:message bundle="${msg}" key="form.clearFields" var="clearFields"/>
<fmt:message bundle="${msg}" key="form.username" var="username"/>
<fmt:message bundle="${msg}" key="form.password" var="password"/>
<fmt:message bundle="${msg}" key="form.usernameRule" var="usernameRule"/>
<fmt:message bundle="${msg}" key="form.passwordRule" var="passwordRule"/>

<fmt:message bundle="${msg}" key="error.wrongUsernamePassword" var="wrongUsernamePassword"/>

<div id="signInModal" class="w3-modal pro-modal">
    <div class="w3-modal-content w3-card-4 w3-animate-zoom modal-content">

        <div class="w3-center w3-padding-16 pro-green capitalize">
            <span id="signInCloseButton"
                  class="w3-button w3-large w3-hover-red w3-display-topright" title="close sign in form">
                &times;
            </span>
            <div class="middle-title">${signInTitle}</div>
        </div>

        <form id="signInForm" class="w3-container bottom-padding" action="${pageContext.request.contextPath}/controller" method="post">
            <input type="hidden" name="command" value="sign-in"/>

            <div class="w3-section">
                <div class="w3-container w3-right">
                    <button type="reset" class="reset-button">${clearFields}</button>
                </div>

                <c:if test="${requestScope.wrongUsernamePassword == true}">
                    <div class="w3-container error-message">${wrongUsernamePassword}</div>
                </c:if>

                <div class="w3-container bottom-padding">
                    <label><b>${username}</b></label>
                    <input class="w3-input" placeholder="${username} *" name="username" required
                           title="${usernameRule}"/>
                </div>

                <div class="w3-container bottom-padding">
                    <label><b>${password}</b></label>
                    <input class="w3-input" type="password" placeholder="${password} *" name="password" required
                           title="${passwordRule}"/>
                </div>

                <div class="w3-container">
                    <button id="signInSubmitButton"
                            class="pro-button pro-green-black pro-green-border w3-block w3-padding">
                        ${signIn}
                    </button>
                </div>

            </div>

        </form>

    </div>
</div>

<c:if test="${requestScope.wrongUsernamePassword == true}">
    <script>
        document.getElementById("signInModal").style.display = "block";
    </script>
</c:if>