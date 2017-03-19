<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" uri="/WEB-INF/tlds/template" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<t:oesPage pageTitle="Manage tests">
    <jsp:attribute name="customHead">
        <link rel="stylesheet" type="text/css" href="${contextPath}/plugins/dataTables/media/css/dataTables.bootstrap.min.css">
    </jsp:attribute>
    <jsp:attribute name="customBeginning">
    </jsp:attribute>
    <jsp:attribute name="customEnding">
        <script src="${contextPath}/plugins/dataTables/media/js/jquery.dataTables.min.js"></script>
        <script src="${contextPath}/plugins/dataTables/media/js/dataTables.bootstrap.min.js"></script>
        <script>
            $(document).ready(function () {
                $('#table').DataTable();
            });
        </script>
    </jsp:attribute>
    <jsp:body>
        <div class="container-fluid">
            <div class="row">
                <div class="col-sm-12">
                    <div class="box">
                        <div class="box-header">
                            <h3 class="box-title">List of tests</h3>
                            <div class="box-tools">
                            </div>
                        </div>
                        <div class="box-body table-responsive">
                            <table class="table table-hover dataTable" id="table">
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
                                            <td><fmt:formatDate type="both" value="${test.joinStartTime}"/></td>
                                            <td><fmt:formatDate type="both" value="${test.joinEndTime}"/></td>
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
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </jsp:body>
</t:oesPage>