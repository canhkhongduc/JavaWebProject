<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="t" uri="/WEB-INF/tlds/template" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<t:oesPage pageTitle="Manage tests">
    <jsp:attribute name="customHead">
        <link rel="stylesheet" type="text/css" href="${contextPath}/plugins/dataTables/media/css/dataTables.bootstrap.min.css">
        <link rel="stylesheet" type="text/css" href="${contextPath}/plugins/select2/css/select2.min.css">
        <link rel="stylesheet" type="text/css" href="${contextPath}/plugins/daterangepicker/daterangepicker.css">
        <style>
            table.dataTable tbody tr.selected {
                background-color: #acbad4;
            }
        </style>
    </jsp:attribute>
    <jsp:attribute name="customBeginning">
    </jsp:attribute>
    <jsp:attribute name="customEnding">
        <script src="${contextPath}/plugins/dataTables/media/js/jquery.dataTables.min.js"></script>
        <script src="${contextPath}/plugins/dataTables/media/js/dataTables.bootstrap.min.js"></script>
        <script src="${contextPath}/plugins/select2/js/select2.min.js"></script>
        <script src="${contextPath}/plugins/daterangepicker/moment.min.js"></script>
        <script src="${contextPath}/plugins/daterangepicker/daterangepicker.js"></script>
        <script src="${contextPath}/plugins/bootstrap-validator/validator.min.js"></script>
        <script>
            $(document).ready(function () {
                $('.dataTable').DataTable();
                if ($('#restricted')[0].checked)
                    $('#studentBox').show();
                else
                    $('#studentBox').hide();
            });
        </script>
    </jsp:attribute>
    <jsp:body>
        <div class="container-fluid">
            <form class="form-horizontal" role="form" data-toggle="validator" method="POST">
                <div class="row">
                    <div class="col-md-10 col-md-offset-1">
                        <!-- Info Box -->
                        <div class="box box-primary">
                            <div class="box-header with-border">
                                <h3 class="box-title">Test info</h3>
                                <div class="box-tools pull-right">
                                    <button type="button" class="btn btn-box-tool" data-widget="collapse">
                                        <i class="fa fa-minus"></i>
                                    </button>
                                </div>
                            </div>
                            <div class="box-body">
                                <input type="hidden" name="id" value="${test.id}">
                                <div class="form-group">
                                    <label class="col-sm-3 col-md-2 control-label">Course</label>
                                    <div class="col-sm-9 col-md-10">
                                        <select name="course" class="form-control select2" style="width: 100%;" disabled>
                                            <c:forEach items="${courses}" var="course">
                                                <option value="${course.id}" <c:if test="${test.course.id == course.id}">selected</c:if>>${course.name}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="name" class="col-sm-3 col-md-2 control-label">Test Name</label>
                                    <div class="col-sm-9 col-md-10">
                                        <input placeholder="e.g. Progress Test 1" id="name" name="name" type="text" class="form-control" required value="${test.name}" disabled>
                                        <div class="help-block with-errors"></div>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-3 col-md-2 control-label">Join Time Range:</label>
                                    <div class="col-sm-9 col-md-10">
                                        <div class="input-group">
                                            <div class="input-group-addon">
                                                <i class="fa fa-clock-o"></i>
                                            </div>
                                            <input type="text" class="form-control pull-right" name="joinTime" id="joinTime" value="<fmt:formatDate pattern="MM/dd/yyyy HH:mm:ss" value="${test.joinStartTime}"></fmt:formatDate> - <fmt:formatDate pattern="MM/dd/yyyy HH:mm:ss" value="${test.joinEndTime}"></fmt:formatDate>" disabled>
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="length" class="col-sm-3 col-md-2 control-label">Length</label>
                                    <div class="col-sm-9 col-md-10">
                                        <input placeholder="in minutes, e.g. 120" id="length" name="length" type="number" class="form-control" step="1" min="1" required value="${test.timeLength}" disabled>
                                        <div class="help-block with-errors"></div>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="attempt" class="col-sm-3 col-md-2 control-label">Attempt Limit</label>
                                    <div class="col-sm-9 col-md-10">
                                        <input placeholder="e.g. 5" id="attemptLimit" name="attempt" type="number" class="form-control" step="1" min="1" required value="${test.attemptLimit}" disabled>
                                        <div class="help-block with-errors"></div>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="restricted" class="col-sm-3 col-md-2 control-label">Restricted?</label>
                                    <div class="col-sm-9 col-md-10">
                                        <input id="restricted" name="restricted" type="checkbox" class="minimal" value="restricted" onchange="toggleRestricted();" <c:if test="${test.restricted}">checked</c:if> disabled>
                                        <div class="help-block with-errors"></div>
                                    </div>
                                </div>
                            </div>
                        </div> <!-- /Info Box -->
                        <!-- Question Box-->
                        <div class="box box-primary">
                            <div class="box-header with-border">
                                <h3 class="box-title">Questions</h3>
                                <div class="box-tools pull-right">
                                    <button type="button" class="btn btn-box-tool" data-widget="collapse">
                                        <i class="fa fa-minus"></i>
                                    </button>
                                </div>
                            </div>
                            <div class="box-body">
                                <div><b><span id="questionCount">${fn:length(test.questions)}</span></b> question(s) selected.</div>
                                <br>
                                <table id="questionTable" class="table display table-hover dataTable" data-count="questionCount" data-list="questionList">
                                    <thead>
                                        <tr>
                                            <th>Course</th>
                                            <th>Content</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach items="${test.questions}" var="question">
                                            <tr data-id="${question.id}">
                                                <td>${question.course.id}</td>
                                                <td>${question.content}</td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </div> <!-- /Question Box -->
                        <!-- Student Box-->
                        <div id="studentBox" class="box box-primary" style="display: none;">
                            <div class="box-header with-border">
                                <h3 class="box-title">Students</h3>
                                <div class="box-tools pull-right">
                                    <button type="button" class="btn btn-box-tool" data-widget="collapse">
                                        <i class="fa fa-minus"></i>
                                    </button>
                                </div>
                            </div>
                            <div class="box-body">
                                <div><b><span id="studentCount">${fn:length(test.examinees)}</span></b> student(s) selected.</div>
                                <br>
                                <table id="studentTable" class="table display table-hover dataTable" data-count="studentCount" data-list="studentList">
                                    <thead>
                                        <tr>
                                            <th>Username</th>
                                            <th>Full name</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach items="${test.examinees}" var="student">
                                            <tr data-id="${student.username}">
                                                <td>${student.username}</td>
                                                <td>${student.profile.fullName}</td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </div> <!-- /Student Box -->
                    </div>
                </div>
            </form>
        </div>
    </jsp:body>
</t:oesPage>