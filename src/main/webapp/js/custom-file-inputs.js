( function (document, window, index) {

    var DEFAULT_MAX_FILE_NUMBER = 4;
    var MAX_FILE_SIZE = 2.1 * 1024 * 1024;

    var inputs = document.querySelectorAll(".inputfile");
    Array.prototype.forEach.call(inputs, function (input) {
        var label = input.nextElementSibling,
            labelVal = label.innerHTML;

        input.addEventListener("change", function (e) {
            var fileMessage = "";

            var maxFileNumber = this.getAttribute("data-max-file-number");
            if (!maxFileNumber) {
                maxFileNumber = DEFAULT_MAX_FILE_NUMBER;
            }

            if (this.files) {
                if (this.files.length > 1) {
                    if (this.files.length < maxFileNumber) {
                        fileMessage = ( this.getAttribute("data-multiple-caption") || "" ).replace("{count}", this.files.length);
                        this.setCustomValidity("");
                    }
                    else {
                        this.value = "";
                        fileMessage = (this.getAttribute("data-rule-message") || "");
                        this.setCustomValidity(fileMessage);
                    }
                } else {
                    fileMessage = e.target.value.split("\\").pop();
                    this.setCustomValidity("");
                }
            }


            var filesSizeMessage = checkFilesSize(this.files, this.getAttribute("data-size-message"));
            if (filesSizeMessage) {
                fileMessage = filesSizeMessage;
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
        var sizeMessage = "";
        var i = 0;
        while (!sizeMessage && i < files.length) {
            if (MAX_FILE_SIZE < files[i].size) {
                sizeMessage = (rule || "File size must be <= 2MB.");
            }
            i++;
        }
        return sizeMessage;
    }

}(document, window, 0));