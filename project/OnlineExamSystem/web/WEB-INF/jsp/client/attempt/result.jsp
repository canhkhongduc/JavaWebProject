<%@page import="model.Question"%>
<%@page import="java.util.List"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" uri="/WEB-INF/tlds/template" %>
<t:oesPage pageTitle="Attempt Result">
    <jsp:attribute name="customHead">
    </jsp:attribute>
    <jsp:attribute name="customBeginning">
    </jsp:attribute>
    <jsp:attribute name="customEnding">
    </jsp:attribute>
    <jsp:body>
        <h2><b>${attempt.getTest().getName()}</b></h2>
        <h3>Total Score: <fmt:formatNumber value="${attempt.getScore()}" maxFractionDigits="2" /> </h3>
        <c:forEach items="${test.getQuestions()}" var="question">
            <h4>${question.getContent()} <i id='${'correctIcon'.concat(question.getId())}' class="" aria-hidden="true"></i></h4>
            <ul>
                
                <!-- All choices of the question loop -->
                <c:forEach items="${question.getChoices()}" var="choice">
                    <c:if test="${choice.isCorrect()}">
                        <c:set var="trueAnswer" value="${choice.getContent()}"></c:set>
                    </c:if>
                    <li id='${choice.getId()}'>${choice.getContent()}</li>
                    
                    <!-- Student's choices loop -->
                    <c:forEach items="${attempt.getChoices()}" var="studentChoice">
                            
                        <!-- First Logic: If student did choose answer(s) -->
                        <c:if test="${studentChoice.getId() == choice.getId()}">
                            <!-- Checking correct or incorrect choice -->
                            <c:choose> 
                                <c:when test="${choice.isCorrect()}">
                                    <script>
                                        document.getElementById('${choice.getId()}').className = "bg-green";
                                        document.getElementById('${'correctIcon'.concat(question.getId())}').className = "fa fa-check";
                                        document.getElementById('${'correctIcon'.concat(question.getId())}').style.color = "green";
                                    </script>
                                </c:when>
                                        
                                <c:otherwise>
                                    <script>
                                        document.getElementById('${choice.getId()}').className = "bg-red";
                                        document.getElementById('${'correctIcon'.concat(question.getId())}').className = "fa fa-times";
                                        document.getElementById('${'correctIcon'.concat(question.getId())}').style.color = "red";
                                    </script>
                                </c:otherwise>
                            </c:choose>
                        </c:if>
                                    
                    </c:forEach>
                                    
                </c:forEach>
            </ul>
            <b>Correct Answer: </b><span class='bg-success text-green'>${trueAnswer}</span>
        </c:forEach>
    </jsp:body>
</t:oesPage>