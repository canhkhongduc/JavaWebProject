<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" uri="/WEB-INF/tlds/template" %>
<t:oesPage pageTitle="Test">
    <jsp:attribute name="customHead">
        <!--Syntax Highlighting-->
        <script src="https://cdn.rawgit.com/google/code-prettify/master/loader/run_prettify.js"></script>
        <link rel="stylesheet" href="http://fonts.googleapis.com/icon?family=Material+Icons">        
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>        
        <!--<link rel="stylesheet" href="https://code.getmdl.io/1.3.0/material.indigo-pink.min.css">-->
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

            .mess{
                position: absolute;
                top: 18em;
                left: 30em;
            }

            .hidden{
                display: none;
            }

            .blur{
                filter: blur(5px);
                pointer-events: none;
            }
        </style>
    </jsp:attribute>
    <jsp:attribute name="customBeginning">
    </jsp:attribute>
    <jsp:attribute name="customEnding">
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

            for (count = 0; count < questionList.length; count++) {
                if (!($('#q' + questionList[count]).hasClass('btn-primary'))) {
                    $('#q' + questionList[count]).removeClass().addClass('btn btn-success');
                }
            }

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
                if ($('.choice:checked').length > 0) {
                    if (index < 0) {
                        questionList.push(questionIndex);
                    }
                } else {
                    if (index > -1) {
                        questionList.splice(index, 1);
                    }
                }
                localStorage.setItem("questionList", JSON.stringify(questionList));
            }

            function submit(testId) {
                localStorage.clear();

                $('#main').addClass('blur');
                $('#mess').removeClass('hidden');

                $.ajax({
                    type: 'POST',
                    url: "marking",
                    dataType: 'JSON',
                    data: {
                        choiceList: JSON.stringify(choiceList),
                        testId: testId
                    },
                    success: function (response) {
                        $('#main').removeClass('blur');
                        $('#mess').addClass('hidden');
                        location.href = "attempt/result?attemptId=" + response;
                    },
                    error: function () {
                        $('#main').removeClass('blur');
                        $('#mess').addClass('hidden');
                        alert('AJAX failed');
                        console.log('AJAX failed');
                    }
                });
            }

            var endTime = new Date(Date.parse(document.getElementById("time").innerHTML));
            checkTime();

            var timeCheck = setInterval((function () {
                checkTime();
            }), 1000);
            
            function checkTime(){
                var currentTime = new Date();
                var remainingTime = new Date(endTime.getTime() - currentTime.getTime());
                document.getElementById("remain").innerHTML = remainingTime.getMinutes() + ":" + remainingTime.getSeconds();
                if (remainingTime <= 0) {
                    submit(${testId});
                    clearInterval(timeCheck);
                    return;
                }
            }
        </script>
    </jsp:attribute>
    <jsp:body>
        <main id="main">
            <div id="testContent" class="row">
                <div id="testChoice" class="col-md-4">
                    <c:forEach items="${questionList}" var="item" varStatus="stt">
                        <a href="test?question=${stt.index}" 
                           class="btn 
                           ${stt.index == questionIndex ? 'btn-primary' : 'btn-default'}"
                           id="q${stt.index}">${stt.index}</a>
                    </c:forEach>
                    <div id="testChoiceOffset">
                        <a class="btn btn-primary ${questionIndex == 0 ? 'disabled' : ''}"
                           href="test?question=${questionIndex - 1}">
                            <i class="material-icons left">keyboard_arrow_left</i></a>
                        <a class="btn btn-primary ${questionIndex == questionList.size() - 1 ? 'disabled' : ''}"
                           href="test?question=${questionIndex + 1}">
                            <i class="material-icons right">keyboard_arrow_right</i></a>
                    </div>
                </div>
                <div id="testAnswer" class="col-md-8">
                    <h3>${questionList.get(questionIndex).getContent()}</h3>

                    <!--                    Add class prettyprint to 
                                        <pre> containing code
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
                                            </pre>-->

                    <c:forEach items="${choiceList}" var="choice" varStatus="index">
                        <div class="checkbox">
                            <label>
                                <input type="checkbox" class="choice" id="c${choice.getId()}" 
                                       onclick="addChoice(this, ${choice.getId()}, ${questionIndex})">
                                ${choice.getContent()}
                            </label>
                        </div>
                    </c:forEach>
                    <span>(Choose ${noOfCorrect} answer(s))</span>
                </div>
            </div>
            <div id="testAction" class="row">
                <div class="col-md-4"></div>
                <div class="col-md-4">
                    <button type="button" class="btn btn-info btn-lg" data-toggle="modal" data-target="#finishModal">Finish</button>
                    <div class="modal fade" id="finishModal" role="dialog">
                        <div class="modal-dialog">

                            <div class="modal-content">
                                <div class="modal-header">
                                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                                    <h4 class="modal-title">Finish test</h4>
                                </div>
                                <div class="modal-body">
                                    <p>Do you want to submit your attempt and exit?</p>
                                </div>
                                <div class="modal-footer">
                                    <a href="" class="btn btn-danger" onclick="submit(${testId})" 
                                       data-dismiss="modal">Yes</a>
                                    <a href="" class="btn btn-primary" data-dismiss="modal">No</a>
                                </div>
                            </div>

                        </div>
                    </div>
                </div>
            </div>
            <br>
            <div class="row" style="color: red; text-align: center">
                Your test will be submitted at <span id="time">${endTime}</span>
                <br>
                Remaining time: <span id="remain"></span>
            </div>                           
        </main>

        <div class="mess hidden" id="mess">
            <h1>Please wait while your score is being calculated...</h1>
        </div>
    </jsp:body>
</t:oesPage>