<%-- 
    Document   : remove
    Created on : Mar 19, 2017, 9:41:30 AM
    Author     : Quang Minh
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Edit Question</h1>
        <table border="1px">
            <tr>
                <th>ID</th>
                <th>Question</th>
                <th></th>
            </tr>
            <!--for each loop -->
            <c:forEach items="${questions}" var="question">
                <tr>
                    <td>${question.id}</td>
                    <td>${question.content}</td>
                    <td>
                        <a href="editquestion?questionID=${question.id}">Edit</a>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </body>
</html>
