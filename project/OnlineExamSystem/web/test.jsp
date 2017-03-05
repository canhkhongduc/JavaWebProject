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
                    <c:forEach items="${questionList}" var="item" varStatus="stt">
                        <a href="test?question=${stt.index}" 
                           class="answerBox waves-effect 
                           ${stt.index == questionIndex ? 'waves-light' : 'white black-text waves-teal'} btn"
                           id="q${stt.index}">
                            ${stt.index}</a>
                        </c:forEach>

                    <!--                <a class="answerBox waves-effect white black-text waves-teal btn">2</a>
                                        <a class="answerBox waves-effect red waves-light btn">3</a>
                                        <a class="answerBox waves-effect red waves-light btn">4</a>
                                        <a class="answerBox waves-effect waves-light btn">5</a>
                                        <a class="answerBox waves-effect red waves-light btn">6</a>
                                        <a class="answerBox waves-effect red waves-light btn">7</a>
                                        <a class="answerBox waves-effect red waves-light btn">8</a>
                                        <a class="answerBox waves-effect red waves-light btn">9</a>
                                        <a class="answerBox waves-effect red waves-light btn">10</a>-->
                    <div id="testChoiceOffset">
                        <a class="btnAction waves-effect waves-light btn ${questionIndex == 0 ? 'disabled' : ''}"
                           href="test?question=${questionIndex - 1}">
                            <i class="material-icons left">keyboard_arrow_left</i>Previous</a>
                        <a class="btnAction waves-effect waves-light btn ${questionIndex == questionList.size() - 1 ? 'disabled' : ''}"
                           href="test?question=${questionIndex + 1}">
                            <i class="material-icons right">keyboard_arrow_right</i>Next</a>
                    </div>
                </div>

                <div id="testAnswer" class="col s8">
                    <h4>${questionList.get(questionIndex).getContent()}</h4>

                    <!--Add class prettyprint to <pre> containing code-->
                    <!--                    <pre class="prettyprint card">
                    #include &lt;stdio.h&gt;
                    int x;
                    void main()
                    {
                        if (x)
                            printf("hi");
                        else
                            printf("how are u");
                    }
                                        </pre>-->

                    <c:forEach items="${choiceList}" var="choice" varStatus="index">
                        <p>
                            <input type="checkbox" class="filled-in" id="c${choice.getId()}" 
                                   onclick="addChoice(this, ${choice.getId()}, ${questionIndex})"/>
                            <label for="c${choice.getId()}">${choice.getContent()}</label>
                        </p>
                    </c:forEach>                   
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
                        <a href="" class=" modal-action modal-close waves-effect waves-green btn-flat">No</a>
                        <a href="" class=" modal-action modal-close waves-effect waves-green btn-flat" onclick="localStorage.clear()">Yes</a>
                    </div>
                </div>
                <!--</div>-->
            </div>
        </main>
        <%@include file="/WEB-INF/jspf/footer.jspf" %>
        <script>
            var choiceList;
            var questionList;
            var count;

            if (localStorage.getItem("choiceList") === null) {
                choiceList = [];
            } else {
                choiceList = JSON.parse(localStorage.getItem("choiceList"));
            }

            if (localStorage.getItem("questionList") === null) {
                questionList = [];
            } else {
                questionList = JSON.parse(localStorage.getItem("questionList"));
            }

            for (count = 0; count < choiceList.length; count++) {
                $('#c' + choiceList[count]).prop("checked", true);
            }
            
            for(count = 0; count < questionList.length; count++){
                if(!($('#q' + questionList[count]).hasClass('waves-light'))){
                    $('#q' + questionList[count]).removeClass().addClass('answerBox waves-effect red waves-light btn');
                }
            }

            $(document).ready(function () {
                // the "href" attribute of .modal-trigger must specify the modal ID that wants to be triggered
                $('.modal').modal();
            });

            function addChoice(cb, choiceId, questionIndex) {
                var index = choiceList.indexOf(choiceId);
                if (cb.checked) {
                    if (index < 0) {
                        choiceList.push(choiceId);
                        $('#c' + choiceId).prop("checked", true);
                    }
                } else {
                    if (index > -1) {
                        choiceList.splice(index, 1);
                    }
                }

                if (typeof (Storage) !== "undefined") {
                    localStorage.setItem("choiceList", JSON.stringify(choiceList));
                    hasChoiceChecked(questionIndex);
                } else {
                    alert("Sorry, your browser does not support Web Storage...");
                }
            }
            ;

            function hasChoiceChecked(questionIndex) {
                var index = questionList.indexOf(questionIndex);
                if ($('.filled-in:checked').length > 0) {
                    if (index < 0) {
                        questionList.push(questionIndex);
                    }
                } 
                else {
                    if (index > -1) {
                        questionList.splice(index, 1);
                    }
                }
                localStorage.setItem("questionList", JSON.stringify(questionList));
            }
        </script>
    </body>
</html>
