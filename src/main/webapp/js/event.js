(function () {

    /* manage opening and closing operation --------------------------------------------- */

    function open(element) {
        element.style.display = "block";
    }

    function close(element) {
        element.style.display = "none";
    }

    var sidebar = document.getElementById("sidebar");
    var sidebarOpenButton = document.getElementById("sidebarOpenButton");
    var sidebarCloseButton = document.getElementById("sidebarCloseButton");

    sidebarOpenButton.addEventListener("click", open.bind(null, sidebar));
    sidebarCloseButton.addEventListener("click", close.bind(null, sidebar));

    /* ---------------------------------------------------------------------------------- */

    /* tracking bottom of the page ------------------------------------------------------ */

    setInterval(function () {

        if (window.innerWidth > 993) {

            var langSwitch = document.getElementById("langSwitch");

            var totalHeight = document.body.scrollHeight;
            var visibleHeight = window.innerHeight;

            var currentScroll;

            if (document.documentElement.scrollTop) {
                currentScroll = document.documentElement.scrollTop;
            }
            else {
                currentScroll = document.body.scrollTop;
            }

            if (totalHeight <= (currentScroll + visibleHeight + 20)) {
                langSwitch.style.bottom = "7em";
            }
            else {
                langSwitch.style.bottom = "4em";
            }
        }

    }, 10);

    /* ---------------------------------------------------------------------------------- */

    /* remove default validation -------------------------------------------------------- */

    function removeDefaultValidationMsg(form) {

        form.addEventListener("invalid", function (event) {
            event.preventDefault();
        }, true);

        form.addEventListener("submit", function (event) {
            if (!this.checkValidity()) {
                event.preventDefault();
            }
        });

    }

    var forms = document.forms;
    for (var i = 0; i < forms.length; i++) {
        removeDefaultValidationMsg(forms[i]);
    }

    /* ---------------------------------------------------------------------------------- */

})();

function validateForm(form) {

    var invalidFields = form.querySelectorAll(":invalid");
    var errorMessages = form.querySelectorAll(".error-message");

    var i;

    for (i = 0; i < errorMessages.length; i++) {
        errorMessages[i].parentNode.removeChild(errorMessages[i]);
    }

    for (i = 0; i < invalidFields.length; i++) {
        var errorMessage = invalidFields[i].title;

        var parent = invalidFields[i].parentNode;

        var newItem = document.createElement("div");
        newItem.className = "error-message";
        var textNode = document.createTextNode(errorMessage);
        newItem.appendChild(textNode);

        parent.insertBefore(newItem, null);
    }

    if (invalidFields.length > 0) {
        invalidFields[0].focus();
        return false;
    }
}