(function () {

    /* Manage opening and closing operation --------------------------------------------- */

    var addItemModal = document.getElementById("addItemModal");
    var addItemOpenButton = document.getElementById("addItemOpenButton");
    var addItemCloseButton = document.getElementById("addItemCloseButton");

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

    addItemSubmitButton.addEventListener("click", validateForm.bind(null, addItemForm));

    /* ---------------------------------------------------------------------------------- */

})();