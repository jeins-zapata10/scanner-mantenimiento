document.addEventListener("DOMContentLoaded", () => {

    const toggle =
        document.querySelector("#notifications-toggle");

    const menu =
        document.querySelector("#notifications-menu");


    if (!toggle || !menu) {
        return;
    }


    function openNotifications() {
        const userMenu =
            document.querySelector("#user-menu");

        if (userMenu) {
            userMenu.classList.add("hidden");
        }

        menu.classList.remove("hidden");

        toggle.setAttribute(
            "aria-expanded",
            "true"
        );


    }


    function closeNotifications() {

        menu.classList.add("hidden");

        toggle.setAttribute(
            "aria-expanded",
            "false"
        );

    }


    function toggleNotifications() {

        if (menu.classList.contains("hidden")) {

            openNotifications();

        } else {

            closeNotifications();

        }

    }


    // BOTÓN CAMPANA

    toggle.addEventListener("click", (event) => {

        event.stopPropagation();

        toggleNotifications();

    });


    // CLIC DENTRO DEL DROPDOWN

    menu.addEventListener("click", (event) => {

        event.stopPropagation();

    });


    // CLIC FUERA

    document.addEventListener("click", () => {

        closeNotifications();

    });


    // ESC

    document.addEventListener("keydown", (event) => {

        if (event.key === "Escape") {

            closeNotifications();

            toggle.focus();

        }

    });

});