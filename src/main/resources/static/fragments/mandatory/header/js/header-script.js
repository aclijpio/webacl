var authorizationContainer = document.getElementById("authorizationContainer");
if (authorizationContainer) {
    authorizationContainer.addEventListener("click", function () {
        var loginForm = document.getElementById("loginFormContainer");
        if (!loginForm) return;
        var popupStyle = loginForm.style;
        if (popupStyle) {
            popupStyle.display = "flex";
            popupStyle.zIndex = 100;
            popupStyle.backgroundColor = "rgba(113, 113, 113, 0.3)";
            popupStyle.alignItems = "center";
            popupStyle.justifyContent = "center";
        }
        loginForm.setAttribute("closable", "");

        var onClickLogin =
            loginForm.onclick ||
            function (e) {
                if (e.target === loginForm && loginForm.hasAttribute("closable")) {
                    popupStyle.display = "none";
                }
            };
        var onClickRegister =
            loginForm.onclick ||
            function (e) {
                if (e.target === loginForm && loginForm.hasAttribute("closable")) {
                    popupStyle.display = "none";
                }
            };
        loginForm.addEventListener("click", onClickLogin);
    });
}
