<%-- 
    Document   : Settings
    Created on : Feb 23, 2017, 10:37:53 PM
    Author     : Hai
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <%@include file="/WEB-INF/jspf/head.jspf" %>
        <title>Settings</title>
        <style>
            header, main, footer {
                padding-left: 300px;
            }

            @media only screen and (max-width : 992px) {
                header, main, footer {
                    padding-left: 0;
                }
            }

            a.button-collapse.top-nav {
                position: absolute;
                text-align: center;
                height: 48px;
                width: 48px;
                left: 7.5%;
                top: 0;
                float: none;
                margin-left: 1.5rem;
                color: #000;
                font-size: 36px;
                z-index: 2;
            }

            a.button-collapse.top-nav.full {
                line-height: 122px;
            }

            .collapsible-body {
                padding: 0px;
            }
            .collapsible-body > ul > li.active {
                background-color: #263238 !important;
            }
        </style>
    </head>
    <body>
        <header>
            <%@include file="/WEB-INF/jspf/navbar.jspf" %>
            <div class="container">
                <a href="#" data-activates="nav-mobile" class="button-collapse top-nav waves-effect waves-light circle hide-on-large-only"><i class="material-icons">menu</i></a>
            </div>
            <ul id="nav-mobile" class="side-nav fixed">
                <li>
                    <div class="center"><h4>Online Exam System</h4></div>
                </li>
                <li><div class="divider"></div></li>
                <li class="no-padding">
                    <ul class="collapsible collapsible-accordion">
                        <li>
                            <a class="collapsible-header waves-effect waves-teal active">Admin Functions</a>
                            <div class="collapsible-body">
                                <ul>
                                    <li class="active"><a href="#!">Manage Test Masters</a></li>
                                </ul>
                            </div>
                        </li>
                        <li>
                            <a class="collapsible-header waves-effect waves-teal">Test Master Functions</a>
                            <div class="collapsible-body">
                                <ul>
                                    <li><a href="#!">Manage Tests</a></li>
                                </ul>
                            </div>
                        </li>
                    </ul>
                </li>
            </ul>
        </header>
        <main>
            <br>
            <br>
            <div class="container">
                <div class="row">
                    <h5>Test Master</h5>
                </div>
                <div class="row">
                    <a class="waves-effect waves-light btn"><i class="material-icons left">add</i>Add</a>
                </div>
                <div class="row">
                    <div class="col s12">
                        <div class="card-panel">
                            <table class="highlight">
                                <thead>
                                    <tr>
                                        <th data-field="name">Name</th>
                                        <th data-field="email">Email</th>
                                        <th data-field="action">Action</th>
                                    </tr>
                                </thead>

                                <tbody>
                                    <tr>
                                        <td>Ngo Tung Son</td>
                                        <td>sonnt5@fpt.edu.vn</td>
                                        <td><a href="#"><i class="material-icons red-text">delete</i></a></td>
                                    </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </main>
        <%@include file="/WEB-INF/jspf/footer.jspf" %>
        <script>
            $('.button-collapse').sideNav({
                menuWidth: 300, // Default is 300
                edge: 'left', // Choose the horizontal origin
                closeOnClick: false, // Closes side-nav on <a> clicks, useful for Angular/Meteor
                draggable: true // Choose whether you can drag to open on touch screens
            }
            );
            $('.collapsible').collapsible();
        </script>
    </body>
</html>
