<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" uri="/WEB-INF/tlds/template" %>
<t:oesPage pageTitle="Attempts">
    <jsp:attribute name="customHead">
        <link rel="stylesheet" type="text/css" href="${contextPath}/plugins/dataTables/media/css/dataTables.bootstrap.min.css">
    </jsp:attribute>
    <jsp:attribute name="customEnding">
        <script src="${contextPath}/plugins/dataTables/media/js/jquery.dataTables.min.js"></script>
        <script src="${contextPath}/plugins/dataTables/media/js/dataTables.bootstrap.min.js"></script>
        <script>
            $(document).ready(function () {
                $('#tblAttempts').DataTable({
                    order: [[2, 'desc']]
                });
                $('#cbxViewMode').change(function() {
                    $('#frmViewMode').submit();
                });
            });
        </script>
    </jsp:attribute>
    <jsp:body>
        <div class="container-fluid">
            <div class="row">
                <div class="col-sm-12">
                    <div class="box">
                        <div class="box-header">
                            <h3 class="box-title">List of your submitted attempt(s)</h3>
                            <div class="box-tools">
                                <form id="frmViewMode" class="form-inline" action="attempt" method="GET">
                                    <div class="form-group">
                                        <label class="control-label" for="cbxViewMode">For each test, view</label>
                                        <select id="cbxViewMode" class="form-control input-sm" name="viewMode">
                                            <option value="latest" ${((empty viewMode) || !(viewMode ne 'all')) ? 'selected' : ''}>Only the latest attempt</option>
                                            <option value="all" ${viewMode eq 'all' ? 'selected' : ''}>All attempts</option>
                                        </select>
                                    </div>
                                </form>
                            </div>
                        </div>
                        <div class="box-body table-responsive">
                            <table id="tblAttempts" class="table table-hover dataTable">
                                <thead>
                                    <tr>
                                        <th>Test</th>
                                        <th>Start time</th>
                                        <th>Submit time</th>
                                        <th>Score</th>
                                        <th>Action</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="attempt" items="${examineeAttempts}">
                                        <tr>
                                            <td>${attempt.test.name}</td>
                                            <td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${attempt.startTime}"/></td>
                                            <td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${attempt.endTime}"/></td>
                                            <td><fmt:formatNumber type="number" maxFractionDigits="2" value="${attempt.score}"/></td>
                                            <td><a href="attempt/result?attemptId=${attempt.id}" class="btn btn-primary btn-xs">Details</a></td>
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