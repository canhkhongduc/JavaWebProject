<%-- 
    Document   : SubjectView
    Created on : Feb 14, 2017, 7:54:32 PM
    Author     : Canh Khong Duc <canhkdse04533 at FPT University>
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Courses</title>
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
        </style>
    </head>
    <body>
        <jsp:include page="Navbar.jsp"></jsp:include>
            <div class="container">

                <ul class="collapsible highlight" data-collapsible="accordion">
                    <li>
                        <div class="collapsible-header teal text-darken-2"><i class="material-icons">subject</i>Java Web Application</div>
                        <div class="collapsible-body"><span>Course creator:<a href="#">Khổng Đức Cảnh</a></span></div>
                        <div class="collapsible-body"><span>Teacher:<a href="#">Phan Đăng Lâm</a></span></div>
                        <div class="collapsible-body"><a class="waves-effect waves-light btn">Enter course</a></div>
                    </li>
                    <li>
                        <div class="collapsible-header teal text-darken-2"><i class="material-icons">subject</i>Introduction to Software Engineering</div>
                        <div class="collapsible-body"><span>Course creator: <a href="#"> Khổng Đức Cảnh</a></span></div>
                        <div class="collapsible-body"><span>Teacher: <a href="#"> Phan Đăng Lâm</a></span></div>
                        <div class="collapsible-body"><a class="waves-effect waves-light btn">Enter course</a></div>
                    </li>
                    <li>
                        <div class="collapsible-header teal text-darken-2"><i class="material-icons">subject</i>OOP with Java</div>
                        <div class="collapsible-body"><span>Course creator: <a href="#"> Khổng Đức Cảnh</a></span></div>
                        <div class="collapsible-body"><span>Teacher: <a href="#"> Phan Đăng Lâm</a></span></div>
                        <div class="collapsible-body"><a class="waves-effect waves-light btn" href="#">Enter course</a></div>
                    </li>
                    <li>
                        <div class="collapsible-header teal text-darken-2"><i class="material-icons">subject</i>Programming with C</div>
                        <div class="collapsible-body"><span>Course creator: <a href="#"> Khổng Đức Cảnh</a></span></div>
                        <div class="collapsible-body"><span>Teacher: <a href="#"> Phan Đăng Lâm</a></span></div>
                        <div class="collapsible-body"><a class="waves-effect waves-light btn">Enter course</a></div>
                    </li>
                    <li>
                        <div class="collapsible-header teal text-darken-2"><i class="material-icons">subject</i>Programming with C</div>
                        <div class="collapsible-body"><span>Course creator: <a href="#"> Khổng Đức Cảnh</a></span></div>
                        <div class="collapsible-body"><span>Teacher: <a href="#"> Phan Đăng Lâm</a></span></div>
                        <div class="collapsible-body"><a class="waves-effect waves-light btn">Enter course</a></div>
                    </li>
                    <li>
                        <div class="collapsible-header teal text-darken-2"><i class="material-icons">subject</i>Programming with C</div>
                        <div class="collapsible-body"><span>Course creator: <a href="#"> Khổng Đức Cảnh</a></span></div>
                        <div class="collapsible-body"><span>Teacher: <a href="#"> Phan Đăng Lâm</a></span></div>
                        <div class="collapsible-body"><a class="waves-effect waves-light btn">Enter course</a></div>
                    </li>
                </ul>
            </div>
        <jsp:include page="Footer.jsp"></jsp:include>
    </body>
</html>
