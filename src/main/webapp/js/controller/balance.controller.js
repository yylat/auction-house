(function () {

    var balanceForm = document.getElementById("balanceForm");
    removeDefaultValidation(balanceForm);

    var balanceButton = balanceForm.querySelector("button");
    balanceButton.addEventListener("click", validateForm.bind(null, balanceForm));

}());