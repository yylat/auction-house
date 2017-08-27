(function () {

    var dropdown = document.getElementById("filterDropdownContent");

    var dropdownSwitch = document.getElementById("filterDropdownSwitch");

    dropdownSwitch.addEventListener("click", openDropdown);

    function openDropdown() {
        if (dropdown.className.indexOf("w3-show") == -1) {
            dropdown.className += " w3-show";
        } else {
            dropdown.className = dropdown.className.replace(" w3-show", "");
        }
    }

    var applyFilter = document.getElementById("applyFilter");
    var filterForm = dropdown.querySelector("form");

    applyFilter.addEventListener("click", validateForm.bind(null, filterForm))

})();