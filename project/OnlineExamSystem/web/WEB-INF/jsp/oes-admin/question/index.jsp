<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="t" uri="/WEB-INF/tlds/template" %>
<t:oesPage pageTitle="Manage questions">
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
                        <table class="table table-hover table-responsive dataTable">
                            <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>Question</th>
                                    <th>Owner</th>
                                    <th>Actions</th>
                                </tr>
                            </thead>
                            <tfoot>
                                <tr>
                                    <th>ID</th>
                                    <th>Question</th>
                                    <th>Owner</th>
                                    <th>Actions</th>
                                </tr>
                            </tfoot>
                            <tbody>
                                <c:forEach items="${questions}" var="question">
                                    <tr>
                                        <td>${question.id}</td>
                                        <td>${question.content}</td>
                                        <td>${question.owner.username}</td>
                                        <td>
                                            <div class="btn-group">
                                                <button class="btn btn-default btn-xs dropdown-toggle" data-toggle="dropdown" type="button" ${question.owner.username ne sessionScope.currentUser.username ? 'disabled' : ''}><span class="fa fa-gear"></span><span class="caret"></span></button>
                                                <ul class="dropdown-menu dropdown-menu-right">
                                                    <li><a href="${contextPath}/oes-admin/question/edit?id=${question.id}"><i class="fa fa-edit"></i> Edit</a></li>
                                                    <li><a href="${contextPath}/oes-admin/question/delete?id=${question.id}"><i class="fa fa-remove"></i> Delete</a></li>
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
    </jsp:body>
</t:oesPage>