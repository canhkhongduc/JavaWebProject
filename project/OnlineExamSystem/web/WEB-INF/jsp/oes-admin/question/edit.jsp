<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" uri="/WEB-INF/tlds/template" %>
<t:oesPage pageTitle="Manage questions">
    <jsp:attribute name="customHead">
        <link rel="stylesheet" type="text/css" href="${contextPath}/plugins/select2/css/select2.min.css">
        <link rel="stylesheet" type="text/css" href="${contextPath}/plugins/iCheck/skins/flat/blue.css">
    </jsp:attribute>
    <jsp:attribute name="customBeginning">
    </jsp:attribute>
    <jsp:attribute name="customEnding">
        <script src="${contextPath}/plugins/select2/js/select2.min.js"></script>
        <script src="${contextPath}/plugins/bootstrap-validator/validator.min.js"></script>
        <script src="${contextPath}/plugins/iCheck/icheck.min.js"></script>
        <script>
            $(document).ready(function () {
                $('.select2').select2();
                $('.checkbox-container input[type="checkbox"]').iCheck({
                    checkboxClass: 'icheckbox_flat-blue',
                    radioClass: 'iradio_flat-blue'
                });
            });
        </script>
    </jsp:attribute>
    <jsp:body>
        <div class="container-fluid">
            <form method="POST" class="form-horizontal" role="form" data-toggle="validator">
                <input type="hidden" name="id" value="${question.id}">
                <div class="row">
                    <div class="col-md-10 col-md-offset-1">
                        <div class="box box-primary">
                            <div class="box-header with-border">
                                <h3 class="box-title">Edit question</h3>
                                <div class="box-tools pull-right">
                                    <button type="button" class="btn btn-box-tool" data-widget="collapse">
                                        <i class="fa fa-minus"></i>
                                    </button>
                                </div>
                            </div>
                            <div class="box-body">
                                <div class="form-group">
                                    <label class="col-sm-3 col-md-2 control-label">Course</label>
                                    <div class="col-sm-9 col-md-10">
                                        <select name="course" class="form-control select2" style="width: 100%;" disabled>
                                            <option selected>${question.course.name}</option>
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-3 col-md-2 control-label" for="content">Content</label>
                                    <div class="col-sm-9 col-md-10">
                                        <textarea class="form-control" name="content" id="content" row="5" required>${question.content}</textarea>
                                        <div class="help-block with-errors"></div>
                                    </div>
                                </div>
                                <c:set var="i" scope="page" value="1"/>
                                <c:forEach items="${choices}" var="choice">
                                    <div class="form-group">
                                        <label for="answer${i}" class="col-sm-3 col-md-2 control-label">Answer ${i}</label>
                                        <div class="col-sm-8 col-md-9">
                                            <input id="answer${i}" name="answer${i}" type="text" class="form-control" value="${choice.content}" required>
                                            <div class="help-block with-errors"></div>
                                        </div>
                                        <div class="col-sm-1 checkbox-container">
                                            <input type="checkbox" name="correct${i}" id="correct${i}" value="true" <c:if test="${choice.isCorrect()}">checked</c:if>>
                                        </div>
                                    </div>
                                    <c:set var="i" scope="page" value="${i+1}"/>
                                </c:forEach>
                                <div class="box-footer with-border">
                                    <input type="submit" value="Save Question" class="btn btn-info pull-right">
                                </div>
                            </div>
                        </div>
                    </div>
                </div>                
            </form>
        </div>
    </jsp:body>
</t:oesPage>