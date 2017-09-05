<fmt:message bundle="${msg}" key="form.addItemTitle" var="addItemTitle"/>
<fmt:message bundle="${msg}" key="form.clearFields" var="clearFields"/>
<fmt:message bundle="${msg}" key="form.itemTitle" var="itemTitle"/>
<fmt:message bundle="${msg}" key="form.itemTitleRule" var="itemTitleRule"/>
<fmt:message bundle="${msg}" key="form.description" var="description"/>
<fmt:message bundle="${msg}" key="form.descriptionRule" var="descriptionRule"/>
<fmt:message bundle="${msg}" key="form.startPrice" var="startPrice"/>
<fmt:message bundle="${msg}" key="form.blitzPrice" var="blitzPrice"/>
<fmt:message bundle="${msg}" key="form.priceRule" var="priceRule"/>
<fmt:message bundle="${msg}" key="form.startDate" var="startDate"/>
<fmt:message bundle="${msg}" key="form.startDateRule" var="startDateRule"/>
<fmt:message bundle="${msg}" key="form.closeDate" var="closeDate"/>
<fmt:message bundle="${msg}" key="form.closeDateRule" var="closeDateRule"/>
<fmt:message bundle="${msg}" key="form.category" var="category"/>
<fmt:message bundle="${msg}" key="form.chooseCategory" var="chooseCategory"/>
<fmt:message bundle="${msg}" key="form.photos" var="photos"/>
<fmt:message bundle="${msg}" key="form.messageFilesSelected" var="messageFilesSelected"/>
<fmt:message bundle="${msg}" key="form.photosRule" var="photosRule"/>
<fmt:message bundle="${msg}" key="form.photosSizeRule" var="photosSizeRule"/>
<fmt:message bundle="${msg}" key="form.chooseFiles" var="chooseFiles"/>
<fmt:message bundle="${msg}" key="form.addButton" var="addButton"/>
<fmt:message bundle="${msg}" key="form.blitzPriceRule" var="blitzPriceRule"/>
<fmt:message bundle="${msg}" key="form.categoryRule" var="categoryRule"/>

<div id="addItemModal" class="w3-modal pro-modal">
    <div class="w3-modal-content w3-card-4 w3-animate-zoom modal-content">


        <div class="w3-center w3-padding-16 pro-green capitalize">
            <span id="addItemCloseButton"
                  class="w3-button w3-large w3-hover-red w3-display-topright" title="close sign in form">
                &times;
            </span>
            <div class="middle-title">${addItemTitle}</div>
        </div>

        <form id="addItemForm" class="w3-container bottom-padding" action="${pageContext.request.contextPath}/upload"
              method="post" enctype="multipart/form-data">
            <input type="hidden" name="command" value="create-item"/>

            <div class="w3-section">
                <div class="w3-container w3-right">
                    <button type="reset" class="reset-button">${clearFields}</button>
                </div>

                <div class="w3-section margin-bottom">
                    <div class="w3-container bottom-padding">
                        <label><b>${itemTitle}</b></label>
                        <input class="w3-input" placeholder="${itemTitle}" name="title" required
                               pattern="[A-Za-zА-Яа-яЁё0-9-_ ]{2,40}"
                               title="${itemTitleRule}"/>
                    </div>

                    <div class="w3-container bottom-padding">
                        <label><b>${description}</b></label>
                        <textarea id="descriptionTextarea" class="w3-input w3-border" name="description"
                                  required title="${descriptionRule}"></textarea>
                    </div>
                </div>

                <div class="w3-section margin-bottom">
                    <div class="w3-row-padding">
                        <div class="w3-col m6">
                            <label><b>${startPrice}</b></label>
                            <input id="startPrice" name="start-price" class="w3-input" type="number" step="1"
                                   min="1" max="99999999999999999999" required
                                   title="${priceRule}"/>
                        </div>
                        <div class="w3-col m6">
                            <label><b>${blitzPrice}</b></label>
                            <input id="blitzPrice" name="blitz-price" class="w3-input" type="number" step="1"
                                   min="1" max="99999999999999999999" required
                                   title="${priceRule}" data-blitz-rule="${blitzPriceRule}"/>
                        </div>
                    </div>
                </div>

                <div class="w3-section margin-bottom">
                    <div class="w3-row-padding">
                        <div class="w3-col m6">
                            <label><b>${startDate}</b></label>
                            <input id="start-date" name="start-date" class="w3-input" type="date" required
                                   title="${startDateRule}"/>
                        </div>
                        <div class="w3-col m6">
                            <label><b>${closeDate}</b></label>
                            <input id="close-date" name="close-date" class="w3-input" type="date" required
                                   title="${closeDateRule}"/>
                        </div>
                    </div>
                </div>

                <div class="w3-section margin-bottom">
                    <div class="w3-container bottom-padding">
                        <label for="categoriesListForAdd"><b>${category}</b></label>

                        <select name="category" id="categoriesListForAdd" class="w3-select"
                                required title="${categoryRule}">
                            <option value="" disabled selected>${chooseCategory}</option>
                        </select>

                    </div>
                </div>

                <div class="w3-section margin-bottom">
                    <div class="w3-container bottom-padding">
                        <label><b>${photos}</b></label>
                        <input name="file" type="file" id="fileInput" class="inputfile"
                               accept="image/jpg,image/jpeg,image/png,image/gif" title="${photosRule}"
                               data-multiple-caption="{count} ${messageFilesSelected}"
                               data-size-message="${photosSizeRule}"
                               data-rule-message="${photosRule}" multiple/>
                        <label for="fileInput"><img src="${pageContext.request.contextPath}/img/file.svg"/>
                            <span>${chooseFiles}</span></label>
                    </div>
                </div>

                <div class="w3-container">
                    <button id="addItemSubmitButton"
                            class="pro-button pro-green-black pro-green-border w3-block w3-padding">
                        ${addButton}
                    </button>
                </div>

            </div>

        </form>

    </div>
</div>

<script src="${pageContext.request.contextPath}/js/price-validation.js"></script>
<script src="${pageContext.request.contextPath}/js/controller/add-item.controller.js"></script>
<script src="${pageContext.request.contextPath}/js/date-validation.js"></script>
<script src="${pageContext.request.contextPath}/js/custom-file-input.js"></script>