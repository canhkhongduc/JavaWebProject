<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" uri="/WEB-INF/tlds/template" %>
<t:oesPage pageTitle="Manage courses">
    <jsp:attribute name="customHead">
        <link rel="stylesheet" type="text/css" href="${contextPath}/plugins/dataTables/media/css/dataTables.bootstrap.min.css">
        <style>
            #tblCourses a.update-button,a.delete-button {
                padding: 2px 6px 2px 6px;
            }
            #btnAddCourse {
                padding: 2px 6px 2px 6px;
            }
        </style>
    </jsp:attribute>
    <jsp:attribute name="customBeginning">
        <div id="dlgAddUpdateCourse" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="dlgAddUpdateCourseTitle">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <form id="frmAddUpdateCourse" accept-charset="UTF-8">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">×</span></button>
                            <h4 class="modal-title" id="dlgAddUpdateCourseTitle">Action</h4>
                        </div>
                        <div class="modal-body">
                            <div class="form-group">
                                <label for="inpCourseId">ID</label>
                                <input id="inpCourseId" type="text" name="id" class="form-control" placeholder="i.e: PRO001" required>
                            </div>
                            <div class="form-group">
                                <label for="inpCourseName">Name</label>
                                <input id="inpCourseName" type="text" name="name" class="form-control" placeholder="i.e: Programming with Alice" required>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="submit" class="btn btn-primary pull-left">Action</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
        <div id="dlgDeleteCourse" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="dlgDeleteCourseTitle">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <form id="frmDeleteCourse" accept-charset="UTF-8">
                        <input type="hidden" name="id">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">×</span></button>
                            <h4 class="modal-title" id="dlgAddUpdateCourseTitle">Delete course</h4>
                        </div>
                        <div class="modal-body">
                            <span id="lblDeleteMessage">If you sure want to delete the course, then click the button below.</span>
                        </div>
                        <div class="modal-footer">
                            <button type="submit" class="btn btn-primary pull-left">Delete</button>
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
                var table = $('#tblCourses').DataTable({
                    order: [[0, 'asc']]
                });
                
                $('#btnAddCourse').click(function() {
                    $('#frmAddUpdateCourse').attr('action', 'course/add');
                    $('#frmAddUpdateCourse').attr('method', 'POST');
                    $('#inpCourseId').removeAttr('readonly');
                    $('#inpCourseId').val('');
                    $('#inpCourseName').val('');
                    $('#frmAddUpdateCourse button[type="submit"]').html('Add');
                    $('#dlgAddUpdateCourseTitle').html('Add course');
                    $('#dlgAddUpdateCourse').modal();
                });
                
                $('#tblCourses a.update-button').click(function() {
                    var courseId = $(this).attr('data-course-id');
                    var rowIndex = table.column().data().indexOf(courseId);
                    var courseName = table.cell(rowIndex, 1).data();
                    $('#frmAddUpdateCourse').attr('action', 'course/update');
                    $('#frmAddUpdateCourse').attr('method', 'POST');
                    $('#inpCourseId').attr('readonly', 'readonly');
                    $('#inpCourseId').val(courseId);
                    $('#inpCourseName').val(courseName);
                    $('#frmAddUpdateCourse button[type="submit"]').html('Update');
                    $('#dlgAddUpdateCourseTitle').html('Update course');
                    $('#dlgAddUpdateCourse').modal();
                });
                
                $('#tblCourses a.delete-button').click(function() {
                    var courseId = $(this).attr('data-course-id');
                    var rowIndex = table.column().data().indexOf(courseId);
                    var courseName = table.cell(rowIndex, 1).data();
                    $('#frmDeleteCourse').attr('action', 'course/delete');
                    $('#frmDeleteCourse').attr('method', 'POST');
                    $('#lblDeleteMessage').html('If you sure want to delete course \'' + courseName + '\', then click the button below.');
                    $('#frmDeleteCourse input[name="id"]').val(courseId);
                    $('#dlgDeleteCourse').modal();
                });
            });
        </script>
    </jsp:attribute>
    <jsp:body>
        <div class="container-fluid">
            <div class="row">
                <div class="col-sm-10 col-md-11 col-lg-10">
                    <div class="box">
                        <div class="box-header">
                            <h3 class="box-title">List of courses</h3>
                            <div class="box-tools">
                                <a id="btnAddCourse" class="add-button btn btn-primary btn-sm pull-right">Add course</a> 
                            </div>
                        </div>
                        <div class="box-body table-responsive">
                            <table id="tblCourses" class="table table-hover dataTable">
                                <thead>
                                    <tr>
                                        <th>ID</th>
                                        <th>Name</th>
                                        <th>Actions</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="course" items="${courses}">
                                    <tr>
                                        <td>${course.id}</td>
                                        <td>${course.name}</td>
                                        <td>
                                            <a role="button" class="update-button btn btn-warning btn-sm" data-course-id="${course.id}">Update</a>
                                            <a role="button" class="delete-button btn btn-danger btn-sm" data-course-id="${course.id}">Delete</a>
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