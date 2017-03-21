<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" uri="/WEB-INF/tlds/template" %>
<t:oesPage pageTitle="Dashboard">
    <jsp:attribute name="customHead">
    </jsp:attribute>
    <jsp:attribute name="customBeginning">
    </jsp:attribute>
    <jsp:attribute name="customEnding">
    </jsp:attribute>
    <jsp:body>
        <div class="table-responsive">
            <table class="table table-hover">
                <thead>
                    <tr>
                        <th>Test name</th>
                        <th>Test attempt limit</th>
                        <th>Test length</th>
                        <th>Test start time</th>
                        <th>Test end time</th>
                        <th></th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${testList}" var="test">
                        <tr>
                            <td>${test.getName()}</td>
                            <td>${test.getAttemptLimit()}</td>
                            <td>${test.getTimeLength()}</td>
                            <td>${test.getJoinStartTime()}</td>
                            <td>${test.getJoinEndTime()}</td>
                            <td><a class="btn btn-primary" href="list?id=${test.getId()}">Join</a></td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>            
        </div>
    </jsp:body>
</t:oesPage>