<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" uri="/WEB-INF/tlds/template" %>
<t:oesPage pageTitle="Reporting">
    <jsp:attribute name="customHead">
        <link rel="stylesheet" type="text/css" href="${contextPath}/plugins/dataTables/media/css/dataTables.bootstrap.min.css">
    </jsp:attribute>
    <jsp:attribute name="customEnding">
        <script src="${contextPath}/plugins/dataTables/media/js/jquery.dataTables.min.js"></script>
        <script src="${contextPath}/plugins/dataTables/media/js/dataTables.bootstrap.min.js"></script>
        <script>
            $(document).ready(function() {
                $('#owned-test-table').DataTable({
                    order: [[ 2, 'desc' ]]
                });
            });
        </script>
    </jsp:attribute>
    <jsp:body>
        <div class="container-fluid">
            <div class="row">
                <div class="col-sm-12 col-md-9 col-lg-6">
                    <div class="box">
                        <div class="box-header">
                            <h3 class="box-title">List of your own test</h3>
                            <div class="box-tools">
                            </div>
                        </div>
                        <div class="box-body table-responsive">
                            <table id="owned-test-table" class="table table-hover dataTable">
                                <thead>
                                    <tr>
                                        <th>Name</th>
                                        <th>Start time</th>
                                        <th>Time length</th>
                                        <th>Actions</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="test" items="${ownedTests}">
                                        <tr>
                                            <td>${test.name}</td>
                                            <td><fmt:formatDate type="both" value="${test.joinStartTime}"/></td>
                                            <td>${test.timeLength}&nbsp;${test.timeLength <= 1 ? 'minute' : 'minutes'}</td>
                                            <td><a role="button" href="report/attempt/view?testId=${test.id}">View attempts</a></td>
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