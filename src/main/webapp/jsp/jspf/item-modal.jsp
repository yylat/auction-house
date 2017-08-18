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
<fmt:message bundle="${msg}" key="form.chooseFiles" var="chooseFiles"/>
<fmt:message bundle="${msg}" key="form.addButton" var="addButton"/>


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
                               pattern="[A-Za-z]{2,40}"
                               title="${itemTitleRule}"/>
                    </div>

                    <div class="w3-container bottom-padding">
                        <label><b>${description}</b></label>
                        <textarea class="w3-input w3-border" name="description" required
                                  title="${descriptionRule}"></textarea>
                    </div>
                </div>

                <div class="w3-section margin-bottom">
                    <div class="w3-row-padding">
                        <div class="w3-col m6">
                            <label><b>${startPrice}</b></label>
                            <input name="start-price" class="w3-input" type="number" step="0.001" min="0.000"
                                   max="99999999999999999999.999" required
                                   title="${priceRule}"/>
                        </div>
                        <div class="w3-col m6">
                            <label><b>${blitzPrice}</b></label>
                            <input name="blitz-price" class="w3-input" type="number" step="0.001" min="0.000"
                                   max="99999999999999999999.999" required
                                   title="${priceRule}"/>
                        </div>
                    </div>
                </div>

                <div class="w3-section margin-bottom">
                    <div class="w3-row-padding">
                        <div class="w3-col m6">
                            <label><b>${startDate}</b></label>
                            <input name="start-date" class="w3-input" type="date" required
                                   title="${startDateRule}"/>
                        </div>
                        <div class="w3-col m6">
                            <label><b>${closeDate}</b></label>
                            <input name="close-date" class="w3-input" type="date" required
                                   title="${closeDateRule}"/>
                        </div>
                    </div>
                </div>

                <div class="w3-section margin-bottom">
                    <div class="w3-container bottom-padding">
                        <label for="categoriesList"><b>${category}</b></label>

                        <select name="category-id" id="categoriesList" class="w3-select">
                            <option value="" disabled selected>${chooseCategory}</option>
                        </select>

                    </div>
                </div>

                <div class="w3-section margin-bottom">
                    <div class="w3-container bottom-padding">
                        <label><b>${photos}</b></label>
                        <input name="file" type="file" id="fileInput" class="inputfile"
                               data-multiple-caption="{count} ${messageFilesSelected}" rule-message="${photosRule}" multiple/>
                        <label for="fileInput"><img
                                src="${pageContext.request.contextPath}/img/file.svg"/><span>${chooseFiles}</span></label>
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

<script src="${pageContext.request.contextPath}/js/add-item.js"></script>
<script src="${pageContext.request.contextPath}/js/custom-file-inputs.js"></script>