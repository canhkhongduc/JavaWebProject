<%@page import="model.Question"%>
<%@page import="java.util.List"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" uri="/WEB-INF/tlds/template" %>
<t:oesPage pageTitle="Attempt Result">
    <jsp:attribute name="customHead">
        <style>
            #wholeAttempt {
                border-radius: 10px;
                border: 1px solid grey;
            }
        </style>
    </jsp:attribute>
    <jsp:attribute name="customBeginning">
    </jsp:attribute>
    <jsp:attribute name="customEnding">
    </jsp:attribute>
    <jsp:body>
        <h2><b>${attempt.getTest().getName()}</b></h2>
        <h3>Total Score: <fmt:formatNumber value="${attempt.getScore()}" maxFractionDigits="2" /> </h3>
        <c:forEach items="${test.getQuestions()}" var="question">
            <div class="container">
                <div class="jumbotron" id="wholeAttempt">
                    <c:set var="correctChoices" value="${0}" ></c:set>
                    <h4>${question.getContent()} <i id='${'correctIcon'.concat(question.getId())}' class="" aria-hidden="true"></i></h4>
                    <ul>

                        <!-- All choices of the question loop -->
                        <c:forEach items="${question.getChoices()}" var="choice">
                            <c:set var="markChecked" value="${false}"></c:set>
                            <c:if test="${choice.isCorrect()}">
                                <c:set var="trueAnswer" value="${choice.getContent()}"></c:set>
                            </c:if>
                            <li id='${choice.getId()}'>${choice.getContent()}</li>

                            <!-- Student's choices loop -->
                            <c:forEach items="${attempt.getChoices()}" var="studentChoice">

                                <!-- First Logic: If student did choose answer(s) -->
                                <c:choose>
                                    <c:when test="${studentChoice.getId() == choice.getId()}">
                                        <!-- Checking correct or incorrect choice -->
                                        <c:choose> 
                                            <c:when test="${choice.isCorrect()}">
                                                <c:set var="correctChoices" value="${correctChoices + 1}"></c:set>
                                                <c:set var="markChecked" value="${true}"></c:set>
                                                <script>
                                                    document.getElementById('${choice.getId()}').className = "bg-green";
                                                </script>
                                            </c:when>

                                            <c:otherwise>
                                                <script>
                                                    document.getElementById('${choice.getId()}').className = "bg-red";
                                                </script>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:when>

                                    <c:when test="${studentChoice.getId() != choice.getId() && choice.isCorrect() && markChecked == false}">
                                        <script>
                                            document.getElementById('${choice.getId()}').className = "bg-yellow";
                                        </script>    
                                    </c:when>
                                </c:choose>            
                            </c:forEach>

                        </c:forEach>
                    </ul>
                    <b>Correct Answer: </b>
                    <c:forEach items="${question.getChoices()}" var="choice">
                        <c:if test="${choice.isCorrect()}" >
                            <span class='bg-success text-green'>${choice.getContent()} &nbsp;</span>
                        </c:if>
                    </c:forEach>
                    
                    <!-- Set tick, cross or exclamation mark for the question -->
                    <c:choose>
                        <c:when test="${correctChoices == question.getTotalCorrectChoices()}">
                            <script>
                                document.getElementById('${'correctIcon'.concat(question.getId())}').className = "fa fa-check";
                                document.getElementById('${'correctIcon'.concat(question.getId())}').style.color = "green";
                            </script>
                        </c:when>
                        <c:when test="${correctChoices == 0}">
                            <script>
                                document.getElementById('${'correctIcon'.concat(question.getId())}').className = "fa fa-times";
                                document.getElementById('${'correctIcon'.concat(question.getId())}').style.color = "red";
                            </script>
                        </c:when>
                        <c:otherwise>
                            <script>
                                document.getElementById('${'correctIcon'.concat(question.getId())}').className = "fa fa-exclamation";
                                document.getElementById('${'correctIcon'.concat(question.getId())}').style.color = "gold";
                            </script>
                        </c:otherwise>
                    </c:choose>
                </div>    
            </div>
        </c:forEach>
    </jsp:body>
</t:oesPage>