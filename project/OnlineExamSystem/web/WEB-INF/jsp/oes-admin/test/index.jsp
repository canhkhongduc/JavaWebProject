<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" uri="/WEB-INF/tlds/template" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<t:oesPage pageTitle="Manage tests">
    <jsp:attribute name="customHead">
        <link rel="stylesheet" type="text/css" href="${contextPath}/plugins/dataTables/media/css/dataTables.bootstrap.min.css">
    </jsp:attribute>
    <jsp:attribute name="customBeginning">
        <div id="dlgDeleteTest" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="dlgDeleteTestTitle">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <form id="frmDeleteTest" action="test/delete" method="POST" accept-charset="UTF-8">
                        <input type="hidden" name="id">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">×</span></button>
                            <h4 class="modal-title" id="dlgDeleteTestTitle">Delete test</h4>
                        </div>
                        <div class="modal-body">
                            <span id="lblDeleteMessage">If you sure want to delete this test, then click the button below.</span>
                        </div>
                        <div class="modal-footer">
                            <button type="submit" class="btn btn-danger pull-left">Delete</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </jsp:attribute>
    <jsp:attribute name="customEnding">
        <script src="${contextPath}/plugins/dataTables/media/js/jquery.dataTables.min.js"></script>
        <script src="${contextPath}/plugins/dataTables/media/js/dataTables.bootstrap.min.js"></script>
        <script>
            $(document).ready(function () {
                $('#tblTests').DataTable();
                
                $('#tblTests a.delete-button').click(function() {
                    var testId = $(this).attr('data-test-id');
                    $('#frmDeleteTest input[name="id"]').val(testId);
                    $('#dlgDeleteTest').modal();
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
                            <h3 class="box-title">List of tests</h3>
                            <div class="box-tools">
                            </div>
                        </div>
                        <div class="box-body table-responsive">
                            <table class="table table-hover dataTable" id="tblTests">
                                <thead>
                                    <tr>
                                        <th>Name</th>
                                        <th>Course</th>
                                        <th>Join time</th>
                                        <th>Time length</th>
                                        <th>Attempt limit</th>
                                        <th>Owner</th>
                                        <th>Status</th>
                                        <th>Actions</th>
                                    </tr>
                                </thead>
                                <tfoot>
                                    <tr>
                                        <th>Name</th>
                                        <th>Course</th>
                                        <th>Join time</th>
                                        <th>Time length</th>
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
                                            <td>${test.course.id}</td>
                                            <td><fmt:formatDate pattern="yyyy-MM-dd hh:mm:ss" value="${test.joinStartTime}"/>&nbsp;-&nbsp;<fmt:formatDate pattern="yyyy-MM-dd hh:mm:ss" value="${test.joinEndTime}"/></td>
                                            <td>${test.timeLength}&nbsp;${test.timeLength > 1 ? 'mins' : 'min'}</td>
                                            <td>${test.attemptLimit}</td>
                                            <td>${test.owner.username}</td>
                                            <td>${test.restricted ? 'Private' : 'Public'}</td>
                                            <td>
                                                <div class="btn-group">
                                                    <button class="btn btn-default btn-xs dropdown-toggle" data-toggle="dropdown" type="button" ${test.owner.username ne sessionScope.currentUser.username ? 'disabled' : ''}><span class="fa fa-gear"></span><span class="caret"></span></button>
                                                    <ul class="dropdown-menu dropdown-menu-right">
                                                        <li><a href="${contextPath}/oes-admin/test/edit?id=${test.id}"><i class="fa fa-edit"></i> Edit</a></li>
                                                        <li><a class="delete-button" data-test-id="${test.id}"><i class="fa fa-remove"></i> Delete</a></li>
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