document.addEventListener("DOMContentLoaded", () => {
    const activeToast = document.querySelector('[role="alert"]');

    if (activeToast) {
        setTimeout(() => {
            activeToast.style.opacity = '0';

            setTimeout(() => {
                activeToast.remove();
            }, 500);
        }, 5000);
    }

    const closeButton = document.querySelector('[data-dismiss-target]');
    if (closeButton) {
        closeButton.addEventListener('click', () => {
            const targetId = closeButton.getAttribute('data-dismiss-target');
            const toast = document.querySelector(targetId);
            if (toast) {
                toast.style.opacity = '0';
                setTimeout(() => {
                    toast.remove();
                }, 500);
            }
        });
    }

});
