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

})();