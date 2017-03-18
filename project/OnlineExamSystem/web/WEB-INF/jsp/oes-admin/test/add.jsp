<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" uri="/WEB-INF/tlds/template" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<t:oesPage pageTitle="Manage tests">
    <jsp:attribute name="customHead">
        <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/v/bs-3.3.7/dt-1.10.13/datatables.min.css"/>
    </jsp:attribute>
    <jsp:attribute name="customBeginning">
    </jsp:attribute>
    <jsp:attribute name="customEnding">
        <script type="text/javascript" src="https://cdn.datatables.net/v/bs-3.3.7/dt-1.10.13/datatables.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/1000hz-bootstrap-validator/0.11.9/validator.min.js"></script>
        <script>
            $(document).ready(function () {
                $('table').DataTable();
            });
        </script>
    </jsp:attribute>
    <jsp:body>
        <form class="form-horizontal" role="form" data-toggle="validator">
            <div class="box-body">
                <div class="form-group">
                    <label class="col-sm-2 control-label">Course</label>
                    <div class="col-sm-8 col-md-6">
                        <select name="course" class="form-control">
                            <c:forEach items="${courses}" var="course">
                                <option value="${course.id}">${course.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
                <div class="form-group">
                    <label for="name" class="col-sm-2 control-label">Test Name</label>
                    <div class="col-sm-8 col-md-6">
                        <input placeholder="e.g. Progress Test 1" id="name" name="name" type="text" class="form-control" required>
                        <div class="help-block with-errors"></div>
                    </div>
                </div>
                <div class="form-group">
                    <label for="startTime" class="col-sm-2 control-label">Start Join Time</label>
                    <div class="col-sm-8 col-md-6">
                        <input placeholder="e.g. 2017-03-14 10:00:00" id="startTime" name="startTime" type="text" class="form-control" pattern="(\d{4})-(\d{2})-(\d{2}) (\d{2}):(\d{2}):(\d{2})" required data-pattern-error="Time must be formatted as yyyy-MM-dd hh:mm:ss">
                        <div class="help-block with-errors"></div>
                    </div>
                </div>
                <div class="form-group">
                    <label for="endTime" class="col-sm-2 control-label">End Join Time</label>
                    <div class="col-sm-8 col-md-6">
                        <input placeholder="e.g. 2017-03-14 12:00:00" id="endTime" name="endTime" type="text" class="form-control" pattern="(\d{4})-(\d{2})-(\d{2}) (\d{2}):(\d{2}):(\d{2})" required data-pattern-error="Time must be formatted as yyyy-MM-dd hh:mm:ss">
                        <div class="help-block with-errors"></div>
                    </div>
                </div>
                <div class="form-group">
                    <label for="length" class="col-sm-2 control-label">Length</label>
                    <div class="col-sm-8 col-md-6">
                        <input placeholder="in minutes, e.g. 120" id="length" name="length" type="number" class="form-control" step="1" min="1" required>
                        <div class="help-block with-errors"></div>
                    </div>
                </div>
                <div class="form-group">
                    <label for="attempt" class="col-sm-2 control-label">Attempt Limit</label>
                    <div class="col-sm-8 col-md-6">
                        <input placeholder="e.g. 5" id="attemptLimit" name="attempt" type="number" class="form-control" step="1" min="1" required>
                        <div class="help-block with-errors"></div>
                    </div>
                </div>
            </div>
        </form>
    </jsp:body>
</t:oesPage>