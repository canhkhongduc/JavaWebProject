<%-- 
    Document   : SubjectView
    Created on : Feb 14, 2017, 7:54:32 PM
    Author     : Canh Khong Duc <canhkdse04533 at FPT University>
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <%@include file="/WEB-INF/jspf/head.jspf" %>
        <title>Courses</title>
        <style>
            .collapsible-body {
                padding: 1rem;
            }
            body {
                overflow:auto;
            }
        </style>
    </head>
    <body>
        <%@include file="/WEB-INF/jspf/navbar.jspf" %>
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
        <%@include file="/WEB-INF/jspf/footer.jspf" %>
    </body>
</html>
