<c:if test="${(sessionScope.message != null) && (!sessionScope.wasShown)}">
    <c:set var="wasShown" value="true" scope="session"/>
    <div id="messageModal" class="w3-modal open-modal">
        <div class="w3-modal-content w3-card-4 w3-animate-zoom modal-content">
            <div class="w3-center w3-padding-16 pro-purple">
            <span onclick="document.getElementById('messageModal').style.display='none'"
                  class="w3-button w3-display-topright">&times;</span>
                <p>${sessionScope.message}</p>
            </div>
        </div>
    </div>
</c:if>
