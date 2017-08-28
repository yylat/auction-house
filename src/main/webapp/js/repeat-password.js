(function () {

    var passwordInput = document.getElementById("password");
    var repeatedPasswordInput = document.getElementById("repeated-password");

    repeatedPasswordInput.addEventListener("input", function () {
        if (passwordInput.value !== repeatedPasswordInput.value) {
            repeatedPasswordInput.setCustomValidity(repeatedPasswordInput.getAttribute("data-repeated-error"));
        }
        else {
            repeatedPasswordInput.setCustomValidity("");
        }
    });

})();