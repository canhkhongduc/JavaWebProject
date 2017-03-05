<!DOCTYPE html>
<html>
    <head>
        <%@include file="/WEB-INF/jspf/head.jspf" %>
        <title>Settings</title>
        <style>
            header, main, footer {
                padding-left: 300px;
            }

            @media only screen and (max-width : 992px) {
                header, main, footer {
                    padding-left: 0;
                }
            }

            a.button-collapse.top-nav {
                position: absolute;
                text-align: center;
                height: 48px;
                width: 48px;
                left: 7.5%;
                top: 0;
                float: none;
                margin-left: 1.5rem;
                color: #000;
                font-size: 36px;
                z-index: 2;
            }

            a.button-collapse.top-nav.full {
                line-height: 122px;
            }
        </style>
    </head>
    <body>
        <header>
            <%@include file="/WEB-INF/jspf/navbar.jspf" %>
            <%@include file="/WEB-INF/jspf/sidenav.jspf" %>
        </header>
        <main>
            <br>
            <br>
            <%@include file="/WEB-INF/jspf/manage_questions.jspf" %>
        </main>
        <%@include file="/WEB-INF/jspf/footer.jspf" %>
        <script>
            $(document).ready(function () {
                $('select').material_select();
            });
            $("#course").change(function() {
                window.location.href = "settings?func=manage_questions&course=" + $(this).val();
            });
        </script>
    </body>
</html>
