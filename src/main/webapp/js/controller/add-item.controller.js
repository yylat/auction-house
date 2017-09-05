(function () {

    /* Manage opening and closing operation --------------------------------------------- */

    var addItemModal = document.getElementById("addItemModal");
    var addItemOpenButton = document.getElementById("addItemOpenButton");
    var addItemCloseButton = document.getElementById("addItemCloseButton");

    removeDefaultValidation(addItemModal);

    addItemOpenButton.addEventListener("click", function () {
        addItemModal.style.display = "block";
    });
    addItemCloseButton.addEventListener("click", function () {
        addItemModal.style.display = "none";
    });

    /* ---------------------------------------------------------------------------------- */

    /* Customizing validation ----------------------------------------------------------- */

    var addItemForm = document.getElementById("addItemForm");
    var addItemSubmitButton = document.getElementById("addItemSubmitButton");

    addItemSubmitButton.addEventListener("click", function () {
        var descriptionTextarea = document.getElementById("descriptionTextarea");
        if (descriptionTextarea) {
            var regExp = /<[^>]*>/;
            if (regExp.test(descriptionTextarea.value)) {
                descriptionTextarea.setCustomValidity(descriptionTextarea.getAttribute("title"));
            }
            else {
                descriptionTextarea.setCustomValidity("");
            }
        }
    });
    addItemSubmitButton.addEventListener("click", validateForm.bind(null, addItemForm));


    /* ---------------------------------------------------------------------------------- */

})();