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

    var signInForm = document.getElementById("signInForm");
    var signUpForm = document.getElementById("signUpForm");

    var signInSubmitButton = document.getElementById("signInSubmitButton");
    var signUpSubmitButton = document.getElementById("signUpSubmitButton");

    signInSubmitButton.addEventListener("click", validateForm.bind(null, signInForm));

    signUpSubmitButton.addEventListener("click", validateForm.bind(null, signUpForm));

    /* ---------------------------------------------------------------------------------- */

})();