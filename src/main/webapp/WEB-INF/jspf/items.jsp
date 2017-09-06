<fmt:message bundle="${msg}" key="form.startPrice" var="startPrice"/>
<fmt:message bundle="${msg}" key="form.blitzPrice" var="blitzPrice"/>
<fmt:message bundle="${msg}" key="form.startDate" var="startDate"/>
<fmt:message bundle="${msg}" key="form.closeDate" var="closeDate"/>
<fmt:message bundle="${msg}" key="form.category" var="category"/>
<fmt:message bundle="${msg}" key="form.itemTitle" var="itemTitle"/>
<fmt:message bundle="${msg}" key="form.itemTitleRule" var="itemTitleRule"/>
<fmt:message bundle="${msg}" key="form.search" var="search"/>

<fmt:message bundle="${msg}" key="status.title" var="statusTitle"/>
<fmt:message bundle="${msg}" key="status.created" var="created"/>
<fmt:message bundle="${msg}" key="status.confirmed" var="confirmed"/>
<fmt:message bundle="${msg}" key="status.active" var="active"/>
<fmt:message bundle="${msg}" key="status.sold" var="sold"/>
<fmt:message bundle="${msg}" key="status.canceled" var="canceled"/>
<fmt:message bundle="${msg}" key="status.ended" var="ended"/>
<fmt:message bundle="${msg}" key="status.not_confirmed" var="not_confirmed"/>

<div class="w3-row-padding w3-margin-top">

    <div class="w3-col s8 w3-margin-top">
        <div class="middle-title uppercase">
            //title-place//
        </div>
    </div>

    <div class="w3-col s4 w3-right">
        <div class="w3-dropdown-click w3-right">
            <button id="filterDropdownSwitch" class="w3-button">
                <img src="${pageContext.request.contextPath}/img/ic_sort_black_24px.svg">
            </button>
            <div id="filterDropdownContent"
                 class="w3-dropdown-content right-dropdown-content pro-dropdown w3-bar-block w3-border">
                <form class="w3-container filter-window"
                      action="${pageContext.request.contextPath}/controller" method="post">
                    <input type="hidden" name="command" value=""/>
                    <%@include file="/WEB-INF/jspf/filters.jsp" %>
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

            <form id="searchForm" action="${pageContext.request.contextPath}/controller">
                <input type="hidden" name="command" value="search-items"/>
                <div class="w3-row w3-padding w3-margin-left">
                    <div class="w3-col l5 m7 s10">
                        <input class="w3-input back-color" placeholder="${search}" name="search-name"
                               required
                               pattern="[A-Za-zА-Яа-яЁё0-9-_ ]{1,40}"
                               title="${itemTitleRule}"/>
                    </div>
                    <div class="w3-col s1">
                        <div class="w3-left pro-padding-top">
                            <button class="button-reset pro-padding-top search-button">
                                <img src="${pageContext.request.contextPath}/img/ic_search_black_18px.svg"/>
                            </button>
                        </div>
                    </div>
                </div>
            </form>

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
                                        <img src="${pageContext.request.contextPath}/img/default_img.gif"
                                             item="${item.id}"/>
                                    </div>
                                </div>
                                <div class="item-back">
                                    <div class="w3-container w3-center item-title uppercase">
                                            ${item.name}
                                    </div>

                                    <div class="w3-row">
                                        <div class="w3-col s6 w3-padding-small">
                                                ${actualPrice}: <span
                                                class="text-on-color"><ctg:money value="${item.actualPrice}"/>
                                        </span>
                                        </div>
                                        <div class="w3-col s6 w3-padding-small">
                                                ${startDate}: <span>${item.startDate}</span>
                                        </div>
                                    </div>

                                    <div class="w3-row">
                                        <div class="w3-col s6 w3-padding-small">
                                                ${blitzPrice}: <span
                                                class="text-on-color"><ctg:money value="${item.blitzPrice}"/>
                                                </span>
                                        </div>
                                        <div class="w3-col s6 w3-padding-small">
                                                ${closeDate}: <span>${item.closeDate}</span>
                                        </div>
                                    </div>

                                    <div class="w3-container w3-padding w3-small w3-left-align pro-back">
                                            ${pageScope[item.status.toString().toLowerCase()]}
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
                     data-page="${requestScope.page}" data-pages="${sessionScope.pages}">
                    <a id="prevLink" class="w3-button">&laquo;</a>

                    <a id="nextLink" class="w3-button">&raquo;</a>
                </div>
            </div>

        </c:otherwise>
    </c:choose>

</div>

<script src="${pageContext.request.contextPath}/js/filter-dropdown.js"></script>
<script src="${pageContext.request.contextPath}/js/photo.loader.js"></script>
<script src="${pageContext.request.contextPath}/js/pagination.js"></script>
<script src="${pageContext.request.contextPath}/js/controller/search.controller.js"></script>