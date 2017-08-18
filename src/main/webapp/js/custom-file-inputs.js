'use strict';

( function (document, window, index) {
    var inputs = document.querySelectorAll(".inputfile");
    Array.prototype.forEach.call(inputs, function (input) {
        var label = input.nextElementSibling,
            labelVal = label.innerHTML;

        input.addEventListener("change", function (e) {
            var fileName = "";
            if (this.files && this.files.length > 1) {
                if (this.files.length < 5) {
                    fileName = ( this.getAttribute("data-multiple-caption") || "" ).replace("{count}", this.files.length);
                }
                else {
                    this.value = "";
                    fileName = (this.getAttribute("rule-message") || "");
                }
            }
            else {
                fileName = e.target.value.split("\\").pop();
            }

            if (fileName) {
                label.querySelector("span").innerHTML = fileName;
            }
            else {
                label.innerHTML = labelVal;
            }
        });

    });
}(document, window, 0));