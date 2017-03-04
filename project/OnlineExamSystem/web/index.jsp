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
        <%@include file="/WEB-INF/jspf/footer.jspf" %>
    </body>
</html>
