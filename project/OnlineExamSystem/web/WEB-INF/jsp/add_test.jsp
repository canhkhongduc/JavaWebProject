<!DOCTYPE html>
<html>
    <head>
        <%@include file="/WEB-INF/jspf/head.jspf" %>
        <title>Add Test</title>
        <style>
            .chips input {
                display: none !important;
            }
        </style>
    </head>
    <body>
        
        <header>
            <%@include file="/WEB-INF/jspf/navbar.jspf" %>
        </header>
        <main>
            <br>
            <br>
            <div class="container">
                <div class="row">
                    <div class="col s12 m10 offset-m1">
                        <div class="card white">
                            <form action="addTest" method="POST">
                                <div class="card-content black-text">
                                    <div class="center">
                                        <span class="card-title">Add Test</span>
                                    </div>

                                    <div class="row">
                                        <div class="input-field col s10 offset-s1" style="text-align-last: center;">
                                            <select name="course">
                                                <c:forEach items="${courses}" var="course">
                                                    <option value="${course.id}">${course.name}</option>
                                                </c:forEach>
                                            </select>
                                            <label>Course</label>
                                        </div>
                                    </div>

                                    <div class="row">
                                        <div class="input-field col s10 offset-s1">
                                            <input id="name" name="name" type="text" class="validate" required>
                                            <label for="name">Test Name</label>
                                        </div>
                                    </div>

                                    <div class="row">
                                        <div class="input-field col s10 offset-s1">
                                            <div id="questions" class="chips">
                                            </div>
                                            <a class="btn waves-effect waves-light" id="edit-questions"><i class="material-icons left">edit</i>Edit Questions</a>
                                        </div>
                                    </div>

                                </div>
                                <div class="card-action right-align">
                                    <button class="btn waves-effect waves-light" type="submit" name="action">submit
                                        <i class="material-icons right">send</i>
                                    </button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </main>
        <%@include file="/WEB-INF/jspf/footer.jspf" %>
        
        <div id="questionModal" class="modal modal-fixed-footer">
            <div class="modal-content">
                <h4>Available Questions</h4>
                <table id="questionTable">
                    
                </table>
            </div>
            <div class="modal-footer">
                <a href="#!" class="modal-action modal-close waves-effect waves-green btn-flat ">Agree</a>
            </div>
        </div>
        <script>
            $(document).ready(function () {
                $('select').material_select();
            });
            $('.chips').material_chip();
            $('#edit-questions').click(function () {
                var questions = $('#questions').material_chip('data');

            });
        </script>
    </body>
</html>
