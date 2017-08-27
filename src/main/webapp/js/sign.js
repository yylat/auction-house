(function () {

    /* Manage opening and closing operation --------------------------------------------- */

    function open(element) {
        element.style.display = "block";
    }

    function close(element) {
        element.style.display = "none";
    }

    var signInModal = document.getElementById("signInModal");
    var signInOpenButton = document.getElementById("signInOpenButton");
    var signInCloseButton = document.getElementById("signInCloseButton");

    signInOpenButton.addEventListener("click", open.bind(null, signInModal));
    signInCloseButton.addEventListener("click", close.bind(null, signInModal));

    var signUpModal = document.getElementById("signUpModal");
    var signUpOpenButton = document.getElementById("signUpOpenButton");
    var signUpCloseButton = document.getElementById("signUpCloseButton");

    signUpOpenButton.addEventListener("click", open.bind(null, signUpModal));
    signUpCloseButton.addEventListener("click", close.bind(null, signUpModal));

    /* ---------------------------------------------------------------------------------- */

    /* Customizing validation ----------------------------------------------------------- */

    // function removeDefaultValidationMsg(form) {
    //
    //     form.addEventListener("invalid", function (event) {
    //         event.preventDefault();
    //     }, true);
    //
    //     form.addEventListener("submit", function (event) {
    //         if (!this.checkValidity()) {
    //             event.preventDefault();
    //         }
    //     });
    //
    // }

    var signInForm = document.getElementById("signInForm");
    var signUpForm = document.getElementById("signUpForm");

    // removeDefaultValidationMsg(signInForm);
    // removeDefaultValidationMsg(signUpForm);

    var signInSubmitButton = document.getElementById("signInSubmitButton");
    var signUpSubmitButton = document.getElementById("signUpSubmitButton");

    // function checkPasswordMatch() {
    //     var password = document.getElementById("password");
    //
    //     var repeatedPassword = document.getElementById("repeated-password");
    //
    //     if (password.value !== repeatedPassword.value) {
    //         repeatedPassword.setCustomValidity("Password didn't match.");
    //     }
    // }

    //
    // function validateForm(form) {
    //
    //     var invalidFields = form.querySelectorAll(":invalid");
    //     var errorMessages = form.querySelectorAll(".error-message");
    //
    //     var i;
    //
    //     for (i = 0; i < errorMessages.length; i++) {
    //         errorMessages[i].parentNode.removeChild(errorMessages[i]);
    //     }
    //
    //     for (i = 0; i < invalidFields.length; i++) {
    //         var errorMessage = invalidFields[i].title;
    //
    //         var parent = invalidFields[i].parentNode;
    //
    //         var newItem = document.createElement("div");
    //         newItem.className = "error-message";
    //         var textNode = document.createTextNode(errorMessage);
    //         newItem.appendChild(textNode);
    //
    //         parent.insertBefore(newItem, null);
    //     }
    //
    //     if (invalidFields.length > 0) {
    //         invalidFields[0].focus();
    //         return false;
    //     }
    // }

    signInSubmitButton.addEventListener("click", validateForm.bind(null, signInForm));

    // signUpSubmitButton.addEventListener("click", checkPasswordMatch);
    signUpSubmitButton.addEventListener("click", validateForm.bind(null, signUpForm));

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

    /* ---------------------------------------------------------------------------------- */

})();