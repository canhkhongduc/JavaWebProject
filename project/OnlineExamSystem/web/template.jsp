<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" uri="/WEB-INF/tlds/template" %>
<t:oesPage pageTitle="Replace here with page title" contentTitle="If not specify this attribute, it will use page title.">
    <jsp:attribute name="customHead">
        <meta name="instruction1" content="Here we can add custom content to the <head> part of the page (i.e: <style>, <link>, <script>, <meta>, ...).">
        <meta name="instruction2" content="The default head content is in /WEB-INF/jspf/head.jspf.">
    </jsp:attribute>
    <jsp:attribute name="customBeginning">
        <p style="display:none">This is added at the beginning of the &t;body&gt; inner HTML.</p>
    </jsp:attribute>
    <jsp:attribute name="customEnding">
        <p style="display:none">This is added at the end of the &t;body&gt; inner HTML.</p>
        <p style="display:none">Here you can specify additional scripts beside the required scripts in /WEB-INF/jspf/scripts.jspf.</p>
    </jsp:attribute>
    <jsp:body>
        <p>Here is the page content</p>
        <p>The content should be organized using Bootstrap's grid system.</p>
        <p>The 3 classes for grid system are .container-fluid, .row, .col-*-*</p>
        <p>Read more about Bootstrap at <a href="http://getbootstrap.com/">http://getbootstrap.com/</a></p>
        <p>To understand how this page generated:</p>
        <ol>
            <li>Read this page first: <a href="http://stackoverflow.com/questions/1296235/jsp-tricks-to-make-templating-easier">Stack Overflow</a></li>
            <li>Look at the tag files in /WEB-INF/tags/</li>
        </ol>
    </jsp:body>
</t:oesPage>