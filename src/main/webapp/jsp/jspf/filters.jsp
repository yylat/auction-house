<div class="w3-section">
    <div class="w3-container w3-margin">
        <label for="orderList">sort by</label>
    </div>
    <div class="w3-container w3-margin">
        <select name="order-by" id="orderList" class="w3-select back-back-color">
            <option value="start-date" selected>startDate</option>
            <option value="close-date">closeDate</option>
            <option value="actual-price">actualPrice</option>
            <option value="blitz-price">blitzPrice</option>
        </select>
    </div>
    <div class="w3-container w3-margin">
        <input class="w3-radio" type="radio" name="order-type" value="desc" checked>desc
        <input class="w3-radio" type="radio" name="order-type" value="asc">asc
    </div>
</div>

<div class="w3-section">
    <div class="w3-container w3-margin-top">
        blitzPrice
    </div>
    <div class="w3-container">
        <input class="w3-input" name="blitz-price-floor" type="number"
               placeholder="from"/>
    </div>
    <div class="w3-container">
        <input class="w3-input" name="blitz-price-ceiling" type="number"
               placeholder="to"/>
    </div>
    <div class="w3-container w3-margin-top">
        actualPrice
    </div>
    <div class="w3-container">
        <input class="w3-input" name="actual-price-floor" type="number"
               placeholder="from"/>
    </div>
    <div class="w3-container">
        <input class="w3-input" name="actual-price-ceiling" type="number"
               placeholder="to"/>
    </div>
</div>

<div class="w3-section">
    <div class="w3-container w3-margin-top">
        startDate
    </div>
    <div class="w3-container">
        <input class="w3-input" name="start-date-floor" type="date"
               placeholder="from"/>
    </div>
    <div class="w3-container">
        <input class="w3-input" name="start-date-ceiling" type="date"
               placeholder="to"/>
    </div>
    <div class="w3-container w3-margin-top">
        closeDate
    </div>
    <div class="w3-container">
        <input class="w3-input" name="close-date-floor" type="date"
               placeholder="from"/>
    </div>
    <div class="w3-container">
        <input class="w3-input" name="close-date-ceiling" type="date"
               placeholder="to"/>
    </div>
</div>

<div class="w3-section w3-margin-top">
    <div class="w3-container">
        <select name="category-id" id="categoriesList" class="w3-select">
            <option value="" selected>chooseCategory</option>
        </select>
    </div>
    <div class="w3-container w3-margin-top">
        <button class="pro-button pro-green-black pro-green-border w3-block w3-padding">
            apply
        </button>
    </div>
</div>
