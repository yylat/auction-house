(function () {

    var pageBar = document.getElementById("pageBar");

    var prevLink = document.getElementById("prevLink");
    var nextLink = document.getElementById("nextLink");

    var basicUrl = "/controller?command=" + pageBar.getAttribute("data-command");

    var pages = pageBar.getAttribute("data-pages");
    basicUrl += "&pages=" + pages;

    var page = pageBar.getAttribute("data-page");

    if (!page) {
        page = 1;
    }

    if (page != 1) {
        prevLink.href = basicUrl + "&page=" + page - 1;
    }

    if (page != pages) {
        nextLink.href = basicUrl + "&page=" + page + 1;
    }

    var fragment = document.createDocumentFragment();

    for (var i = 1; i <= pages; i++) {
        fragment.appendChild(buildLink(i));
    }

    pageBar.insertBefore(fragment, nextLink);

    function buildLink(index) {
        var link = document.createElement("a");
        link.className = "w3-button";

        if (index != page) {
            link.href = basicUrl + "&page=" + index;
        }
        else {
            link.className += " pro-lightgrey";
        }

        var textNode = document.createTextNode(index);
        link.appendChild(textNode);

        return link;
    }

}());