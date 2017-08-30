(function () {

    var maxPhotoNumber = 4;

    var photosContainer = document.getElementById("photos");
    var fileInput = document.getElementById("fileInput");

    var deletePhotosForm = document.getElementById("deletePhotosForm");

    deletePhotosForm.addEventListener("submit", function (event) {
        var checkboxes = document.getElementsByName("photo-id");
        var checked = 0;
        for (var i = 0; i < checkboxes.length; i++) {
            if (checkboxes[i].checked) {
                checked++;
            }
        }
        if (checked == 0) {
            event.preventDefault();
        }
    });

    var addPhotosForm = document.getElementById("addPhotosForm");

    addPhotosForm.addEventListener("submit", function (event) {
        if (fileInput.files.length == 0) {
            //event.preventDefault();
        }
    });

    function displayErrorMsg() {
        var newItem = document.createElement("p");
        var textNode = document.createTextNode(photosContainer.getAttribute("data-error-message"));
        newItem.appendChild(textNode);
        photosContainer.insertBefore(newItem, null);
    }

    function createColumn(photo, number) {
        var column = document.createElement("div");
        column.className = "w3-col s3";

        var imgContainer = document.createElement("div");
        imgContainer.className = "static-img-container";

        var img = document.createElement("img");
        img.className = "";
        img.src = "data:image/png;base64," + photo;

        imgContainer.appendChild(img);

        var checkbox = document.createElement("input");
        checkbox.type = "checkbox";
        checkbox.className = "w3-check";
        checkbox.name = "photo-id";
        checkbox.value = number;

        column.appendChild(imgContainer);
        column.appendChild(checkbox);

        return column;
    }

    function loadPhotos(itemId) {
        var xhttp = new XMLHttpRequest();
        xhttp.open("GET", "/ajax?command=load-photos-for-delete&itemId=" + itemId + "&t=" + Math.random(), true);
        xhttp.onreadystatechange = function () {
            if (this.readyState == 4 && this.status == 200) {
                var photos = JSON.parse(this.responseText);
                var length = Object.keys(photos).length;

                fileInput.setAttribute("data-max-file-number", maxPhotoNumber - length);

                if (length == 0) {
                    displayErrorMsg();
                    var deletePhotosButton = document.getElementById("deletePhotosButton");
                    deletePhotosButton.disabled = true;
                }
                else {

                    var fragment = document.createDocumentFragment();

                    Object.keys(photos).forEach(function (key) {
                        var column = createColumn(photos[key], key);
                        fragment.appendChild(column);
                    });

                    photosContainer.insertBefore(fragment, null);

                }
            }
        };
        xhttp.send();
    }

    loadPhotos(photosContainer.getAttribute("data-item-id"));

}());