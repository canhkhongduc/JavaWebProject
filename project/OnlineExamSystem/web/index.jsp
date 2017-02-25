<%-- 
    Document   : Homepage
    Created on : Feb 15, 2017, 4:39:41 PM
    Author     : Canh Khong Duc <canhkdse04533 at FPT University>
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <%@include file="/WEB-INF/jspf/head.jspf" %>
        <title>Home page</title>
        <style>
            .collapsible-body {
                padding: 1rem;
            }
            body {
                overflow: auto;
            }
            .carousel-item{
                width: 80%;
                height: 200px;
            }
        </style>
    </head>
    <body>
        <%@include file="/WEB-INF/jspf/navbar.jspf" %>
        <main>
            <div class="carousel carousel-slider">
                <a class="carousel-item"><img src="resources/images/sky.jpg" style="height:200px;"></a>
                <a class="carousel-item"><img src="resources/images/mountain.jpg" style="height:200px;"></a>
                <a class="carousel-item"><img src="resources/images/sea.png" style="height:200px;"></a>
            </div>
        </main>
        <%@include file="/WEB-INF/jspf/footer.jspf" %>
        <script>
            $('.carousel.carousel-slider').carousel({fullWidth: true});
        </script>
    </body>
</html>
