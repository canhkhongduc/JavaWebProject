<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" uri="/WEB-INF/tlds/template" %>
<t:oesPage pageTitle="Dashboard">
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
                $('.dataTable').DataTable();
            });
        </script>
    </jsp:attribute>
    <jsp:body>
        <div class="container-fluid">
            <div class="row">
                <div class="box">
                    <div class="box-header">
                        <h3 class="box-title">List of questions</h3>
                        <div class="box-tools">
                        </div>
                    </div>
                    <div class="box-body table-responsive">
                        <table class="table table-hover dataTable">
                            <thead>
                                <tr>
                                    <th>Test name</th>
                                    <th>Course</th>
                                    <th>Your attempts</th>
                                    <th>Test attempt limit</th>
                                    <th>Test length</th>
                                    <th>Test start time</th>
                                    <th>Test end time</th>
                                    <th></th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${testList}" var="test" varStatus="stt">
                                    <tr>
                                        <td>${test.getName()}</td>
                                        <td>${test.getCourse().getId()}</td>
                                        <td>${attemptList.get(stt.index)}</td>
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
                </div>
            </div>
        </div>
    </jsp:body>
</t:oesPage>
