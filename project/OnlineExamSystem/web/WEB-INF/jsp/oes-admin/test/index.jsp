<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" uri="/WEB-INF/tlds/template" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<t:oesPage pageTitle="Manage tests">
    <jsp:attribute name="customHead">
        <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/v/bs-3.3.7/dt-1.10.13/datatables.min.css"/>
    </jsp:attribute>
    <jsp:attribute name="customBeginning">
    </jsp:attribute>
    <jsp:attribute name="customEnding">
        <script type="text/javascript" src="https://cdn.datatables.net/v/bs-3.3.7/dt-1.10.13/datatables.min.js"></script>
        <script>
            $(document).ready(function () {
                $('#table').DataTable();
            });
        </script>
    </jsp:attribute>
    <jsp:body>
        <table class="table table-striped table-bordered" id="table" cellspacing="0" width="100%">
            <thead>
                <tr>
                    <th data-field="name">Name</th>
                    <th data-field="start">Join Start Time</th>
                    <th data-field="end"> Join End Time</th>
                    <th data-field="length">Length</th>
                    <th data-field="attempt">Attempts</th>
                    <th data-field="owner">Owner</th>
                    <th data-field="status">Status</th>
                </tr>
            </thead>
            <tfoot>
                <tr>
                    <th data-field="name">Name</th>
                    <th data-field="start">Join Start Time</th>
                    <th data-field="end"> Join End Time</th>
                    <th data-field="length">Length</th>
                    <th data-field="attempt">Attempts</th>
                    <th data-field="owner">Owner</th>
                    <th data-field="status">Status</th>
                </tr>
            </tfoot>


            <tbody>
            <c:forEach items="${tests}" var="test">
                <tr>
                    <td>${test.name}</td>
                    <td><fmt:formatDate value="${test.joinStartTime}" pattern="dd/MM/yyyy hh:mm:ss" /></td>
                    <td><fmt:formatDate value="${test.joinEndTime}" pattern="dd/MM/yyyy hh:mm:ss" /></td>
                    <td>${test.timeLength}</td>
                    <td>${test.attemptLimit}</td>
                    <td>${test.owner.username}</td>
                    <td>
                        <c:choose>
                            <c:when test="${test.restricted}">Private</c:when>
                            <c:otherwise>Public</c:otherwise>
                        </c:choose>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </jsp:body>
</t:oesPage>