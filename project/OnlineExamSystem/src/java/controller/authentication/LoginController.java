package controller.authentication;

import dao.AccountManager;
import dao.GroupManager;
import java.io.IOException;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.UriBuilder;
import model.Account;
import model.Group;
import util.HashingUtil;
import util.googleapi.GoogleProfile;

/**
 *
 * @author nguyen
 */
public class LoginController extends HttpServlet {

    private static final long serialVersionUID = 1846816234516327701L;

    private final AccountManager accountManager = new AccountManager();

    public static void responseLoginError(HttpServletResponse response, LoginResult result)
            throws ServletException, IOException {
        UriBuilder builder = UriBuilder.fromUri("login/error").queryParam("errorId", result.name().toLowerCase());
        response.sendRedirect(builder.build().toString());
    }

    private boolean isValidDomain(String email) {
        // TODO: Store allowed domain in application settings
        return email.endsWith("@fpt.edu.vn");
    }

    private LoginResult verifyLogin(String username, String password) {
        if (username == null || password == null) {
            return LoginResult.BAD_REQUEST;
        }
        Account account = accountManager.getAccount(username);
        if (account == null) {
            return LoginResult.USERNAME_DOES_NOT_EXIST;
        }
        String passwordHash = HashingUtil.generateSHA512Hash(password);
        if (!passwordHash.equals(account.getPassword())) {
            return LoginResult.INCORRECT_PASSWORD;
        }
        return LoginResult.SUCCESS;
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
        LoginResult loginResult;
        GoogleProfile googleProfile = (GoogleProfile) request.getAttribute("googleProfile");
        
        String username, password;
        if (googleProfile != null) {
            username = googleProfile.getEmail();
            password = googleProfile.getId();
            if (!isValidDomain(username)) {
                loginResult = LoginResult.DOMAIN_NOT_ALLOWED;
            } else {
                loginResult = verifyLogin(username, password);
                if (loginResult == LoginResult.USERNAME_DOES_NOT_EXIST) {
                    if (addNewStudentAccount(accountManager, googleProfile)) {
                        loginResult = LoginResult.SUCCESS;
                    }
                }
            }
        } else {
            request.setCharacterEncoding("UTF-8");
            username = request.getParameter("username");
            password = request.getParameter("password");
            loginResult = verifyLogin(username, password);
        }

        switch (loginResult) {
            case SUCCESS:
                Account account = accountManager.getAccount(username);
                session.setAttribute("account", account);
                response.sendRedirect("");
                break;
            default:
                responseLoginError(response, loginResult);
                break;
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
