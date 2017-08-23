(function () {
    var images = document.querySelectorAll(".img-container > img");

    images.forEach(function (image) {
        var itemId = image.getAttribute("item");
        loadImage(itemId, image);
    });

    function loadImage(itemId, img) {
        var xhttp = new XMLHttpRequest();
        xhttp.open("GET", "/ajax?command=load-photo&itemId=" + itemId + "&t=" + Math.random(), true);
        xhttp.onreadystatechange = function () {
            if (this.readyState == 4 && this.status == 200) {
                var string = this.responseText;
                img.src = "data:image/png;base64," + string;
            }
        };
        xhttp.send();
    }

}());