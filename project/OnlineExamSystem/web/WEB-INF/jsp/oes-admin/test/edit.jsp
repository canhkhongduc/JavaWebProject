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
            var setTableSelectListener = function (table, name) {
                table.find('tbody').on('click', 'tr', function () {
                    var table = $(this).closest('table');
                    var count = $('#' + table.attr('data-count'));
                    var list = $('#' + table.attr('data-list'));
                    $(this).toggleClass('selected');
                    var id = $(this).attr('data-id');
                    if ($(this).hasClass('selected')) {
                        list.append($('<li>')
                                .addClass('list-group-item')
                                .text($(this).children('td').eq(1).text())
                                .append($('<input>').attr('type', 'hidden').attr('name', name).val(id))
                                .append($('<i>').addClass('btn fa fa-times fa-pull-right btnRemove'))
                                .attr('data-id', id));
                        count.text(parseInt(count.text()) + 1);
                    } else {
                        list.children().each(function () {
                            if ($(this).attr('data-id') === id) {
                                $(this).remove();
                            }
                        });
                        count.text(parseInt(count.text()) - 1);
                    }
                });
            };
            var setListRemoveListener = function (list) {
                list.on('click', '.btnRemove', function () {
                    var table = $('#' + list.attr('data-table'));
                    var count = $('#' + list.attr('data-count'));
                    var id = $(this).parent().attr('data-id');
                    $(this).parent().remove();
                    count.text(parseInt(count.text()) - 1);
                    table.find('tbody').children('.selected').each(function () {
                        if ($(this).attr('data-id') === id) {
                            $(this).removeClass('selected');
                        }
                    });
                });
            };
            var toggleRestricted = function () {
                $('#studentBox').slideToggle();
            };
            $(document).ready(function () {
                $('.dataTable').DataTable();
                setTableSelectListener($('#questionTable'), 'selectedQuestion');
                setListRemoveListener($('#questionList'));
                setTableSelectListener($('#studentTable'), 'selectedStudent');
                setListRemoveListener($('#studentList'));
                $('.select2').select2();
                $('#joinTime').daterangepicker({
                    timePicker: true,
                    timePicker24Hour: true,
                    timePickerIncrement: 1,
                    timePickerSeconds: true,
                    locale: {
                        format: 'MM/DD/YYYY HH:mm:ss'}
                });
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
                                        <select name="course" class="form-control select2" style="width: 100%;">
                                            <c:forEach items="${courses}" var="course">
                                                <option value="${course.id}" <c:if test="${test.course.id == course.id}">selected</c:if>>${course.name}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="name" class="col-sm-3 col-md-2 control-label">Test Name</label>
                                    <div class="col-sm-9 col-md-10">
                                        <input placeholder="e.g. Progress Test 1" id="name" name="name" type="text" class="form-control" required value="${test.name}">
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
                                            <input type="text" class="form-control pull-right" name="joinTime" id="joinTime" value="<fmt:formatDate pattern="MM/dd/yyyy HH:mm:ss" value="${test.joinStartTime}"></fmt:formatDate> - <fmt:formatDate pattern="MM/dd/yyyy HH:mm:ss" value="${test.joinEndTime}"></fmt:formatDate>">
                                            </div>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label for="length" class="col-sm-3 col-md-2 control-label">Length</label>
                                        <div class="col-sm-9 col-md-10">
                                                <input placeholder="in minutes, e.g. 120" id="length" name="length" type="number" class="form-control" step="1" min="1" required value="${test.timeLength}">
                                        <div class="help-block with-errors"></div>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="attempt" class="col-sm-3 col-md-2 control-label">Attempt Limit</label>
                                    <div class="col-sm-9 col-md-10">
                                        <input placeholder="e.g. 5" id="attemptLimit" name="attempt" type="number" class="form-control" step="1" min="1" required value="${test.attemptLimit}">
                                        <div class="help-block with-errors"></div>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="restricted" class="col-sm-3 col-md-2 control-label">Restricted?</label>
                                    <div class="col-sm-9 col-md-10">
                                        <input id="restricted" name="restricted" type="checkbox" class="minimal" value="restricted" onchange="toggleRestricted();" <c:if test="${test.restricted}">checked</c:if>>
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
                                    <div><i><i class="fa fa-info-circle"></i> Click on a question to add it to selection list</i></div>
                                    <br>
                                    <div><b><span id="questionCount">${fn:length(test.questions)}</span></b> question(s) selected.</div>
                                <br>
                                <ul id="questionList" class="list-group" data-table="questionTable" data-count="questionCount">
                                    <c:forEach items="${test.questions}" var="question">
                                        <li class="list-group-item" data-id="${question.id}">
                                            ${question.content}
                                            <input type="hidden" name="selectedQuestion" value="${question.id}">
                                            <i class="fa fa-times fa-pull-right btnRemove"></i>
                                        </li>
                                    </c:forEach>
                                </ul>
                                <table id="questionTable" class="table display table-striped dataTable" data-count="questionCount" data-list="questionList">
                                    <thead>
                                        <tr>
                                            <th>Course</th>
                                            <th>Content</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach items="${questions}" var="question">
                                            <tr data-id="${question.id}" <c:if test="${test.questions.contains(question)}">class="selected"</c:if>>
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
                                <div><i><i class="fa fa-info-circle"></i> Click on a student to add he/she to selection list</i></div>
                                <br>
                                <div><b><span id="studentCount">${fn:length(test.examinees)}</span></b> student(s) selected.</div>
                                <br>
                                <ul id="studentList" class="list-group" data-table="studentTable" data-count="studentCount">
                                    <c:forEach items="${test.examinees}" var="examinee">
                                        <li class="list-group-item" data-id="${examinee.username}">
                                            ${examinee.profile.fullName}
                                            <input type="hidden" name="selectedStudent" value="${examinee.username}">
                                            <i class="fa fa-times fa-pull-right btnRemove"></i>
                                        </li>
                                    </c:forEach>
                                </ul>
                                <table id="studentTable" class="table display table-striped dataTable" data-count="studentCount" data-list="studentList">
                                    <thead>
                                        <tr>
                                            <th>Username</th>
                                            <th>Full name</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach items="${students}" var="student">
                                            <tr data-id="${student.username}" <c:if test="${test.examinees.contains(student)}">class="selected"</c:if>>
                                                <td>${student.username}</td>
                                                <td>${student.profile.fullName}</td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </div> <!-- /Student Box -->
                        <div class="box no-border">
                            <button type="submit" class="btn btn-info pull-right">Save test</button>
                        </div>
                    </div>
                </div>
            </form>
        </div>
    </jsp:body>
</t:oesPage>