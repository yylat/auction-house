(function () {
    var startPriceInput = document.getElementById("startPrice");
    var blitzPriceInput = document.getElementById("blitzPrice");
    var minBlitzPrice;

    startPriceInput.addEventListener("change", function () {
        minBlitzPrice = parseInt(startPriceInput.value);
        validateBlitzPrice();
    });

    blitzPriceInput.addEventListener("change", validateBlitzPrice);

    function validateBlitzPrice() {
        if (minBlitzPrice > parseInt(blitzPriceInput.value)) {
            blitzPriceInput.setCustomValidity(blitzPriceInput.getAttribute("data-blitz-rule"));
        } else {
            blitzPriceInput.setCustomValidity("");
        }
    }
})();