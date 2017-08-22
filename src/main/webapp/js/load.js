function loadItems(command, lastItemId) {
    var xhttp = new XMLHttpRequest();
    var url = "/ajax?command=" + command;
    if (lastItemId != undefined) {
        url += "&lastItemId=" + lastItemId;
    }
    xhttp.open("GET", url + "&t=" + Math.random(), true);
    xhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            var response = JSON.parse(this.responseText);
            var items = JSON.parse(response.items);

            var itemsList = document.getElementById("itemsList");
            if (items.length != 0) {
                buildGrid(itemsList, response.hasNext, lastItemId, items, itemsList, command);
            }
            else {
                displayEmptyMessage(itemsList);
            }

        }
    };
    xhttp.send();
}

function buildGrid(itemsList, hasNext, lastItemId, items, command) {
    var button;
    var imgName = "image";

    var length = items.length;

    if (hasNext == true) {
        if (lastItemId != undefined) {
            button = document.getElementById("loadItemsButton");
            imgName += lastItemId;
        }
        else {
            button = document.createElement("button");
            button.className = "w3-button";
            button.id = "loadItemsButton";
            var textNode = document.createTextNode("next");
            button.appendChild(textNode);
        }
        button.addEventListener("click", loadItems.bind(null, command, items[length].id));
        itemsList.insertBefore(button, null);
    } else {
        button = document.getElementById("loadItemsButton");
        if (button != undefined) {
            button.display = "none";
        }
    }

    var documentFragment = document.createDocumentFragment();

    var leftCellIndex;
    var rightCellIndex;

    for (leftCellIndex = 0; leftCellIndex < length; leftCellIndex = leftCellIndex + 2) {
        rightCellIndex = leftCellIndex + 1;

        var row = document.createElement("div");
        row.className = "w3-row-padding";

        row.appendChild(buildCell(items[leftCellIndex], imgName));

        if (rightCellIndex > length) {
            row.appendChild(buildCell(items[rightCellIndex], imgName));
        }

        documentFragment.appendChild(row);
    }

    if (button != undefined) {
        itemsList.insertBefore(documentFragment, button);
    } else {
        itemsList.insertBefore(documentFragment, null);
    }

    loadImages(imgName);
}

function buildCell(item, imgName) {
    var cell = document.createElement("form");
    cell.action = "/controller";

    var command = document.createElement("input");
    command.type = "hidden";
    command.name = "command";
    command.value = "load-item";
    cell.appendChild(command);

    var itemId = document.createElement("input");
    itemId.type = "hidden";
    itemId.name = "itemId";
    itemId.value = item.id;
    cell.appendChild(itemId);

    var button = document.createElement("button");
    button.className = "button-reset";
    button.insertAdjacentHTML("afterBegin",
        "<div class=\"w3-col w3-margin-top m6\">" +
        "<div class=\"w3-card item-wrapper\">" +
        "<div class=\"w3-container item-back\">" +
        "<div class=\"w3-container img-container\">" +
        "<img src=\"\" item=\"" + item.id + "\"" + " name=\"" + imgName + "\"" + "/>" +
        "</div><div class=\"w3-container w3-center uppercase\">" + item.name +
        "</div><div class=\"w3-container w3-center\">" +
        "<div class=\"text-on-color money\">" + item.actualPrice +
        "</div></div></div></div></div>");
    cell.appendChild(button);

    return cell;
}

function loadImages(imgName) {
    var images = document.getElementsByName(imgName);

    images.forEach(function (image) {
        var itemId = image.getAttribute("item");
        loadImage(itemId, image);
    });
}

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

function displayEmptyMessage(itemsList) {
    emptyMsg = document.createElement("p");
    var textNode = document.createTextNode(itemsList.getAttribute("data-empty-message"));
    emptyMsg.appendChild(textNode);
    itemsList.insertBefore(emptyMsg, null);
}