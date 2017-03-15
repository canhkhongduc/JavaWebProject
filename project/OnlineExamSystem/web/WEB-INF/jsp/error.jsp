<%-- 
    Document   : Homepage
    Created on : Feb 15, 2017, 4:39:41 PM
    Author     : Canh Khong Duc <canhkdse04533 at FPT University>
--%>

<%@page import="util.servlet.HttpStatus"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <%@include file="/WEB-INF/jspf/head.jspf" %>
        <title>Error</title>
        <%
            int statusCode = (int) request.getAttribute("javax.servlet.error.status_code");
            HttpStatus status = util.servlet.HttpStatus.getByCode(statusCode);
            String name = status.getName();
            String message = (String) request.getAttribute("javax.servlet.error.message");
            if (message == null || message.isEmpty()) {
                message = status.getDescription();
            }
        %>
    </head>
    <body>
        <%@include file="/WEB-INF/jspf/navbar.jspf" %>
        <main>
            <div class="container">
                <h3>Error</h3>
                <p><%=message%></p>
                <%if (statusCode >= 500) {%>
                <p>Details of the error can be found in the server's log.</p>
                <%}%>
                <pre><i>Status code: <%=statusCode%> (<%=name%>).</i></pre>
                <a class="btn waves-effect waves-light" href="${contextPath}/">Back to main page</a>
            </div>
        </main>
        <%@include file="/WEB-INF/jspf/footer.jspf" %>
    </body>
</html>
