<%-- 
    Document   : Test
    Created on : Feb 19, 2017, 10:30:39 AM
    Author     : Lam
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Test</title>
        <link href="http://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
        <!--Import materialize.css-->
        <link rel="stylesheet" type="text/css" href="css/materialize.min.css"/>
        <!--Import jQuery before materialize.js-->
        <script type="text/javascript" src="https://code.jquery.com/jquery-2.1.1.min.js"></script>
        <script type="text/javascript" src="js/materialize.min.js"></script>

        <style>
            #testTitle{
                height: 20em;
            }

            #testContent{

            }

            .answerBox{
                width: 17%;
                margin: 0.3em;
            }

            #testChoice{
                border-right: 1px solid gray;
            }

            #testChoiceOffset{
                height: 20em;
                margin-top: 2em;
                text-align: center;
            }

            #testAction{
                text-align: center;
            }

            #testAnswer{
            }

            .btnAction{
                width: 5em;
            }
        </style>
    </head>
    <body>
        <jsp:include page="Navbar.jsp"></jsp:include>

        <main>
            <div id="testTitle" class="row amber lighten-2 valign-wrapper">
                <div class="col s4 offset-s4">
                    <h3 class="valign center-align white-text">Insert test name here</h3>
                </div>
            </div>

            <div id="testContent" class="row">
                <div id="testChoice" class="col s4 lighten-4">
                    <a class="answerBox waves-effect white black-text waves-teal btn">1</a>
                    <a class="answerBox waves-effect white black-text waves-teal btn">2</a>
                    <a class="answerBox waves-effect red waves-light btn">3</a>
                    <a class="answerBox waves-effect red waves-light btn">4</a>
                    <a class="answerBox waves-effect waves-light btn">5</a>
                    <a class="answerBox waves-effect red waves-light btn">6</a>
                    <a class="answerBox waves-effect red waves-light btn">7</a>
                    <a class="answerBox waves-effect red waves-light btn">8</a>
                    <a class="answerBox waves-effect red waves-light btn">9</a>
                    <a class="answerBox waves-effect red waves-light btn">10</a>
                    <div id="testChoiceOffset">
                        <a class="btnAction waves-effect waves-light btn"><i class="material-icons left">keyboard_arrow_left</i>Previous</a>
                        <a class="btnAction waves-effect waves-light btn"><i class="material-icons right">keyboard_arrow_right</i>Next</a>
                    </div>
                </div>

                <div id="testAnswer" class="col s8">
                    <h4>What is the difference between JDK,JRE and JVM?</h4>

                    <p>
                        <input type="checkbox" class="filled-in" id="filled-in-box1" checked="checked" />
                        <label for="filled-in-box1">Answer 1</label>
                    </p>

                    <p>
                        <input type="checkbox" class="filled-in" id="filled-in-box2" />
                        <label for="filled-in-box2">Answer 2</label>
                    </p>

                    <p>
                        <input type="checkbox" class="filled-in" id="filled-in-box3" />
                        <label for="filled-in-box3">Answer 3</label>
                    </p>

                    <p>
                        <input type="checkbox" class="filled-in" id="filled-in-box4" />
                        <label for="filled-in-box4">Answer 4</label>
                    </p>
                </div>
            </div>

            <div id="testAction">
                <!--<div class="col s4 offset-s4">-->
                <a class="waves-effect waves-light btn" href="#modalConfirm">Finish</a>
                <!-- Modal Structure -->
                <div id="modalConfirm" class="modal">
                    <div class="modal-content">
                        <h3>Do you want to submit the test?</h3>
                    </div>
                    <div class="modal-footer">
                        <a href="#!" class=" modal-action modal-close waves-effect waves-green btn-flat">No</a>
                        <a href="#!" class=" modal-action modal-close waves-effect waves-green btn-flat">Yes</a>
                    </div>
                </div>
                <!--</div>-->
            </div>
        </main>
        
        <jsp:include page="Footer.jsp"></jsp:include>

        <script>
            $(document).ready(function () {
                // the "href" attribute of .modal-trigger must specify the modal ID that wants to be triggered
                $('.modal').modal();
            });
        </script>
    </body>
</html>
