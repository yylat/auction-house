<fmt:message bundle="${msg}" key="confirm.message" var="message"/>
<fmt:message bundle="${msg}" key="confirm.yes" var="yes"/>
<fmt:message bundle="${msg}" key="confirm.no" var="no"/>

<div id="confirmModal" class="w3-modal">
    <div class="w3-modal-content w3-card-4 w3-animate-zoom modal-content">
        <div class="w3-center w3-padding-16 pro-purple">
            <span id="closeConfirm" class="w3-button w3-display-topright">&times;</span>
            <p>${message}</p>
            <div class="w3-row-padding">
                <div class="w3-col s6">
                    <button class="w3-button" id="yesButton">
                        ${yes}
                    </button>
                </div>
                <div class="w3-col s6">
                    <button class="w3-button" id="noButton">
                        ${no}
                    </button>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="${pageContext.request.contextPath}/js/controller/confirm.controller.js"></script>