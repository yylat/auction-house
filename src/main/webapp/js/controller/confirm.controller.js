function confirmAction(callback) {
    var confirmModal = document.getElementById("confirmModal");
    var yesButton = document.getElementById("yesButton");
    var noButton = document.getElementById("noButton");
    var closeConfirm = document.getElementById("closeConfirm");

    confirmModal.style.display = "block";

    yesButton.addEventListener("click", function () {
        callback(true);
        confirmModal.style.display = "none";
    }, false);
    noButton.addEventListener("click", function () {
        callback(false);
        confirmModal.style.display = "none";
    }, false);
    closeConfirm.addEventListener("click", function () {
        callback(false);
        confirmModal.style.display = "none";
    }, false);

}