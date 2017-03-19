<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" uri="/WEB-INF/tlds/template" %>
<t:oesPage pageTitle="Homepage">
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
                <div class="col-sm-4">
                    <c:forEach items="tests" var="test">
                        <div class="box">
                            <div class="box-header with-border">
                                <h3 class="box-title">Box header 1</h3>
                                <div class="box-tools pull-right">
                                </div>
                            </div>
                            <div class="box-body">
                                <p>Box body 1</p>
                            </div>
                            <div class="box-footer">
                                <p>Box footer 1</p>
                            </div>
                        </div>
                    </c:forEach>
                </div>
                <div class="col-sm-4">
                    <div class="box">
                        <div class="box-header with-border">
                            <h3 class="box-title">Box Header 2</h3>
                            <div class="box-tools pull-right">
                            </div>
                        </div>
                        <div class="box-body">
                            <p>Box Body 2</p>
                        </div>
                        <div class="box-footer">
                            <p>Box Footer 2</p>
                        </div>
                    </div>
                </div>
                <div class="col-sm-4">
                    <div class="box">
                        <div class="box-header with-border">
                            <h3 class="box-title">Box Header 3</h3>
                            <div class="box-tools pull-right">
                            </div>
                        </div>
                        <div class="box-body">
                            <p>Box Body 3</p>
                        </div>
                        <div class="box-footer">
                            <p>Box Footer 3</p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </jsp:body>
</t:oesPage>