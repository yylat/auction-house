<%@ include file="/jsp/jspf/header.jsp" %>

<main>

    <%@ include file="/jsp/jspf/sidebar.jsp" %>

    <div class="w3-main main-left-margin">

        <div class="content">
            <div class="w3-row-padding w3-margin-top">

                <div class="w3-col s8 w3-margin-top">
                    <div class="middle-title uppercase">
                        //title-place//
                    </div>
                </div>

                <div class="w3-col s4 w3-right">
                    <div class="w3-dropdown-click w3-right">
                        <button id="filterDropdownSwitch" class="w3-button">
                            <img src="${pageContext.request.contextPath}/img/ic_menu_black_24px.svg">
                        </button>
                        <div id="filterDropdownContent"
                             class="w3-dropdown-content right-dropdown-content pro-dropdown w3-bar-block w3-border">
                            <form class="w3-container"
                                  action="${pageContext.request.contextPath}/controller" method="post">
                                <input type="hidden" name="command" value=""/>
                                <%@include file="/jsp/jspf/filters.jsp" %>
                            </form>
                        </div>
                    </div>
                </div>


            </div>

            <div class="w3-row-padding" id="itemsList">

                <c:choose>
                    <c:when test="${empty requestScope.items}">
                        <p>${noItemsYet}</p>
                    </c:when>
                    <c:otherwise>

                        <div class="pro-margin-bottom">
                            <c:forEach var="item" items="${requestScope.items}" varStatus="status">

                                <c:if test="${status.count % 2 != 0}">
                                    <div class="w3-row-padding">
                                </c:if>

                                <form class="w3-col w3-margin-top m6"
                                      action="${pageContext.request.contextPath}/controller">
                                    <input type="hidden" name="command" value="load-item"/>
                                    <input type="hidden" name="itemId" value="${item.id}"/>
                                    <button class="button-reset">
                                        <div class="w3-card item-wrapper">
                                            <div class="w3-container item-back">
                                                <div class="w3-container img-container">
                                                    <img src="" item="${item.id}"/>
                                                </div>
                                                <div class="w3-container w3-center item-title uppercase">
                                                        ${item.name}
                                                </div>
                                                <div class="w3-container w3-center">
                                                    <div class="text-on-color money">
                                                            ${item.actualPrice}
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
                        </div>

                        <div class="page-bar w3-center">
                            <div id="pageBar" class="w3-bar w3-small w3-margin-top" data-command=""
                                 data-page="${requestScope.page}" data-pages="${requestScope.pages}">
                                <a id="prevLink" class="w3-button">&laquo;</a>

                                <a id="nextLink" class="w3-button">&raquo;</a>
                            </div>
                        </div>

                    </c:otherwise>
                </c:choose>

            </div>
        </div>

    </div>


</main>

<%@ include file="/jsp/jspf/footer.jsp" %>

<script src="${pageContext.request.contextPath}/js/load-categories.js"></script>

<script src="${pageContext.request.contextPath}/js/filter-dropdown.js"></script>

<script src="${pageContext.request.contextPath}/js/load-img.js"></script>

<script src="${pageContext.request.contextPath}/js/pagination.js"></script>