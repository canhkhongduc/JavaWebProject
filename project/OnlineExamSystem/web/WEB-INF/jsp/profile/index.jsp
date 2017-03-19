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
    final String NULL_STRING = "None";
    Account currentUser = (Account) session.getAttribute("currentUser");
    AccountProfile profile = currentUser.getProfile();
    
    String username = currentUser.getUsername();
    String fullName = CommonUtil.getNullable(profile.getFullName(), NULL_STRING);
    String email = CommonUtil.getNullable(profile.getEmail(), NULL_STRING);
    String gender = CommonUtil.convertNullable(
            profile.getGender(),
            NULL_STRING,
            ( g) -> g ? "Male" : "Female"
    );
    String birthDate = CommonUtil.convertNullable(
            profile.getBirthdate(),
            NULL_STRING,
            ( date) -> SimpleDateFormat.getDateInstance().format(date)
    );
    String roles = CommonUtil.toSequenceString(currentUser.getRoles(), ( role) -> role.getDescription());

    pageContext.setAttribute("username", username);
    pageContext.setAttribute("fullName", fullName);
    pageContext.setAttribute("email", email);
    pageContext.setAttribute("gender", gender);
    pageContext.setAttribute("birthDate", birthDate);
    pageContext.setAttribute("roles", roles);
%>
<t:oesPage pageTitle="View profile">
    <jsp:attribute name="customHead">

    </jsp:attribute>
    <jsp:attribute name="customBeginning">

    </jsp:attribute>
    <jsp:attribute name="customEnding">

    </jsp:attribute>
    <jsp:body>
        <div class="container-fluid">
            <div class="row">
                <div class="col-sm-8 col-md-6 col-lg-4">
                    <div class="box box-widget widget-user">
                        <div class="widget-user-header bg-blue">
                            <h3 class="widget-user-username">${fullName}</h3>
                            <h5 class="widget-user-desc">${roles}</h5>
                        </div>
                        <div class="box-body no-padding">
                            <ul class="nav nav-stacked">
                                <li><a href="#">Username <span class="pull-right">${username}</span></a></li>
                                <li><a href="#">Email <span class="pull-right">${email}</span></a></li>
                                <li><a href="#">Gender <span class="pull-right">${gender}</span></a></li>
                                <li><a href="#">Birth date <span class="pull-right">${birthDate}</span></a></li>
                            </ul>
                        </div>
                        <div class="box-footer">
                            <a class="pull-right btn btn-primary" href="profile/update">Update</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </jsp:body>
</t:oesPage>