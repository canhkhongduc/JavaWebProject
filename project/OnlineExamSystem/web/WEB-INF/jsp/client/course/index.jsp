<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" uri="/WEB-INF/tlds/template" %>
<t:oesPage pageTitle="Courses">
    <jsp:attribute name="customHead">
        <link rel="stylesheet" type="text/css" href="${contextPath}/plugins/dataTables/media/css/dataTables.bootstrap.min.css">
        <style>
            #tblCourses a.join-button {
                padding: 2px 6px 2px 6px;
            }
        </style>
    </jsp:attribute>
    <jsp:attribute name="customBeginning">
    </jsp:attribute>
    <jsp:attribute name="customEnding">
        <script src="${contextPath}/plugins/dataTables/media/js/jquery.dataTables.min.js"></script>
        <script src="${contextPath}/plugins/dataTables/media/js/dataTables.bootstrap.min.js"></script>
        <script>
            $(document).ready(function () {
                var table = $('#tblCourses').DataTable({
                    order: [[0, 'asc']]
                });
            });

            $('#tblCourses a.join-button').click(function () {
                var courseId = $(this).attr('data-course-id');
                $('#frmJoinCourse').attr('action', '/client/test/list');
                $('#frmJoinCourse').attr('method', 'POST');
                $('#inpCourseId').val(courseId);
                $('#frmJoinCourse').submit();
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
                                        <td>${course.getId()}</td>
                                        <td>${course.getName()}</td>
                                        <td>
                                            <form id="frmJoinCourse" accept-charset="UTF-8">
                                                <input id="inpCourseId" type="hidden" name="courseId" value=""/>
                                                <a role="button" class="join-button btn btn-info btn-sm" data-course-id="${course.id}">Join Course</a>
                                            </form>
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