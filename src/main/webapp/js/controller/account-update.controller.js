(function () {

    var content = document.querySelector(".content");

    var forms = content.forms;
    var length = forms.length;

    for (var i = 0; i < length; i++) {
        removeDefaultValidation(forms[i]);
        var button = forms[i].querySelector("button");
        button.addEventListener("click", validateForm.bind(null, forms[i]));
    }



}());