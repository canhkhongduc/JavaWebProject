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
                                        <th>Name</th>
                                        <th>Join Start Time</th>
                                        <th>Join End Time</th>
                                        <th>Length</th>
                                        <th>Attempt limit</th>
                                        <th>Owner</th>
                                        <th>Status</th>
                                        <th>Actions</th>
                                    </tr>
                                </thead>
                                <tfoot>
                                    <tr>
                                        <th>Name</th>
                                        <th>Join Start Time</th>
                                        <th>Join End Time</th>
                                        <th>Length</th>
                                        <th>Attempt limit</th>
                                        <th>Owner</th>
                                        <th>Status</th>
                                        <th>Actions</th>
                                    </tr>
                                </tfoot>
                                <tbody>
                                    <c:forEach items="${tests}" var="test">
                                        <tr>
                                            <td><a href="${contextPath}/oes-admin/test/view?id=${test.id}">${test.name}</a></td>
                                            <td><fmt:formatDate type="both" value="${test.joinStartTime}"/></td>
                                            <td><fmt:formatDate type="both" value="${test.joinEndTime}"/></td>
                                            <td>${test.timeLength}&nbsp;${test.timeLength > 1 ? 'minutes' : 'minute'}</td>
                                            <td>${test.attemptLimit}</td>
                                            <td>${test.owner.username}</td>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${test.restricted}">Private</c:when>
                                                    <c:otherwise>Public</c:otherwise>
                                                </c:choose>
                                            </td>
                                            <td>
                                                <div class="btn-group">
                                                    <button class="btn btn-default btn-xs dropdown-toggle" data-toggle="dropdown" type="button" ${test.owner.username ne sessionScope.currentUser.username ? 'disabled' : ''}><span class="fa fa-gear"></span><span class="caret"></span></button>
                                                    <ul class="dropdown-menu dropdown-menu-right">
                                                        <li><a href="${contextPath}/oes-admin/test/edit?id=${test.id}"><i class="fa fa-edit"></i> Edit</a></li>
                                                        <li><a href="${contextPath}/oes-admin/test/delete?id=${test.id}"><i class="fa fa-remove"></i> Delete</a></li>
                                                    </ul>
                                                </div>
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