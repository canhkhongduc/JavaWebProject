<%-- 
    Document   : Homepage
    Created on : Feb 15, 2017, 4:39:41 PM
    Author     : Canh Khong Duc <canhkdse04533 at FPT University>
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Home page</title>
        <link href="http://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
        <!--Import materialize.css-->
        <link rel="stylesheet" type="text/css" href="css/materialize.min.css"/>
        <!--Import jQuery before materialize.js-->
        <script type="text/javascript" src="https://code.jquery.com/jquery-2.1.1.min.js"></script>
        <script type="text/javascript" src="js/materialize.min.js"></script>
        <style>
            .collapsible-body {
                padding: 1rem;
            }
            body{overflow:auto;}
            .carousel-item{
                width: 80%;
                height: 200px;
            }
        </style>

    </head>
    <body>
        <jsp:include page="Navbar.jsp"></jsp:include>
            <div class="carousel carousel-slider">
                <a class="carousel-item"><img src="image/brucelee.jpg"></a>
                <a class="carousel-item"><img src="image/simonsinek.jpg"></a>
                <a class="carousel-item"><img src="image/gandi.jpg"></a>
                <a class="carousel-item"><img src="image/stevejob.jpg"></a>
            </div>
        <jsp:include page="Footer.jsp"></jsp:include>
        <script>
            $('.carousel.carousel-slider').carousel({fullWidth: true});
        </script>
    </body>
</html>
