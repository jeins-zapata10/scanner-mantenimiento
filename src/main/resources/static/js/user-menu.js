document.addEventListener("DOMContentLoaded", () => {

    const toggle =
        document.querySelector("#user-menu-toggle");

    const menu =
        document.querySelector("#user-menu");

    const arrow =
        document.querySelector("#user-menu-arrow");


    if (!toggle || !menu) {
        return;
    }


    function openMenu() {
        const notificationsMenu =
            document.querySelector("#notifications-menu");

        if (notificationsMenu) {
            notificationsMenu.classList.add("hidden");
        }

        menu.classList.remove("hidden");

        toggle.setAttribute(
            "aria-expanded",
            "true"
        );

        if (arrow) {
            arrow.classList.add("rotate-180");
        }

    }


    function closeMenu() {

        menu.classList.add("hidden");

        toggle.setAttribute(
            "aria-expanded",
            "false"
        );

        if (arrow) {
            arrow.classList.remove("rotate-180");
        }

    }


    function toggleMenu() {

        if (menu.classList.contains("hidden")) {

            openMenu();

        } else {

            closeMenu();

        }

    }


    // Abrir / cerrar
    toggle.addEventListener("click", (event) => {

        event.stopPropagation();

        toggleMenu();

    });


    // Evitar cerrar al hacer clic dentro
    menu.addEventListener("click", (event) => {

        event.stopPropagation();

    });


    // Cerrar haciendo clic fuera
    document.addEventListener("click", () => {

        closeMenu();

    });


    // Cerrar con ESC
    document.addEventListener("keydown", (event) => {

        if (event.key === "Escape") {

            closeMenu();

            toggle.focus();

        }

    });

});