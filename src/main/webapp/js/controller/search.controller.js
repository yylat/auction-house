(function () {

    var searchForm = document.getElementById("searchForm");

    removeDefaultValidation(searchForm);

    searchForm.addEventListener("submit", function () {
        if (!this.checkValidity()) {
            event.preventDefault();
        }
    });

}());