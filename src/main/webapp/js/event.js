(function () {

    /* manage opening and closing operation --------------------------------------------- */

    function open(element) {
        element.style.display = "block";
    }

    function close(element) {
        element.style.display = "none";
    }

    var sidebar = document.getElementById("sidebar");
    var sidebarOpenButton = document.getElementById("sidebarOpenButton");
    var sidebarCloseButton = document.getElementById("sidebarCloseButton");

    sidebarOpenButton.addEventListener("click", open.bind(null, sidebar));
    sidebarCloseButton.addEventListener("click", close.bind(null, sidebar));

    /* ---------------------------------------------------------------------------------- */

    /* tracking bottom of the page ------------------------------------------------------ */

    setInterval(function () {

        if (window.innerWidth > 993) {

            var langSwitch = document.getElementById("langSwitch");

            var totalHeight = document.body.scrollHeight;
            var visibleHeight = window.innerHeight;

            var currentScroll;

            if (document.documentElement.scrollTop) {
                currentScroll = document.documentElement.scrollTop;
            }
            else {
                currentScroll = document.body.scrollTop;
            }

            if (totalHeight <= (currentScroll + visibleHeight + 20)) {
                langSwitch.style.bottom = "7em";
            }
            else {
                langSwitch.style.bottom = "4em";
            }
        }

    }, 10)

    /* ---------------------------------------------------------------------------------- */

})();