<!DOCTYPE html>
<html>
    <head>
        <%@include file="/WEB-INF/jspf/head.jspf" %>
        <title>Error</title>
    </head>
    <body>
        <%@include file="/WEB-INF/jspf/navbar.jspf" %>
        <main>
            <div class="container">
                <div class="row">
                    <div class="col s10">
                        <h5>${param.error}</h5>
                    </div>
                </div>
            </div>
        </main>
        <%@include file="/WEB-INF/jspf/footer.jspf" %>
    </body>
</html>
