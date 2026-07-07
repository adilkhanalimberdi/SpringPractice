document.addEventListener("DOMContentLoaded", () => {
    const menuBtn = document.getElementById("menu-button");
    const menuContainer = document.getElementById("menu-container");

    menuBtn.addEventListener("click", () => {
        menuContainer.classList.toggle('!block')
    });
});