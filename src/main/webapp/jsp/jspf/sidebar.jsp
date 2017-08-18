<div class="w3-sidebar w3-bar-block w3-collapse w3-animate-opacity sidebar right-separator" id="sidebar">

    <button class="w3-bar-item w3-button w3-large w3-hide-large" id="sidebarCloseButton">
        ${close} &times;
    </button>

    <div class="w3-margin-left w3-margin-top w3-margin-bottom">
        <div class="small-title uppercase">${menuTitle}</div>
    </div>

    <a href="${pageContext.request.contextPath}/controller?command=open-main-page"
       class="w3-bar-item pro-bottom-button">${homePage}</a>
    <a href="#" class="w3-bar-item pro-bottom-button">${auctionPage}</a>
    <a href="#" class="w3-bar-item pro-bottom-button">${contactPage}</a>

    <div id="langSwitch" class="w3-bar-item lang-button w3-margin-bottom">
        <c:choose>
            <c:when test="${ sessionScope.locale.language eq 'en' }">
                <a class="w3-row w3-medium a-button"
                   href="${pageContext.request.contextPath}/controller?command=change_locale&lang=ru">
                    <div class="w3-col">
                        <div class="w3-col s6 pro-switch-grey-r pro-green">
                            ru
                        </div>
                        <div class="w3-col s6 pro-switch-green-r pro-lightgrey">
                            en
                        </div>
                    </div>
                </a>
            </c:when>
            <c:otherwise>
                <a class="w3-row w3-medium a-button"
                   href="${pageContext.request.contextPath}/controller?command=change_locale&lang=en">
                    <div class="w3-col">
                        <div class="w3-col s6 pro-switch-green pro-lightgrey">
                            ru
                        </div>
                        <div class="w3-col s6 pro-switch-grey pro-green">
                            en
                        </div>
                    </div>
                </a>
            </c:otherwise>
        </c:choose>
    </div>

</div>