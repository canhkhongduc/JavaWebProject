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
    
    public void redirectLoginError(HttpServletResponse response, LoginError error)
            throws ServletException, IOException {
        response.sendError(400, "Error while logging in: " + error.getMessage());
    }

    private String getPasswordFromGoogleProfile(GoogleProfile profile) {
        return profile.getEmail();
    }

    private boolean addNewStudentAccount(GoogleProfile profile) {
        AccountManager accountManager = new AccountManager();
        RoleManager roleManager = new RoleManager();
        Role studentRole = roleManager.getRole("student");
        Account account = new Account();
        account.setUsername(profile.getEmail());
        account.setPassword(HashingUtil.generateSHA512Hash(getPasswordFromGoogleProfile(profile)));
        account.getProfile().setFullName(profile.getName());
        account.getProfile().setEmail(profile.getEmail());
        account.addRole(studentRole);
        return accountManager.saveAccount(account);
    }

    private LoginResult verifyCredential(String username, String password) {
        LoginResult result = new LoginResult(LoginError.NONE, username, password, null);
        if (username == null || password == null) {
            result.setError(LoginError.BAD_REQUEST);
            return result;
        }
        AccountManager accountManager = new AccountManager();
        Account account = accountManager.getAccount(username);
        if (account == null) {
            result.setError(LoginError.USERNAME_DOES_NOT_EXIST);
            return result;
        }
        String passwordHash = HashingUtil.generateSHA512Hash(password);
        if (!passwordHash.equals(account.getPassword())) {
            result.setError(LoginError.INCORRECT_PASSWORD);
            return result;
        }
        result.setAccount(account);
        return result;
    }

    private LoginResult verifyLoginRequest(HttpServletRequest request) throws ServletException {
        AccountManager accountManager = new AccountManager();
        GoogleProfile googleProfile = (GoogleProfile) request.getAttribute("googleProfile");
        String username, password;
        // 1. Verify the credential
        if (googleProfile != null) {
            username = googleProfile.getEmail();
            password = getPasswordFromGoogleProfile(googleProfile);
            if (!accountManager.hasAccount(username)) {
                addNewStudentAccount(googleProfile);
            }
        } else {
            username = request.getParameter("username");
            password = request.getParameter("password");
        }
        LoginResult result = verifyCredential(username, password);
        return result;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        getCorrespondingViewDispatcher().forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (request.getUserPrincipal() != null) {
            request.logout();
        }
        LoginResult result = verifyLoginRequest(request);
        switch (result.getError()) {
            case NONE:
                request.login(result.getUsername(), result.getPassword());
                request.getSession().setAttribute("currentUser", result.getAccount());
                redirect(response, "");
                break;
            default:
                redirectLoginError(response, result.getError());
        }
    }
}
