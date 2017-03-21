/*
 * Copyright © 2017 Six Idiots Team
 */
package controller.dev;

import dao.AccountManager;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.servlet.ServletException;
import javax.servlet.annotation.HttpConstraint;
import javax.servlet.annotation.ServletSecurity;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Account;
import model.Role;
import util.HashingUtil;
import util.hibernate.transaction.TransactionPerformer;
import util.servlet.ManagedServlet;

/**
 * Controller for testing Hibernate DAO objects.
 *
 * @author Le Cao Nguyen
 */
@WebServlet("/dev/hbtest")
@ServletSecurity(@HttpConstraint(rolesAllowed = "admin"))
public class HibernateTestController extends ManagedServlet {

    /**
     * Append a line constructed from format string and arguments to response
     * body.
     *
     * @param response The response object.
     * @param format The format string.
     * @param args The arguments to be included.
     * @throws ServletException
     * @throws IOException
     */
    private void appendToResponse(HttpServletResponse response, String format, Object... args)
            throws ServletException, IOException {
        response.getWriter().printf("<p>" + format + "</p>\n", args);
    }

    /**
     * Change password of admin account.
     *
     * @param password The new password.
     * @return true if success, otherwise false.
     */
    private boolean changeAdminPassword(String password) {
        AccountManager accountManager = new AccountManager();
        Account admin = accountManager.getAccount("admin");
        admin.setPassword(HashingUtil.generateSHA512Hash(password));
        return accountManager.updateAccount(admin);
    }

    /**
     * Print account information.
     *
     * @param username Username of the account.
     * @param response The response object.
     * @throws ServletException
     * @throws IOException
     */
    private void showAccountInfo(String username, HttpServletResponse response)
            throws ServletException, IOException {
        AccountManager accountManager = new AccountManager();
        if (accountManager.hasAccount(username)) {
            Account a = accountManager.getAccount(username, true);
            appendToResponse(response, "Username: %s", a.getUsername());
            appendToResponse(response, "Full name: %s",
                    Optional.ofNullable(a.getProfile().getFullName())
                            .orElse("null")
            );
            appendToResponse(response, "Gender: %s",
                    Optional.ofNullable(a.getProfile().getGender())
                            .map((gender) -> gender ? "Male" : "Female")
                            .orElse("null")
            );
            appendToResponse(response, "Birthdate: %s",
                    Optional.ofNullable(a.getProfile().getBirthdate())
                            .map((date) -> DateFormat.getInstance().format(date))
                            .orElse("null")
            );
            appendToResponse(response, "Email: %s",
                    Optional.ofNullable(a.getProfile().getEmail())
                            .orElse("null"));
            appendToResponse(response, "Roles: %s",
                    a.getRoles().stream().map(Role::getName)
                            .collect(Collectors.joining(", "))
            );
        } else {
            appendToResponse(response, "%s does not exist!", username);
        }
    }

    /**
     * Initialize data for the database.
     */
    private boolean initializeDataSource() {
        TransactionPerformer performer = new TransactionPerformer();
        return performer.performTransaction((session) -> {
            Role adminRole = new Role("admin", "Administrator");
            Role testMasterRole = new Role("testmaster", "Test Master");
            Role studentRole = new Role("student", "Student");

            session.save(adminRole);
            session.save(testMasterRole);
            session.save(studentRole);

            Account adminAccount = new Account("admin", HashingUtil.generateSHA512Hash("admin"));
            adminAccount.getProfile().setFullName("Administrator");
            adminAccount.addRole((Role) session.get(Role.class, "admin"));
            session.save(adminAccount);
        });
    }

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
        String action = request.getParameter("action");
        switch (action) {
            case "chgpwd":
                String password = request.getParameter("password");
                if (password == null) {
                    password = "admin";
                }
                if (changeAdminPassword(password)) {
                    appendToResponse(response, "Admin password has been changed!");
                } else {
                    appendToResponse(response, "Failed to change admin password!");
                }
                break;
            case "accinfo":
                String username = request.getParameter("username");
                if (username == null) {
                    appendToResponse(response, "No username provided!");
                } else {
                    showAccountInfo(username, response);
                }
                break;
            case "init":
                if (initializeDataSource()) {
                    appendToResponse(response, "Data has been initialized!");
                } else {
                    appendToResponse(response, "Failed to initialize data!");
                }
                break;
            default:
                appendToResponse(response, "No action specified!");
        }
    }
}
