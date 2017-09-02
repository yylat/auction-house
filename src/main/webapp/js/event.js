(function () {

    var sidebar = document.getElementById("sidebar");

    var sidebarCloseButton = document.getElementById("sidebarCloseButton");
    var sidebarOpenButton = document.getElementById("sidebarOpenButton");
    sidebarCloseButton.addEventListener("click", function () {
        sidebar.style.display = "none";
    });
    sidebarOpenButton.addEventListener("click", function () {
        sidebar.style.display = "block";
    });

})();

function removeDefaultValidation(form) {

    form.addEventListener("invalid", function (event) {
        event.preventDefault();
    }, true);

}

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