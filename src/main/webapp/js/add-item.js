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

    /* Manage opening and closing operation --------------------------------------------- */

    function open(element) {
        loadCategories();
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

    var addItemForm = document.getElementById("addItemForm");
    var addItemSubmitButton = document.getElementById("addItemSubmitButton");

    addItemSubmitButton.addEventListener("click", validateForm.bind(null, addItemForm));

    /* ---------------------------------------------------------------------------------- */

})();