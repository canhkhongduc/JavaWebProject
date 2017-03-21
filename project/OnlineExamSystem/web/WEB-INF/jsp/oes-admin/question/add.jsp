<%-- 
    Document   : add
    Created on : Mar 18, 2017, 3:07:25 AM
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
        <h1>Adding Question</h1>
        <form action="add" method="POST">
            <table>            
                <tr>
                    <td>Select course:</td>
                    <td>
                        <select name="course">
                            <c:forEach items="${courses}" var="course">
                                <option value="${course.id}">${course.name}</option>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>Question content:</td>
                    <td>
                        <textarea rows="4" cols="50" name="content" ></textarea>
                    </td>
                </tr>
                <tr>
                    <td>
                        Answer choices:
                    </td>
                    <td>
                        Answer 1: <input type="text" name="answer1"><input type="checkbox" name="correct1" value="true"></br>
                        Answer 2: <input type="text" name="answer2"><input type="checkbox" name="correct2" value="true"></br>
                        Answer 3: <input type="text" name="answer3"><input type="checkbox" name="correct3" value="true"></br>
                        Answer 4: <input type="text" name="answer4"><input type="checkbox" name="correct4" value="true"></br>
                    </td>
                </tr>
            </table>
            <input type="submit" value="Add Question">
        </form>
    </body>
</html>

