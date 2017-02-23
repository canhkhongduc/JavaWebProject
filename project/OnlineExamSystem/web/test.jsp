<%-- 
    Document   : Test
    Created on : Feb 19, 2017, 10:30:39 AM
    Author     : Lam
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <%@include file="/WEB-INF/jspf/head.jspf" %>
        <title>Test</title>
        <!--Syntax Highlighting-->
        <script src="https://cdn.rawgit.com/google/code-prettify/master/loader/run_prettify.js"></script>

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

            pre.prettyprint {
                background: #e0e0e0;
            }
        </style>
    </head>
    <body>
        <%@include file="/WEB-INF/jspf/navbar.jspf" %>
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
                    <h4>What is the output of the code below?</h4>

                    <!--Add class prettyprint to <pre> containing code-->
                    <pre class="prettyprint card">
#include &lt;stdio.h&gt;
int x;
void main()
{
    if (x)
        printf("hi");
    else
        printf("how are u");
}
                    </pre>
                    <p>
                        <input type="checkbox" class="filled-in" id="filled-in-box1" checked="checked" />
                        <label for="filled-in-box1">hi</label>
                    </p>
                    <p>
                        <input type="checkbox" class="filled-in" id="filled-in-box2" />
                        <label for="filled-in-box2">how are you</label>
                    </p>
                    <p>
                        <input type="checkbox" class="filled-in" id="filled-in-box3" />
                        <label for="filled-in-box3">Compile time error</label>
                    </p>
                    <p>
                        <input type="checkbox" class="filled-in" id="filled-in-box4" />
                        <label for="filled-in-box4">None of the mentioned</label>
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
        <%@include file="/WEB-INF/jspf/footer.jspf" %>
        <script>
            $(document).ready(function () {
                // the "href" attribute of .modal-trigger must specify the modal ID that wants to be triggered
                $('.modal').modal();
            });
        </script>
    </body>
</html>
