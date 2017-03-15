/*
 * Copyright © 2017 Six Idiots Team
 */
package controller.login;

import dao.AccountManager;
import dao.RoleManager;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Account;
import model.Role;
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
        redirect(response, getServletURL(LoginErrorController.class), "errorId", error.name().toLowerCase());
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
        RoleManager roleManager = new RoleManager();
        Role studentRole = roleManager.getRole("student");
        Account account = new Account();
        account.setUsername(profile.getEmail());
        account.setPassword(HashingUtil.generateSHA512Hash(profile.getEmail()));
        account.getProfile().setFullName(profile.getName());
        account.getProfile().setEmail(profile.getEmail());
        account.addRole(studentRole);
        return manager.saveAccount(account);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        getCorrespondingViewDispatcher().forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        GoogleProfile googleProfile = (GoogleProfile) request.getAttribute("googleProfile");
        LoginError error;
        String username, password;
        if (googleProfile != null) {
            username = googleProfile.getEmail();
            if (isValidDomain(username)) {
                error = LoginError.NONE;
                if (!accountManager.hasAccount(username)) {
                    addNewStudentAccount(accountManager, googleProfile);
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
                session.setAttribute("currentUser", account);
                redirect(response, "");
                break;
            default:
                redirectLoginError(response, error);
        }
    }
}
