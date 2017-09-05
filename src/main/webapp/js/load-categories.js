(function () {

    function loadCategories() {
        var xhttp = new XMLHttpRequest();
        xhttp.open("GET", "/ajax?command=load-categories", true);
        xhttp.onreadystatechange = function () {
            if (this.readyState == 4 && this.status == 200) {
                var categoriesListForSearch = document.getElementById("categoriesListForSearch");

                var categories = JSON.parse(this.responseText);

                var fragment=document.createDocumentFragment();
                for (var i = 0; i < categories.length; i++) {

                    var newItem = document.createElement("option");
                    newItem.value = categories[i].id;
                    var textNode = document.createTextNode(categories[i].description);
                    newItem.appendChild(textNode);
                    fragment.appendChild(newItem);
                }

                var categoriesListForAdd = document.getElementById("categoriesListForAdd");
                if(categoriesListForAdd){
                    var fragmentClone = fragment.cloneNode(true);
                    categoriesListForAdd.insertBefore(fragmentClone, null);
                }

                categoriesListForSearch.insertBefore(fragment, null);
            }
        };
        xhttp.send();
    }

    loadCategories();

})();