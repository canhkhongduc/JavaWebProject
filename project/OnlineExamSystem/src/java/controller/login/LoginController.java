/*
 * Copyright © 2017 Six Idiots Team
 */
package controller.login;

import dao.AccountManager;
import dao.GroupManager;
import java.io.IOException;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Account;
import model.Group;
import util.HashingUtil;
import util.googleapi.GoogleProfile;
import util.servlet.ManagedServlet;

/**
 *
 * @author nguyen
 */
@WebServlet("/login")
public class LoginController extends ManagedServlet {

    private final AccountManager accountManager = new AccountManager();

    public void redirectLoginError(HttpServletResponse response, LoginError error)
            throws ServletException, IOException {
        String uri = buildUri(getServletURL(LoginErrorController.class), "errorId", error.name().toLowerCase());
        response.sendRedirect(uri);
    }

    private boolean isValidDomain(String email) {
        // TODO: Move allowed domain to controller parameter or application configuration
        return email.endsWith("@fpt.edu.vn");
    }

    private LoginError verifyLogin(String username, String password) {
        if (username == null || password == null) {
            return LoginError.BAD_REQUEST;
        }
        Account account = accountManager.getAccount(username);
        if (account == null) {
            return LoginError.USERNAME_DOES_NOT_EXIST;
        }
        String passwordHash = HashingUtil.generateSHA512Hash(password);
        if (!passwordHash.equals(account.getPassword())) {
            return LoginError.INCORRECT_PASSWORD;
        }
        return LoginError.NONE;
    }

    private boolean addNewStudentAccount(AccountManager manager, GoogleProfile profile) {
        GroupManager groupManager = new GroupManager();
        Group studentGroup = groupManager.getGroup("student");
        Account account = new Account();
        account.setUsername(profile.getEmail());
        account.setPassword(HashingUtil.generateSHA512Hash(profile.getId()));
        account.setFullName(profile.getName());
        account.setEmail(profile.getEmail());
        account.setGender(true);
        account.setBirthdate(new Date());
        account.setGroup(studentGroup);
        return manager.addAccount(account);
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        GoogleProfile googleProfile = (GoogleProfile) request.getAttribute("googleProfile");
        LoginError error;
        String username, password;
        if (googleProfile != null) {
            username = googleProfile.getEmail();
            password = googleProfile.getId();
            if (isValidDomain(username)) {
                error = verifyLogin(username, password);
                if (error == LoginError.USERNAME_DOES_NOT_EXIST) {
                    if (addNewStudentAccount(accountManager, googleProfile)) {
                        error = LoginError.NONE;
                    }
                }
            } else {
                error = LoginError.DOMAIN_NOT_ALLOWED;
            }
        } else {
            request.setCharacterEncoding("UTF-8");
            username = request.getParameter("username");
            password = request.getParameter("password");
            error = verifyLogin(username, password);
        }

        switch (error) {
            case NONE:
                Account account = accountManager.getAccount(username);
                session.setAttribute("account", account);
                response.sendRedirect(getContextPath());
                break;
            default:
                redirectLoginError(response, error);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}
