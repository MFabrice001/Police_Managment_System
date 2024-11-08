document.addEventListener("DOMContentLoaded", () => {
    // Function to include HTML components
    const includes = document.querySelectorAll('[data-include]');
    includes.forEach(include => {
        fetch(include.getAttribute('data-include'))
            .then(response => response.text())
            .then(data => {
                include.innerHTML = data;
            });
    });
});
