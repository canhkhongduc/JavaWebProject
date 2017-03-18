<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" uri="/WEB-INF/tlds/template" %>
<t:oesPage pageTitle="View attempts">
    <jsp:attribute name="customHead">
        <link rel="stylesheet" type="text/css" href="${contextPath}/plugins/dataTables/media/css/dataTables.bootstrap.min.css">
    </jsp:attribute>
    <jsp:attribute name="customEnding">
        <script src="${contextPath}/plugins/dataTables/media/js/jquery.dataTables.min.js"></script>
        <script src="${contextPath}/plugins/dataTables/media/js/dataTables.bootstrap.min.js"></script>
        <script>
            $(document).ready(function() {
                var ownedTestTable = $('#owned-test-table').DataTable();
            });
        </script>
    </jsp:attribute>
    <jsp:body>
        <div class="container-fluid">
            <div class="row">
                <div class="col-sm-12 col-md-12 col-lg-9">
                    <div class="box">
                        <div class="box-header">
                            <h3 class="box-title">Attempts submitted for '${test.name}'</h3>
                            <div class="box-tools">
                            </div>
                        </div>
                        <div class="box-body table-responsive">
                            <table id="owned-test-table" class="table table-hover dataTable">
                                <thead>
                                    <tr>
                                        <th>ID</th>
                                        <th>Examinee</th>
                                        <th>Start time</th>
                                        <th>End time</th>
                                        <th>Score</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="attempt" items="${testAttempts}">
                                        <tr>
                                            <td>${attempt.id}</td>
                                            <td>${attempt.examinee.profile.fullName} (${attempt.examinee.username})</td>
                                            <td><fmt:formatDate type="both" value="${attempt.startTime}"/></td>
                                            <td><fmt:formatDate type="both" value="${attempt.endTime}"/></td>
                                            <td><fmt:formatNumber type="number" maxFractionDigits="2" value="${attempt.score}"/></td>
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