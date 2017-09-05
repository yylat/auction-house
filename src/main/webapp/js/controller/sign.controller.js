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

    if (signInOpenButton) {
        signInOpenButton.addEventListener("click", open.bind(null, signInModal));
    }
    if (signInOpenButton) {
        signInCloseButton.addEventListener("click", close.bind(null, signInModal));
    }

    var signUpModal = document.getElementById("signUpModal");
    var signUpOpenButton = document.getElementById("signUpOpenButton");
    var signUpCloseButton = document.getElementById("signUpCloseButton");

    if (signUpOpenButton) {
        signUpOpenButton.addEventListener("click", open.bind(null, signUpModal));
    }
    if (signUpCloseButton) {
        signUpCloseButton.addEventListener("click", close.bind(null, signUpModal));
    }

    /* ---------------------------------------------------------------------------------- */

    /* Customizing validation ----------------------------------------------------------- */

    var signInForm = document.getElementById("signInForm");
    var signUpForm = document.getElementById("signUpForm");

    var signInSubmitButton = document.getElementById("signInSubmitButton");
    var signUpSubmitButton = document.getElementById("signUpSubmitButton");

    removeDefaultValidation(signInForm);
    removeDefaultValidation(signUpForm);

    if (signInSubmitButton) {
        signInSubmitButton.addEventListener("click", validateForm.bind(null, signInForm));
    }
    if (signUpSubmitButton) {
        signUpSubmitButton.addEventListener("click", validateForm.bind(null, signInForm));
    }

    /* ---------------------------------------------------------------------------------- */

})();