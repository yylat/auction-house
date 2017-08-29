'use strict';

( function (document, window, index) {
    var inputs = document.querySelectorAll(".inputfile");
    Array.prototype.forEach.call(inputs, function (input) {
        var label = input.nextElementSibling,
            labelVal = label.innerHTML;

        input.addEventListener("change", function (e) {
            var fileMessage = "";

            if (this.files && this.files.length > 1) {
                if (this.files.length < 5) {
                    fileMessage = ( this.getAttribute("data-multiple-caption") || "" ).replace("{count}", this.files.length);
                }
                else {
                    this.value = "";
                    fileMessage = (this.getAttribute("data-rule-message") || "");
                    this.setCustomValidity(fileMessage);
                }
            }
            else {
                fileMessage = e.target.value.split("\\").pop();
            }

            var filesSize = checkFilesSize(this.files, this.getAttribute("data-size-message"));
            if (filesSize) {
                fileMessage = filesSize;
                this.setCustomValidity(fileMessage);
            }

            if (fileMessage) {
                label.querySelector("span").innerHTML = fileMessage;
            }
            else {
                label.innerHTML = labelVal;
            }
        });

    });

    function checkFilesSize(files, rule) {
        var fileMessage = "";
        var maxFileSize = 2.1;
        var i = 0;
        while (!fileMessage && i < files.length) {
            if ((files[i].size / 1024 / 1024) > maxFileSize) {
                fileMessage = (rule || "File size must be <= 2MB.");
            }
            i++;
        }
        return fileMessage;
    }

}(document, window, 0));