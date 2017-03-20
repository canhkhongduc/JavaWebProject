<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="model.AccountProfile"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="util.CommonUtil"%>
<%@page import="java.util.Optional"%>
<%@page import="java.util.stream.Collectors"%>
<%@page import="model.Role"%>
<%@page import="model.Account"%>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" uri="/WEB-INF/tlds/template" %>
<%
    Account account = (Account) request.getAttribute("account");
    AccountProfile profile = account.getProfile();

    String fullName = CommonUtil.getNullable(profile.getFullName(), "");
    String email = CommonUtil.getNullable(profile.getEmail(), "");
    boolean gender = CommonUtil.getNullable(profile.getGender(), true);
    String birthDate = CommonUtil.convertNullable(
            profile.getBirthdate(),
            "",
            (date) -> new SimpleDateFormat("dd/MM/yyyy").format(date)
    );

    pageContext.setAttribute("fullName", fullName);
    pageContext.setAttribute("email", email);
    pageContext.setAttribute("gender", gender);
    pageContext.setAttribute("birthDate", birthDate);
%>
<t:oesPage pageTitle="Update account">
    <jsp:attribute name="customHead">
        <link rel="stylesheet" href="${contextPath}/plugins/bootstrap-datepicker/css/bootstrap-datepicker3.min.css">
    </jsp:attribute>
    <jsp:attribute name="customBeginning">

    </jsp:attribute>
    <jsp:attribute name="customEnding">
        <script src="${contextPath}/plugins/bootstrap-datepicker/js/bootstrap-datepicker.min.js"></script>
        <script>
            $('#inpBirthDate').datepicker({
                format: 'dd/mm/yyyy',
                endDate: '0d',
                defaultViewDate: {year: 2000, month: 1, day: 1}
            });
        </script>
    </jsp:attribute>
    <jsp:body>
        <div class="container-fluid">
            <div class="row">
                <div class="col-sm-10 col-md-8 col-lg-6">
                    <div class="box box-primary">
                        <form action="update" method="POST" accept-charset="UTF-8">
                            <input type="hidden" name="username" value="${account.username}">
                            <div class="box-body">
                                <div class="form-group">
                                    <label for="inpUsername">Username</label>
                                    <input type="text" id="inpUsername" class="form-control" placeholder="Username" value="${account.username}" readonly>
                                </div>
                                <div class="form-group">
                                    <label for="inpFullName">Full name</label>
                                    <input type="text" name="fullName" id="inpFullName" class="form-control" placeholder="Full name" value="${fullName}" required>
                                </div>
                                <div class="form-group">
                                    <label for="inpEmail">Email</label>
                                    <input type="email" name="email" id="inpEmail" class="form-control" placeholder="Email" value="${email}">
                                </div>
                                <div class="form-group">
                                    <label for="grpGender">Gender</label>
                                    <div id="grpGender">
                                        <label class="radio-inline">
                                            <input type="radio" name="gender" value="true" ${gender ? 'checked' : ''}>Male
                                        </label>
                                        <label class="radio-inline">
                                            <input type="radio" name="gender" value="false" ${!gender ? 'checked' : ''}>Female
                                        </label>
                                    </div>
                                </div>
                                <div class="form-group date" data-provider="datepicker">
                                    <label for="inpBirthDate">Birth date</label>
                                    <input type="text" name="birthDate" id="inpBirthDate" class="form-control" placeholder="dd/mm/yyyy" value="${birthDate}">
                                </div>
                                <div class="form-group">
                                    <label for="cbxRole">Role</label>
                                    <select name="role" id="cbxRole" class="form-control">
                                        <c:forEach var="role" items="${roles}">
                                            <option value="${role.name}" ${account.hasRole(role) ? 'selected' : ''}>${role.description}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <div class="box-footer">
                                <button type="submit" class="btn btn-primary">Update</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </jsp:body>
</t:oesPage>