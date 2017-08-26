<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="/customtags" %>

<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="/localization/message" var="msg"/>

<fmt:message bundle="${msg}" key="label.projectTitle" var="projectTitle"/>

<fmt:message bundle="${msg}" key="menu.checkedItems" var="checkedItems"/>

<fmt:message bundle="${msg}" key="message.noItemsForCheck" var="noItemsForCheck"/>

<fmt:message bundle="${msg}" key="form.addButton" var="addButton"/>

<c:if test="${requestScope.items == null}">
    <jsp:forward page="${pageContext.request.contextPath}/controller">
        <jsp:param name="command" value="load-items-for-check"/>
    </jsp:forward>
</c:if>

<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${projectTitle}</title>
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

            <div class="w3-row-padding w3-margin-top">

                <div class="w3-col w3-margin-top">
                    <div class="middle-title uppercase">
                        ${checkedItems}
                    </div>
                </div>

            </div>

            <div class="w3-row-padding" id="itemsList">

                <c:choose>
                    <c:when test="${empty requestScope.items}">
                        <p>${noItemsForCheck}</p>
                    </c:when>
                    <c:otherwise>

                        <div class="w3-dropdown-click w3-right">
                            <button class="w3-button" onclick="openDropdown()">
                                <img src="${pageContext.request.contextPath}/img/ic_menu_black_24px.svg">
                            </button>
                            <div id="filterDropdownContent"
                                 class="w3-dropdown-content right-dropdown-content pro-dropdown w3-bar-block w3-border">
                                <form class="w3-container"
                                      action="${pageContext.request.contextPath}/controller" method="post">
                                    <input type="hidden" name="command" value="load-items-for-check"/>
                                    <%@include file="/jsp/jspf/filters.jsp" %>
                                </form>
                            </div>
                        </div>

                        <%@ include file="/jsp/jspf/items_list.jsp" %>

                        <div class="w3-center">
                            <div id="pageBar" class="w3-bar w3-small w3-margin-top" data-command="load-items-for-check"
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

<script>
    function openDropdown() {
        var dropdown = document.getElementById("filterDropdownContent");
        if (dropdown.className.indexOf("w3-show") == -1) {
            dropdown.className += " w3-show";
        } else {
            dropdown.className = dropdown.className.replace(" w3-show", "");
        }
    }
</script>

<script src="${pageContext.request.contextPath}/js/load-categories.js"></script>

<script src="${pageContext.request.contextPath}/js/pagination.js"></script>

<script src="${pageContext.request.contextPath}/js/load-img.js"></script>

</body>

</html>