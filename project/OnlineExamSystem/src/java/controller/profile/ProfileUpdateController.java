/*
 * Copyright © 2017 Six Idiots Team
 */
package controller.profile;

import dao.AccountManager;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Account;
import model.AccountProfile;
import util.servlet.ManagedServlet;

/**
 *
 * @author Le Cao Nguyen
 */
@WebServlet("/profile/update")
public class ProfileUpdateController extends ManagedServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Account currentUser = (Account) request.getSession().getAttribute("currentUser");
        if (currentUser == null) {
            response.sendError(403, "You must login to be able to update profile.");
            return;
        }
        getCorrespondingViewDispatcher().forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Extract parameters
        Account currentUser = (Account) request.getSession().getAttribute("currentUser");
        request.setCharacterEncoding("UTF-8");
        String fullName = request.getParameter("fullName");
        String email = request.getParameter("email");
        String genderStr = request.getParameter("gender");
        String birthDateStr = request.getParameter("birthDate");
        // Validation
        if (fullName == null) {
            response.sendError(400);
            return;
        }
        Boolean gender = (genderStr == null) ? null : Boolean.parseBoolean(genderStr);
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date birthDate = null;
        try {
            birthDate = (birthDateStr == null) ? null : formatter.parse(birthDateStr);
        } catch (ParseException ex) {
            response.sendError(400);
            return;
        }
        // Update profile
        AccountManager accountManager = new AccountManager();
        currentUser.getProfile().setFullName(fullName);
        currentUser.getProfile().setEmail(email);
        currentUser.getProfile().setGender(gender);
        currentUser.getProfile().setBirthdate(birthDate);
        accountManager.updateAccount(currentUser);
        
        redirect(response, getServletURL(ProfileController.class));
    }
}
