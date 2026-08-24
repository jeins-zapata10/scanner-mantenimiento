document.addEventListener("DOMContentLoaded", () => {

    const sidebar = document.querySelector("#sidebar");

    const backdrop =
        document.querySelector("#sidebar-backdrop");

    const openButton =
        document.querySelector("#sidebar-open");

    const closeButton =
        document.querySelector("#sidebar-close");

    const dashboardToggle =
        document.querySelector("#dashboard-toggle");

    const dashboardMenu =
        document.querySelector("#dashboard-menu");

    const dashboardArrow =
        document.querySelector("#dashboard-arrow");

    const expandButton =
        document.querySelector("#sidebar-expand");

    const expandIcon =
        document.querySelector("#sidebar-expand-icon");

    const inventoryToggle =
        document.querySelector("#inventory-toggle");

    const inventoryMenu =
        document.querySelector("#inventory-menu");

    const inventoryArrow =
        document.querySelector("#inventory-arrow");

    // ==========================================
    // INVENTARIO
    // ==========================================

    if (inventoryToggle && inventoryMenu) {

        inventoryToggle.addEventListener("click", () => {

            inventoryMenu.classList.toggle("hidden");

            if (inventoryArrow) {

                inventoryArrow.classList.toggle(
                    "rotate-180"
                );

            }

        });

    }

    function expandDesktopSidebar() {

        if (!sidebar) return;

        sidebar.classList.remove("lg:w-20");
        sidebar.classList.add("lg:w-64");

        document.body.classList.add(
            "sidebar-expanded"
        );

        if (expandIcon) {
            expandIcon.classList.add("rotate-180");
        }

        localStorage.setItem(
            "sidebar-expanded",
            "true"
        );

        const sidebarTexts =
            document.querySelectorAll(".sidebar-text");

        sidebarTexts.forEach((text) => {

            text.classList.remove("lg:hidden");

        });

        const collapsedLabels =
            document.querySelectorAll(
                ".sidebar-collapsed-label"
            );

        collapsedLabels.forEach((label) => {
            label.classList.add("lg:hidden");
        });


        const submenus =
            document.querySelectorAll(
                ".sidebar-submenu"
            );

        submenus.forEach((submenu) => {
            submenu.classList.remove("lg:hidden");
        });
    }


    function collapseDesktopSidebar() {

        if (!sidebar) return;

        sidebar.classList.remove("lg:w-64");
        sidebar.classList.add("lg:w-20");

        document.body.classList.remove(
            "sidebar-expanded"
        );

        if (expandIcon) {
            expandIcon.classList.remove("rotate-180");
        }

        localStorage.setItem(
            "sidebar-expanded",
            "false"
        );

        const sidebarTexts =
            document.querySelectorAll(".sidebar-text");

        sidebarTexts.forEach((text) => {

            text.classList.add("lg:hidden");

        });

        const collapsedLabels =
            document.querySelectorAll(
                ".sidebar-collapsed-label"
            );

        collapsedLabels.forEach((label) => {
            label.classList.remove("lg:hidden");
        });


        const submenus =
            document.querySelectorAll(
                ".sidebar-submenu"
            );

        submenus.forEach((submenu) => {
            submenu.classList.add("lg:hidden");
        });
    }
    if (expandButton) {

        expandButton.addEventListener("click", () => {

            const expanded =
                document.body.classList.contains(
                    "sidebar-expanded"
                );

            if (expanded) {

                collapseDesktopSidebar();

            } else {

                expandDesktopSidebar();

            }

        });

    }


    function openSidebar() {

        if (!sidebar) return;

        sidebar.classList.remove("-translate-x-64");

        if (backdrop) {

            backdrop.classList.remove(
                "opacity-0",
                "pointer-events-none"
            );

            backdrop.classList.add("opacity-100");
        }
    }


    function closeSidebar() {

        if (!sidebar) return;

        sidebar.classList.add("-translate-x-64");

        if (backdrop) {

            backdrop.classList.remove("opacity-100");

            backdrop.classList.add(
                "opacity-0",
                "pointer-events-none"
            );
        }
    }


    // ABRIR

    if (openButton) {

        openButton.addEventListener("click", () => {
            openSidebar();
        });

    }


    // CERRAR

    if (closeButton) {

        closeButton.addEventListener("click", () => {
            closeSidebar();
        });

    }


    // CERRAR TOCANDO EL FONDO

    if (backdrop) {

        backdrop.addEventListener("click", () => {
            closeSidebar();
        });

    }


    // CERRAR CON ESC

    document.addEventListener("keydown", (event) => {

        if (event.key === "Escape") {
            closeSidebar();
        }

    });


    // SUBMENÚ DASHBOARD

    if (dashboardToggle && dashboardMenu) {

        dashboardToggle.addEventListener("click", () => {

            dashboardMenu.classList.toggle("hidden");

            if (dashboardArrow) {
                dashboardArrow.classList.toggle("rotate-180");
            }

        });

    }

    const savedSidebarState =
        localStorage.getItem("sidebar-expanded");


    if (savedSidebarState === "true") {

        expandDesktopSidebar();

    } else {

        collapseDesktopSidebar();

    }

});