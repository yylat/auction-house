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
                    fileName = (this.getAttribute("data-rule-message") || "");
                }
            }
            else {
                fileName = e.target.value.split("\\").pop();
            }

            var filesSize = checkFilesSize(this.files, this.getAttribute("data-size-message"));
            if (filesSize) {
                fileName = filesSize;
            }

            if (fileName) {
                label.querySelector("span").innerHTML = fileName;
            }
            else {
                label.innerHTML = labelVal;
            }
        });

    });

    function checkFilesSize(files, rule) {
        var fileName = "";
        var maxFileSize = 2.1;
        var i = 0;
        while (!fileName && i < files.length) {
            if ((files[i].size / 1024 / 1024) > maxFileSize) {
                fileName = (rule || "File size must be <= 2MB.");
            }
            i++;
        }
        return fileName;
    }

}(document, window, 0));