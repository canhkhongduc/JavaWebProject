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
        <table>      
            <tr>
                <td>Question content:</td>
                <td>
                    <textarea rows="4" cols="50" name="content">${question.content}</textarea>
                </td>
            </tr>
            <tr>
                <td>
                    Answer choices:
                </td>
                <td>
                    <c:set var="i" scope="Page" value="1"/>
                    <c:forEach items="${question.getChoices()}" var="choice">
                        Answer ${i}: <input type="text" name="answer${i}" value="${choice.content}"><input type="checkbox" name="correct${i}" value="true" <c:if test="${choice.correct==true}">checked</c:if>></br>
                        <c:set var="i" scope="Page" value="i+1"/>
                    </c:forEach>
                    Answer 1: <input type="text" name="answer1"><input type="checkbox" name="correct1" value="true"></br>
                    Answer 2: <input type="text" name="answer2"><input type="checkbox" name="correct2" value="true"></br>
                    Answer 3: <input type="text" name="answer3"><input type="checkbox" name="correct3" value="true"></br>
                    Answer 4: <input type="text" name="answer4"><input type="checkbox" name="correct4" value="true"></br>
                </td>
            </tr>
        </table>
    </body>
</html>
