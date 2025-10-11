/*!
    * Start Bootstrap - SB Admin v7.0.7 (https://startbootstrap.com/template/sb-admin)
    * Copyright 2013-2023 Start Bootstrap
    * Licensed under MIT (https://github.com/StartBootstrap/startbootstrap-sb-admin/blob/master/LICENSE)
    */

window.addEventListener('DOMContentLoaded', event => {
    const sidebarToggle = document.body.querySelector('#sidebarToggle');
    if (sidebarToggle) {
        if (localStorage.getItem('sb|sidebar-toggle') !== 'true') {
            document.body.classList.add('sb-sidenav-toggled');
        }
        sidebarToggle.addEventListener('click', event => {
            event.preventDefault();
            document.body.classList.toggle('sb-sidenav-toggled');
            localStorage.setItem(
                'sb|sidebar-toggle',
                !document.body.classList.contains('sb-sidenav-toggled')
            );
        });
    }
});

function goBack() {
    const currentUrl = window.location.href;
    const previousUrl = document.referrer;

    if (previousUrl && previousUrl !== currentUrl) {
        window.history.back();
    } else {
        window.location.href = '/dashboard';
    }
}
