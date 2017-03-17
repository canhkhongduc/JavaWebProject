<%-- 
    Document   : genericPage
    Created on : Mar 17, 2017, 8:57:47 PM
    Author     : Le Cao Nguyen
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@tag description="Create a generic page." pageEncoding="UTF-8"%>
<%@attribute name="head" description="The content of the head part." fragment="true"%>
<%@attribute name="beginning" description="The beginning content of the page (before header)." fragment="true"%>
<%@attribute name="header" description="The content of the header part." fragment="true"%>
<%@attribute name="main" description="The content of the main part." fragment="true" required="true"%>
<%@attribute name="footer" description="The content of the footer part." fragment="true"%>
<%@attribute name="ending" description="The ending content of the page (after footer)." fragment="true"%>
<!DOCTYPE html>
<html>
    <head>
        <jsp:invoke fragment="head"/>
    </head>
    <body>
        <jsp:invoke fragment="beginning"/>
        <header>
            <jsp:invoke fragment="header"/>
        </header>
        <main>
            <jsp:invoke fragment="main"/>
        </main>
        <footer>
            <jsp:invoke fragment="footer"/>
        </footer>
        <jsp:invoke fragment="ending"/>
    </body>
</html>