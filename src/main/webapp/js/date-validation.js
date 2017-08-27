(function () {

    var currentDate = new Date();

    var minAllowedStart = new Date();
    minAllowedStart.setDate(minAllowedStart.getDate() + 2);
    var minAllowedClose = new Date();
    minAllowedClose.setDate(minAllowedClose.getDate() + 4);

    var startDate = document.getElementById("start-date");
    var closeDate = document.getElementById("close-date");

    startDate.value = minAllowedStart.toISOString().slice(0, 10);
    closeDate.value = minAllowedClose.toISOString().slice(0, 10);

    var d1 = new Date(startDate.value);
    var d2 = Date.parse(startDate.value);

    startDate.addEventListener("change", function () {
        if (new Date(startDate.value) < minAllowedStart) {
            startDate.setCustomValidity(startDate.title);
        }
        else {
            startDate.setCustomValidity("");
        }
    });

    closeDate.addEventListener("change", function () {
        if (new Date(closeDate.value) < minAllowedClose) {
            closeDate.setCustomValidity(closeDate.title);
        }
        else {
            startDate.setCustomValidity("");
        }
    });

})();