$(document).ready(function() {
    console.log(window.location.pathname);
    $('ul.sidebar-menu li').each(function() {
        if (window.location.pathname.startsWith($(this).find('a').attr('href'))) {
            $(this).addClass('active');
        }
    });
});