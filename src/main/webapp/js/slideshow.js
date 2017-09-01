(function () {

    var photosContainer = document.getElementById("photos");

    function displayDefaultPic() {
        var imgContainer = document.createElement("div");
        imgContainer.className = "static-img-container";

        var img = document.createElement("img");
        img.className = "";
        img.src = "/img/default_img.gif";

        imgContainer.appendChild(img);

        photosContainer.insertBefore(imgContainer, null);
    }

    var slideIndex = 1;

    function showDivs(n) {
        slideIndex = n;
        var i;
        var slides = document.getElementsByClassName("photo-slide");
        var switches = document.getElementsByClassName("photo-switch");
        if (n > slides.length) {
            slideIndex = 1;
        }
        if (n < 1) {
            slideIndex = slides.length;
        }
        for (i = 0; i < slides.length; i++) {
            slides[i].style.display = "none";
        }
        for (i = 0; i < switches.length; i++) {
            switches[i].className = switches[i].className.replace(" w3-opacity-off", "");
        }
        slides[slideIndex - 1].style.display = "block";
        switches[slideIndex - 1].className += " w3-opacity-off";
    }

    function createImgContainer(photo, number) {
        var imgContainer = document.createElement("div");
        imgContainer.className = "static-img-container";

        var img = document.createElement("img");
        img.className = "";
        img.src = "data:image/png;base64," + photo;

        imgContainer.appendChild(img);

        if (number == undefined) {
            imgContainer.style.display = "none";
            imgContainer.className += " photo-slide";
        }
        else {
            imgContainer.style.display = "block";
            imgContainer.className += " photo-switch w3-opacity w3-hover-opacity-off";
            imgContainer.addEventListener("click", showDivs.bind(null, number));
        }

        return imgContainer;
    }

    function loadPhotos(itemId) {
        var xhttp = new XMLHttpRequest();
        xhttp.open("GET", "/ajax?command=load-all-photos&itemId=" + itemId + "&t=" + Math.random(), true);
        xhttp.onreadystatechange = function () {
            if (this.readyState == 4 && this.status == 200) {

                var photos = JSON.parse(this.responseText);

                var length = photos.length;

                if (length == 0) {
                    displayDefaultPic();
                }
                else {

                    var imgContainer;

                    if (length == 1) {
                        imgContainer = createImgContainer(photos[0]);
                        imgContainer.style.display = "block";
                        photosContainer.insertBefore(imgContainer, null);
                    }

                    else {
                        var i;

                        var row = document.createElement("div");
                        row.className = "w3-row-padding w3-padding w3-border w3-margin-top photo-row";
                        var col = 12 / photos.length;
                        for (i = 0; i < length; i++) {
                            imgContainer = createImgContainer(photos[i]);
                            photosContainer.insertBefore(imgContainer, null);

                            var column = document.createElement("div");
                            column.className = "w3-col s" + col;

                            var smallImgContainer = createImgContainer(photos[i], i + 1);
                            column.appendChild(smallImgContainer);
                            row.appendChild(column);
                        }
                        photosContainer.insertBefore(row, null);
                        showDivs(slideIndex);
                    }
                }
            }
        };
        xhttp.send();
    }

    loadPhotos(photosContainer.getAttribute("data-item-id"));

})();