<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" uri="/WEB-INF/tlds/template" %>
<t:oesPage pageTitle="Homepage">
    <jsp:attribute name="customHead">
        <style>
            .carousel-inner > .item > img,
            .carousel-inner > .item > a > img {
                margin: auto;
            }
            .carousel-caption > h3 {
                text-shadow: 2px 2px black;
            }
            .carousel-caption > p {
                text-shadow: 2px 2px black;
                font-size: 1.2em;
            }
            .box-body > ul > li {
                font-size: 1.2em;
            }
        </style>
    </jsp:attribute>
    <jsp:body>
        <div class="container-fluid">
            <div class="row">
                <div class="col-sm-12">
                    <div class="box box-primary">
                        <div class="box-header with-border">
                            <h3 class="box-title">Introduction</h3>
                            <div class="box-tools pull-right">
                            </div>
                        </div>
                        <div class="box-body">
                            <p style="font-size: 1.2em; text-align: justify;">
                                <span style="font-weight: bold">Online Exam System (OES)</span> is a Java 
                                web application that provide online examination 
                                services to students in FPT University.<br>
                                The application is developed by Six Idiots Team, 
                                a group of five students from SE1101 and one student 
                                from IA1101. We are studying Java Web Application Development 
                                (PRJ321) in our 4th semester, under the instruction of Mr. Ngo Tung Son.<br>
                                One day, Mr. Son offered us a request to develop 
                                an examination website that he can use for his classes. 
                                This project can be considered as a final project 
                                for the PRJ321 course, and is allowed to do in group.<br>
                                So after receiving the offer, we met and decided to 
                                accept the offer and teamed up to do this project. 
                                During the development progress, we have experienced 
                                through a lot of technologies, libraries and frameworks 
                                for web application development. We learned a lot from 
                                this project, and we believed that these knowlegde will 
                                benefit us in study and in our future careers.
                            </p>
                        </div>
                        <div class="box-footer">
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-4">
                    <div class="box box-success">
                        <div class="box-header with-border">
                            <h3 class="box-title">Front-end technologies</h3>
                        </div>
                        <div class="box-body">
                            <ul>
                                <li>Bootstrap + AdminLTE template</li>
                                <li>jQuery</li>
                                <li>
                                    <p>Plugins:</p>
                                    <ul>
                                        <li>bootstrap-datepicker</li>
                                        <li>bootstrap-validator</li>
                                        <li>dataTables</li>
                                        <li>daterangepicker</li>
                                        <li>font-awesome</li>
                                        <li>slimScroll</li>
                                    </ul>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
                <div class="col-sm-4">
                    <div class="box box-warning">
                        <div class="box-header with-border">
                            <h3 class="box-title">Our members</h3>
                        </div>
                        <div class="box-body">
                            <div id="member-carousel" class="carousel slide" data-ride="carousel">
                                <!-- Indicators -->
                                <ol class="carousel-indicators">
                                    <li data-target="#member-carousel" data-slide-to="0" class="active"></li>
                                    <li data-target="#member-carousel" data-slide-to="1"></li>
                                    <li data-target="#member-carousel" data-slide-to="2"></li>
                                    <li data-target="#member-carousel" data-slide-to="3"></li>
                                    <li data-target="#member-carousel" data-slide-to="4"></li>
                                    <li data-target="#member-carousel" data-slide-to="5"></li>
                                </ol>
                                <!-- Wrapper for slides -->
                                <div class="carousel-inner" role="listbox">
                                    <div class="item active">
                                        <img src="${contextPath}/resources/images/canhkd.jpg" alt="Khổng Đức Cảnh">
                                        <div class="carousel-caption">
                                            <h3>Khổng Đức Cảnh (SE1101)</h3>
                                            <p>Leader, Tester</p>
                                        </div>
                                    </div>
                                    <div class="item">
                                        <img src="${contextPath}/resources/images/haind.jpg" alt="Nguyễn Duy Hải">
                                        <div class="carousel-caption">
                                            <h3>Nguyễn Duy Hải (SE1101)</h3>
                                            <p>Full-stack Developer</p>
                                        </div>
                                    </div>
                                    <div class="item">
                                        <img src="${contextPath}/resources/images/lampd.jpg" alt="Phan Đăng Lâm">
                                        <div class="carousel-caption">
                                            <h3>Phan Đăng Lâm (SE1101)</h3>
                                            <p>Full-stack Developer</p>
                                        </div>
                                    </div>
                                    <div class="item">
                                        <img src="${contextPath}/resources/images/minhnq.jpg" alt="Nguyễn Quang Minh">
                                        <div class="carousel-caption">
                                            <h3>Nguyễn Quang Minh (SE1101)</h3>
                                            <p>Full-stack Developer, Tester</p>
                                        </div>
                                    </div>
                                    <div class="item">
                                        <img src="${contextPath}/resources/images/minhct.jpg" alt="Công Tôn Minh">
                                        <div class="carousel-caption">
                                            <h3>Công Tôn Minh (SE1101)</h3>
                                            <p>Full-stack Developer, Database Designer</p>
                                        </div>
                                    </div>
                                    <div class="item">
                                        <img src="${contextPath}/resources/images/nguyenlc.jpg" alt="Lê Cao Nguyên">
                                        <div class="carousel-caption">
                                            <h3>Lê Cao Nguyên (IA1101)</h3>
                                            <p>Full-stack Developer, Technical Advisor</p>
                                        </div>
                                    </div>
                                </div>
                                <!-- Controls -->
                                <a class="left carousel-control" href="#member-carousel" role="button" data-slide="prev">
                                    <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
                                    <span class="sr-only">Previous</span>
                                </a>
                                <a class="right carousel-control" href="#member-carousel" role="button" data-slide="next">
                                    <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
                                    <span class="sr-only">Next</span>
                                </a>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-sm-4">
                    <div class="box box-danger">
                        <div class="box-header with-border">
                            <h3 class="box-title">Backend technologies</h3>
                            <div class="box-tools pull-right">
                            </div>
                        </div>
                        <div class="box-body">
                            <ul>
                                <li>Java EE 7 (Servlet + JSP)</li>
                                <li>JSTL 1.2 + Custom tag library</li>
                                <li>Hibernate 4.3.1 (JPA 2.1)</li>
                                <li>Google GSON</li>
                                <li>Scribe OAuth</li>
                                <li>Apache Tomcat 8.5</li>
                                <li>Microsoft Azure SQL Server Database</li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </jsp:body>
</t:oesPage>