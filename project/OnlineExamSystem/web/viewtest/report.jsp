<%-- 
    Document   : report
    Created on : Mar 11, 2017, 10:31:29 AM
    Author     : Niles
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <%@include file="/WEB-INF/jspf/head.jspf" %>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Test Report</title>
    </head>
    <body>
        <%@include file="/WEB-INF/jspf/navbar.jspf" %>
        <div id="testInfo">
            <h2 class="card-title">${test.getName()}</h2>
            <ul class="collection">
                <li class="collection-item">Test ID: &nbsp; ${test.getId()}</li>
                <li class="collection-item">Join Start Time: &nbsp; ${test.getJoinStartTime()}</li>
                <li class="collection-item">Join End Time: &nbsp; ${test.getJoinEndTime()}</li>
                <li class="collection-item">Max Attempt: &nbsp; ${test.getAttemptLimit()}</li>
                <li class="collection-item">Time Length: &nbsp; ${test.getTimeLength()}</li>
                <li class="collection-item">Test Type: &nbsp; ${(test.isRestricted()) ? "Private" : "Public"}</li>
            </ul>
        </div>
        <div id="testReport">
            <table class="highlight teal lighten-3">
                <thead>
                    <tr>
                        <th>Username</th>
                        <th>Full Name</th>
                        <th>Score</th>
                        <th>Start Time</th>
                        <th>End Time</th>
                    </tr>
                </thead>

                <tbody>
                    <c:forEach items="${attempts}" var="attempt">
                        <tr>
                            <td>${attempt.getExaminee().getUsername()}</td>
                            <td>${attempt.getExaminee().getFullName()}</td>
                            <td>${attempt.getScore()}</td>
                            <td>${attempt.getStartTime()}</td>
                            <td>${attempt.getEndTime()}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
        <%@include file="/WEB-INF/jspf/footer.jspf" %>
    </body>
</html>
