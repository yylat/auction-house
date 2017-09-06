<fmt:message bundle="${msg}" key="label.signIn" var="signIn"/>
<fmt:message bundle="${msg}" key="label.signUp" var="signUp"/>
<fmt:message bundle="${msg}" key="label.close" var="close"/>

<fmt:message bundle="${msg}" key="label.notifications" var="notifications"/>

<fmt:message bundle="${msg}" key="menu.menuTitle" var="menuTitle"/>
<fmt:message bundle="${msg}" key="menu.homePage" var="homePage"/>
<fmt:message bundle="${msg}" key="menu.auctionPage" var="auctionPage"/>
<fmt:message bundle="${msg}" key="menu.contactPage" var="contactPage"/>

<fmt:message bundle="${msg}" key="menu.account" var="account"/>
<fmt:message bundle="${msg}" key="menu.logOut" var="logOut"/>
<fmt:message bundle="${msg}" key="menu.balanceReplenishment" var="balanceReplenishment"/>

<fmt:message bundle="${msg}" key="menu.management" var="management"/>
<fmt:message bundle="${msg}" key="menu.balance" var="balance"/>

<fmt:message bundle="${msg}" key="label.hi" var="hi"/>


<header>
    <div class="w3-row bottom-separator back-color">

        <div class="w3-col w3-left-align s2">
            <button class="w3-button w3-xlarge w3-hide-large" id="sidebarOpenButton">
                &#9776;
            </button>
        </div>

        <div class="main-title w3-col w3-padding w3-cell-middle l10 s8">
            <div class="middle-title uppercase"><b>${projectTitle}</b></div>
        </div>

        <div class="w3-col w3-right-align s2">
            <div class="w3-dropdown-hover w3-right">
                <button class="back-color w3-button hi-title">
                    <img class="menu-button-icon"
                         src="${pageContext.request.contextPath}/img/ic_person_black_24px.svg"
                         alt=""/>
                </button>

                <div id="accountDropdownContent"
                     class="w3-dropdown-content right-dropdown-content w3-bar-block w3-border back-color">

                    <c:choose>
                        <c:when test="${sessionScope.user != null}">
                            <div class="pro-container w3-left-align pro-padding-top pro-padding-left">
                                    ${hi}, ${sessionScope.user.username}
                            </div>

                            <div class="pro-container w3-left-align w3-border-bottom pro-padding-bottom pro-padding-left">
                                    ${balance}: <span class="text-on-color"><ctg:money

                                    value="${sessionScope.user.balance}"/></span>
                            </div>

                            <a href="${pageContext.request.contextPath}/jsp/user/account.jsp"
                               class="w3-bar-item w3-button pro-hover-green w3-ripple">
                                    ${account}
                            </a>
                            <form action="${pageContext.request.contextPath}/controller">
                                <input type="hidden" name="command" value="load-notifications">
                                <button class="w3-bar-item w3-button pro-hover-green w3-ripple">${notifications}</button>
                            </form>
                            <a href="${pageContext.request.contextPath}/jsp/user/balance.jsp"
                               class="w3-bar-item w3-button pro-hover-green w3-ripple">
                                    ${balanceReplenishment}
                            </a>
                            <form action="${pageContext.request.contextPath}/controller">
                                <input type="hidden" name="command" value="log-out">
                                <button class="w3-bar-item w3-button pro-hover-green w3-ripple">${logOut}</button>
                            </form>
                        </c:when>
                        <c:otherwise>
                            <button class="w3-bar-item w3-button pro-hover-green w3-ripple" id="signInOpenButton">
                                    ${signIn}
                            </button>
                            <button class="w3-bar-item w3-button pro-hover-green w3-ripple" id="signUpOpenButton">
                                    ${signUp}
                            </button>
                        </c:otherwise>
                    </c:choose>

                </div>

            </div>
        </div>

    </div>
</header>