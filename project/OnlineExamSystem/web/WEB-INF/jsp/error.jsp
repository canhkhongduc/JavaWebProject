<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="util.servlet.HttpStatus"%>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" uri="/WEB-INF/tlds/template" %>
<%
    int statusCode = (int) request.getAttribute("javax.servlet.error.status_code");
    HttpStatus status = util.servlet.HttpStatus.getByCode(statusCode);
    String name = status.getName();
    String message = (String) request.getAttribute("javax.servlet.error.message");
    if (message == null || message.isEmpty()) {
        message = status.getDescription();
    }
    pageContext.setAttribute("statusCode", statusCode);
    pageContext.setAttribute("name", name);
    pageContext.setAttribute("message", message);
%>
<t:oesPage pageTitle="Error">
    <jsp:body>
        <div class="container">
            <div class="row">
                <div class="col-sm-2"><h1 class="text-red" style="font-size: 6em;">${statusCode}</h1></div>
                <div class="col-sm-10">
                    <h3><i class="fa fa-warning text-red"></i>&nbsp;${name}</h3>
                    <p>${message}</p>
                    <c:if test="${statusCode >= 500}">
                        <p>Details of the error can be found in server's log file.</p>
                    </c:if>
                    <a class="btn btn-default bg-yellow" href="${contextPath}/">Back to homepage</a>
                </div>
            </div>
        </div>
    </jsp:body>
</t:oesPage>