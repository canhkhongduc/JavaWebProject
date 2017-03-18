$(document).ready(function() {
    $('ul.sidebar-menu li').each(function() {
        if (window.location.pathname == $(this).find('a').attr('href')) {
            $(this).addClass('active');
            if ($(this).parent().parent().hasClass('treeview')) {
                $(this).parent().parent().addClass('active');
            }
        }
    });
});