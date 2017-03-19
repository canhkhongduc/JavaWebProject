<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" uri="/WEB-INF/tlds/template" %>
<t:oesPage pageTitle="Dashboard">
    <jsp:attribute name="customHead">
    </jsp:attribute>
    <jsp:attribute name="customBeginning">
    </jsp:attribute>
    <jsp:attribute name="customEnding">
    </jsp:attribute>
    <jsp:body>
        <div class="container-fluid">
            <div class="row">
                <div class="col-sm-12">
                    <div class="box">
                        <div class="box-header with-border">
                            <h3 class="box-title">Wide box</h3>
                            <div class="box-tools pull-right">
                            </div>
                        </div>
                        <div class="box-body">
                            <p>Website in construction</p>
                        </div>
                        <div class="box-footer">
                            Wide box footer
                        </div>
                    </div>
                </div>
            </div>

            <div class="row">
                <h3>Incoming test</h3>
                <c:forEach items="${tests}" var="test">
                    <div class="col-sm-4">

                        <div class="box">
                            <div class="box-header with-border">
                                <a href="#"><h3 class="box-title">${test.name}</h3></a>
                                <div class="box-tools pull-right">
                                </div>
                            </div>
                            <div class="box-body">
                                <p><fmt:formatDate value="${test.joinStartTime}" pattern="dd/MM/yyyy hh:mm:ss" /></p>
                                <p><fmt:formatDate value="${test.joinEndTime}" pattern="dd/MM/yyyy hh:mm:ss" /></p>
                            </div>
                            <div class="box-footer">
                                <p>${test.owner.username}</p>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>
    </jsp:body>
</t:oesPage>