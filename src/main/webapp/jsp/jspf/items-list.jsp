<c:forEach var="item" items="${requestScope.items}" varStatus="status">

    <c:if test="${status.count % 2 != 0}">
        <div class="w3-row-padding">
    </c:if>

    <form action="${pageContext.request.contextPath}/controller">
        <input type="hidden" name="command" value="load-item"/>
        <input type="hidden" name="item-id" value="${item.id}"/>
        <button class="button-reset">
            <div class="w3-col w3-margin-top m6">
                <div class="w3-card item-wrapper">
                    <div class="w3-container item-back">
                        <div class="w3-container img-container">
                            <img src="" item="${item.id}"/>
                        </div>
                        <div class="w3-container w3-center uppercase">
                                ${item.name}
                        </div>
                        <div class="w3-container w3-center">
                            <div class="text-on-color money">
                                    ${item.actualPrice}
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </button>
    </form>

    <c:if test="${(status.count % 2 == 0) || (status.last)}">
        </div>
    </c:if>

</c:forEach>