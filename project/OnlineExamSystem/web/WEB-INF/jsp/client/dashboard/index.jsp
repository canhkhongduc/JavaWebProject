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
                    <div class="box box-info">
                        <div class="box-header with-border">
                            <h3 class="box-title">Today's tests</h3>
                            <div class="box-tools pull-right">
                                <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i></button>
                                <button type="button" class="btn btn-box-tool" data-widget="remove"><i class="fa fa-times"></i></button>
                            </div>
                        </div>
                        <div class="box-body">
                            <div class="table-responsive">
                                <c:if test="${tests.size() == 0}">
                                    <div style="text-align: center">
                                        There is no test for today.
                                    </div>
                                </c:if>
                                <c:if test="${tests.size() > 0}">
                                    <table class="table no-margin">
                                        <thead>
                                            <tr>
                                                <th>Name</th>
                                                <th>Join time</th>
                                                <th>Time length</th>
                                                <th>Created by</th>
                                                <th>Status</th>
                                                <th>Action</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach items="${tests}" var="test">
                                            <tr>
                                                <td>${test.name}</td>
                                                <td><fmt:formatDate type="time" value="${test.joinStartTime}"/>&nbsp;-&nbsp;<fmt:formatDate type="time" value="${test.joinEndTime}"/></td>
                                                <td>${test.timeLength}&nbsp;${test.timeLength > 1 ? 'minutes' : 'minute'}</td>
                                                <td>${empty test.owner ? 'Unknown user' : test.owner.profile.fullName}</td>
                                                <td>
                                                    <c:choose>
                                                        <c:when test="${test.getStatus() == 'PENDING'}">
                                                            <span class="label label-danger">Pending</span>
                                                        </c:when>
                                                        <c:when test="${test.getStatus() == 'ONGOING'}">
                                                            <span class="label label-warning">Ongoing</span>
                                                        </c:when>
                                                        <c:when test="${test.getStatus() == 'FINISHED'}">
                                                            <span class="label label-success">Finished</span>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <span class="label label-default">Unknown</span>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </td>
                                                <td>
                                                    <c:if test="${sessionScope.currentUser.hasRole('student') && test.isJoinable()}">
                                                        <a href="test/list?id=${test.getId()}" class="btn btn-primary btn-xs">Join</a>
                                                    </c:if>
                                                </td>
                                            </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>
                                </c:if>
                            </div>
                        </div>
                        <div class="box-footer clearfix">
                            <a href="test" class="btn btn-sm btn-default btn-flat pull-right">View all tests</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </jsp:body>
</t:oesPage>