<fmt:message bundle="${msg}" key="form.blitzPrice" var="blitzPrice"/>
<fmt:message bundle="${msg}" key="item.actualPrice" var="actualPrice"/>
<fmt:message bundle="${msg}" key="form.startDate" var="startDate"/>
<fmt:message bundle="${msg}" key="form.closeDate" var="closeDate"/>

<fmt:message bundle="${msg}" key="form.chooseCategory" var="chooseCategory"/>

<fmt:message bundle="${msg}" key="filter.apply" var="apply"/>
<fmt:message bundle="${msg}" key="filter.sortBy" var="sortBy"/>
<fmt:message bundle="${msg}" key="filter.orderAsc" var="orderAsc"/>
<fmt:message bundle="${msg}" key="filter.orderDesc" var="orderDesc"/>
<fmt:message bundle="${msg}" key="filter.from" var="from"/>
<fmt:message bundle="${msg}" key="filter.to" var="to"/>


<div class="w3-row-padding w3-margin-top pro-padding-bottom w3-border-bottom">
    <div class="w3-col m6">
        <label for="orderList">${sortBy}</label>
        <select name="order-by" id="orderList" class="w3-select back-back-color">
            <option value="start-date" selected>${startDate}</option>
            <option value="close-date">${closeDate}</option>
            <option value="actual-price">${actualPrice}</option>
            <option value="blitz-price">${blitzPrice}</option>
        </select>
    </div>
    <div class="w3-col m6">
        <input class="w3-radio pro-padding-top" type="radio" name="order-type" value="desc" checked>${orderDesc}<br>
        <input class="w3-radio" type="radio" name="order-type" value="asc">${orderAsc}
    </div>
</div>

<div class="w3-row-padding w3-margin-top pro-padding-bottom w3-border-bottom">
    <div class="w3-col m6">
        <div class="w3-container pro-padding-top">
            ${blitzPrice}
        </div>
        <div class="w3-container">
            <input class="w3-input" name="blitz-price-floor" type="number" step="0.001" min="0.000"
                   max="99999999999999999999.999" placeholder="${from}"/>
        </div>
        <div class="w3-container">
            <input class="w3-input" name="blitz-price-ceiling" type="number" step="0.001" min="0.000"
                   max="99999999999999999999.999" placeholder="${to}"/>
        </div>
    </div>
    <div class="w3-col m6">
        <div class="w3-container pro-padding-top">
            ${actualPrice}
        </div>
        <div class="w3-container">
            <input class="w3-input" name="actual-price-floor" type="number" step="0.001" min="0.000"
                   max="99999999999999999999.999" placeholder="${from}"/>
        </div>
        <div class="w3-container">
            <input class="w3-input" name="actual-price-ceiling" type="number" step="0.001" min="0.000"
                   max="99999999999999999999.999" placeholder="${to}"/>
        </div>
    </div>
</div>

<div class="w3-row-padding w3-margin-top pro-padding-bottom w3-border-bottom">
    <div class="w3-col m6">
        <div class="w3-container pro-padding-top">
            ${startDate}
        </div>
        <div class="w3-container">
            <label class="w3-small">${from}</label>
            <input class="w3-input" name="start-date-floor" type="date"/>
        </div>
        <div class="w3-container">
            <label class="w3-small">${to}</label>
            <input class="w3-input" name="start-date-ceiling" type="date"/>
        </div>
    </div>
    <div class="w3-col m6">
        <div class="w3-container pro-padding-top">
            ${closeDate}
        </div>
        <div class="w3-container">
            <label class="w3-small">${from}</label>
            <input class="w3-input" name="close-date-floor" type="date"/>
        </div>
        <div class="w3-container">
            <label class="w3-small">${to}</label>
            <input class="w3-input" name="close-date-ceiling" type="date"/>
        </div>
    </div>
</div>

<div class="w3-row-padding w3-margin-top pro-padding-bottom w3-border-bottom">
    <div class="w3-col m6">
        <div class="w3-container">
            <select name="category-id" id="categoriesList" class="w3-select">
                <option value="" selected>${chooseCategory}</option>
            </select>
        </div>
    </div>
    <div class="w3-col m6">
        <div class="w3-container">
            <button id="applyFilter" class="pro-button pro-green-black pro-green-border w3-block w3-padding">
                ${apply}
            </button>
        </div>
    </div>
</div>