(function (e, t, n) {
    var r = e.querySelectorAll("html")[0];
    r.className = r.className.replace(/(^|\s)no-js(\s|$)/, "$1js$2")
})(document, window, 0);

(function () {

    function loadCategories() {
        var xhttp = new XMLHttpRequest();
        xhttp.open("GET", "/ajax?command=load-categories&t=" + Math.random(), true);
        xhttp.onreadystatechange = function () {
            if (this.readyState == 4 && this.status == 200) {
                var categoriesList = document.getElementById("categoriesList");

                var categories = JSON.parse(this.responseText);
                for (var i = 0; i < categories.length; i++) {

                    var newItem = document.createElement("option");
                    newItem.value = categories[i].id;
                    var textNode = document.createTextNode(categories[i].description);
                    newItem.appendChild(textNode);
                    categoriesList.insertBefore(newItem, null);

                }
            }
        };
        xhttp.send();
    }

    loadCategories();

    /* Manage opening and closing operation --------------------------------------------- */

    function open(element) {
        element.style.display = "block";
    }

    function close(element) {
        element.style.display = "none";
    }

    var addItemModal = document.getElementById("addItemModal");
    var addItemOpenButton = document.getElementById("addItemOpenButton");
    var addItemCloseButton = document.getElementById("addItemCloseButton");

    addItemOpenButton.addEventListener("click", open.bind(null, addItemModal));
    addItemCloseButton.addEventListener("click", close.bind(null, addItemModal));

    /* ---------------------------------------------------------------------------------- */

    /* Customizing validation ----------------------------------------------------------- */

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

    var addItemForm = document.getElementById("addItemForm");

    removeDefaultValidationMsg(addItemForm);

    var addItemSubmitButton = document.getElementById("addItemSubmitButton");

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
            //var textNode = document.createTextNode(invalidFields[i].validationMessage);
            var textNode = document.createTextNode(errorMessage);
            newItem.appendChild(textNode);

            parent.insertBefore(newItem, null);
        }

        if (invalidFields.length > 0) {
            invalidFields[0].focus();
            return false;
        }

    }

    addItemSubmitButton.addEventListener("click", validateForm.bind(null, addItemForm));

    /* ---------------------------------------------------------------------------------- */

})();