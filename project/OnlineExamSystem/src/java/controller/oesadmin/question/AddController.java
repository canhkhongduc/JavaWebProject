/*
 * Copyright © 2017 Six Idiots Team
 */
package controller.oesadmin.question;

import com.sun.xml.internal.ws.api.config.management.policy.ManagedServiceAssertion;
import dao.CourseManager;
import dao.QuestionManager;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Account;
import model.Choice;
import model.Course;
import model.Question;
import util.servlet.ManagedServlet;

/**
 *
 * @author Quang Minh
 */
@WebServlet("/oes-admin/question/add")
public class AddController extends ManagedServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        getCorrespondingViewDispatcher().forward(request, response);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        CourseManager cm = new CourseManager();
        List<Course> courses = cm.getAllCourses();
        request.setAttribute("courses", courses);
        getCorrespondingViewDispatcher().forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //--add question---//
        QuestionManager db = new QuestionManager();
        List<Question> questions = db.getAllQuestions();
        System.out.println("id: "+questions.get(questions.size()-1).getId());
        HttpSession session = request.getSession(true);
        Account owner = (Account)session.getAttribute("currentUser");        
        String content = request.getParameter("content");
        String courseID = request.getParameter("course");
        CourseManager cm = new CourseManager();
        Course course = cm.getCourse(courseID);
        Question question = new Question(owner, course, content);
        QuestionManager qm = new QuestionManager();
        
        long questionID = questions.get(questions.size()-1).getId()+1;
        System.out.println(""+owner.getUsername()+" "+content+" "+courseID);
        //---//
        
        //---add choices---//
        boolean result = false;
        for (int i = 0; i < 4; i++) {
            String answer = request.getParameter("answer"+(i+1));
            String correct = request.getParameter("correct"+(i+1));
            System.out.println("correct: "+correct);
            System.out.println("answer: "+answer);
            if(correct==null){
                result = false;
            } else if (correct.equalsIgnoreCase("true")) {
                result = true;
            }
            Choice choice = new Choice(answer, result);
            question.addChoice(choice);
        }
        qm.addQuestion(question);
        //---//
        redirect(response, "/oes-admin/question/add");
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
