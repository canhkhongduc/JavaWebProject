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
        Account currentUser = (Account) request.getSession().getAttribute("currentUser");
        // Extract parameters
        request.setCharacterEncoding("UTF-8");
        String fullName = request.getParameter("fullName");
        String email = request.getParameter("email");
        String genderStr = request.getParameter("gender");
        String birthDateStr = request.getParameter("birthDate");
        
        if (fullName.isEmpty()) fullName = null;
        if (email.isEmpty()) email = null;
        if (genderStr.isEmpty()) genderStr = null;
        if (birthDateStr.isEmpty()) birthDateStr = null;
        
        // Validation
        if (fullName == null) {
            response.sendError(400, "Full name must not be empty.");
            return;
        }
        Boolean gender = (genderStr == null) ? null : Boolean.parseBoolean(genderStr);
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date birthDate = null;
        try {
            birthDate = (birthDateStr == null) ? null : formatter.parse(birthDateStr);
        } catch (ParseException ex) {
            response.sendError(400, "Invalid birth date.");
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
