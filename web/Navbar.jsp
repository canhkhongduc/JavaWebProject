<%-- 
    Document   : Header
    Created on : Feb 15, 2017, 12:39:40 AM
    Author     : Canh Khong Duc <canhkdse04533 at FPT University>
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Navigation bar</title>
        <style>
            nav .input-field input {
                height: 100%;
                font-size: 1.2rem;
                border: none;
                padding-left: 2rem;
                margin-top: 0.25rem;
                margin-bottom: 5px;
                padding: 1.2rem;
            }
            .row{margin-bottom: 0;}
        </style>
    </head>
    <body>
        <div class="container">
            <nav>
                <div class="nav-wrapper teal text-darken-2">
                    <!--<a href="#" class="brand-logo right">Idiot Team</a>-->

                    <ul class="left hide-on-med-and-down">
                        <li><a href="Homepage.jsp">Home</a></li>
                        <li><a href="SubjectView.jsp">My courses</a></li>
                    </ul>
                </div>
            </nav>
            <div class="row">
                <div class="col s6"></div>
                <div class="col s6">
                    <form>
                        <div class="input-field teal text-darken-2">
                            <input id="search" type="search" placeholder="Search" required>
                            <label class="label-icon" for="search"><i class="material-icons">search</i></label>
                            <i class="material-icons">close</i>
                        </div>
                    </form
                </div>
            </div>
        </div>
    </div>
</body>
</html>
