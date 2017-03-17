<%@tag description="Create a template for a JSP page in OES." pageEncoding="UTF-8"%>
<%@taglib prefix="t" uri="/WEB-INF/tlds/template" %>
<%@attribute name="pageTitle" required="true" %>
<%@attribute name="contentTitle" %>
<%@attribute name="customHead" fragment="true" %>
<%@attribute name="customBeginning" fragment="true" %>
<%@attribute name="customEnding" fragment="true" %>
<t:genericPage>
    <jsp:attribute name="head">
        <%@include file="/WEB-INF/jspf/head.jspf" %>
        <jsp:invoke fragment="customHead"/>
        <title>${pageTitle}</title>
    </jsp:attribute>
    <jsp:attribute name="beginning">
        <jsp:invoke fragment="customBeginning"/>
    </jsp:attribute>
    <jsp:attribute name="header">
        <%@include file="/WEB-INF/jspf/header.jspf" %>
    </jsp:attribute>
    <jsp:attribute name="main">
        <%@include file="/WEB-INF/jspf/sidebar.jspf" %>
        <div class="content-wrapper">
            <section class="content-header">
                <h1>${empty contentTitle ? pageTitle : contentTitle}</h1>
            </section>
            <section class="content">
                <jsp:doBody/>
            </section>
        </div>
    </jsp:attribute>
    <jsp:attribute name="footer">
        <%@include file="/WEB-INF/jspf/footer.jspf" %>
    </jsp:attribute>
    <jsp:attribute name="ending">
        <%@include file="/WEB-INF/jspf/scripts.jspf" %>
        <script>$('body').addClass('skin-blue sidebar-mini');</script>
        <jsp:invoke fragment="customEnding"/>
    </jsp:attribute>
</t:genericPage>