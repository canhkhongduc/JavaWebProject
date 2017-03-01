<%-- 
    Document   : Homepage
    Created on : Feb 15, 2017, 4:39:41 PM
    Author     : Canh Khong Duc <canhkdse04533 at FPT University>
--%>

<%@page import="controller.authentication.LoginResult"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <%@include file="/WEB-INF/jspf/head.jspf" %>
        <title>Error</title>
        <%
            String errorId = request.getParameter("errorId");
            LoginResult result = LoginResult.SUCCESS;
            try {
                result = LoginResult.valueOf(errorId.toUpperCase());
            } catch (IllegalArgumentException ex) {
                result = LoginResult.UNKNOWN_ERROR;
            }
        %>
    </head>
    <body>
        <%@include file="/WEB-INF/jspf/navbar.jspf" %>
        <main>
            <h1>Error</h1>
            <p><%=result.getMessage()%></p>
        </main>
        <%@include file="/WEB-INF/jspf/footer.jspf" %>
    </body>
</html>
