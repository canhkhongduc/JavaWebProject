<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" uri="/WEB-INF/tlds/template" %>
<t:oesPage pageTitle="Manage accounts">
    <jsp:attribute name="customHead">
        <link rel="stylesheet" type="text/css" href="${contextPath}/plugins/dataTables/media/css/dataTables.bootstrap.min.css">
    </jsp:attribute>
    <jsp:attribute name="customEnding">
        <script src="${contextPath}/plugins/dataTables/media/js/jquery.dataTables.min.js"></script>
        <script src="${contextPath}/plugins/dataTables/media/js/dataTables.bootstrap.min.js"></script>
        <script>
            $(document).ready(function () {
                var table = $('#tblAccounts').DataTable({
                    order: [[0, 'asc']]
                });
                $('#cbxRoleFilter').change(function () {
                    $('#account-box-title').html('List of ' + $(this).val().toLowerCase());
                    table.column(5).search($(this).val()).draw();
                });
            });
        </script>
    </jsp:attribute>
    <jsp:body>
        <div class="container-fluid">
            <div class="row">
                <div class="col-sm-12 col-md-11 col-lg-10">
                    <div class="box">
                        <div class="box-header">
                            <h3 id="account-box-title" class="box-title">List of accounts</h3>
                            <div class="box-tools">
                                <form class="form-inline">
                                    <div class="form-group">
                                        <label class="control-label" for="cbxRoleFilter">Filter role</label>
                                        <select id="cbxRoleFilter" class="form-control input-sm">
                                            <option value="">All</option>
                                            <c:forEach var="role" items="${roles}">
                                                <option value="${role.description}" ${role eq selectedRole ? 'selected' : ''}>${role.description}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </form>
                            </div>
                        </div>
                        <div class="box-body table-responsive">
                            <table id="tblAccounts" class="table table-hover dataTable">
                                <thead>
                                    <tr>
                                        <th>Username</th>
                                        <th>Full name</th>
                                        <th>Gender</th>
                                        <th>Birth date</th>
                                        <th>Email</th>
                                        <th>Role</th>
                                        <th>Actions</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="account" items="${accounts}">
                                        <tr>
                                            <td>${account.username}</td>
                                            <td>${account.profile.fullName}</td>
                                            <td>${empty account.profile.gender ? 'None' : (account.profile.gender ? 'Male' : 'Female')}</td>
                                            <td>
                                                <c:if test="${empty account.profile.birthdate}">
                                                    None
                                                </c:if>
                                                <c:if test="${!empty account.profile.birthdate}">
                                                    <fmt:formatDate type="date" value="${account.profile.birthdate}"/>
                                                </c:if>
                                            </td>
                                            <td>${empty account.profile.email ? 'Unknown' : account.profile.email}</td>
                                            <td>${account.getRolesDescription()}</td>
                                            <td><a role="button" href="account/update?username=${account.username}">Update</a></td>
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