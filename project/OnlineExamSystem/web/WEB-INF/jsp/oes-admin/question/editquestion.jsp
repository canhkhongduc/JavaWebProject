<%-- 
    Document   : editquestion
    Created on : Mar 20, 2017, 6:59:02 AM
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
        <h1>Edit question</h1>
        <form action="editquestion" method="POST">
            <table>      
                <tr>
                    <td>Question content:</td>
                    <td>
                        <input type="hidden" name="questionID" value="${question.id}">
                        <textarea rows="4" cols="50" name="content">${question.content}</textarea>
                    </td>
                </tr>
                <tr>
                    <td>
                        Answer choices:
                    </td>
                    <td>
                        <c:set var="i" scope="page" value="1"/>
                        <c:forEach items="${choices}" var="choice">
                            Answer ${i}: <input type="text" name="answer${i}" value="${choice.content}"><input type="checkbox" name="correct${i}" value="true" <c:if test="${choice.isCorrect()==true}">checked</c:if>></br>
                            <c:set var="i" scope="page" value="${i+1}"/>
                        </c:forEach>
                    </td>
                </tr>
            </table>
                        <input type="submit" value="Save">
        </form>

    </body>
</html>
