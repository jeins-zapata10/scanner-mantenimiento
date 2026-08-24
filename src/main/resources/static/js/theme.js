document.addEventListener("DOMContentLoaded", () => {

    const themeToggle =
        document.querySelector("#theme-toggle");


    if (!themeToggle) {
        return;
    }


    themeToggle.addEventListener("click", () => {

        const html = document.documentElement;

        const darkMode =
            html.classList.contains("dark");


        // Si actualmente está oscuro
        if (darkMode) {

            html.classList.remove("dark");

            html.style.colorScheme = "light";

            localStorage.theme = "light";

        }

        // Si actualmente está claro
        else {

            html.classList.add("dark");

            html.style.colorScheme = "dark";

            localStorage.theme = "dark";

        }

    });

});