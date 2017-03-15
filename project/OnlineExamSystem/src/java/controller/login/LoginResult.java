/*
 * Copyright © 2017 Six Idiots Team
 */
package controller.login;

import model.Account;

/**
 *
 * @author Le Cao Nguyen
 */
public class LoginResult {
    private LoginError error;
    private String username;
    private String password;
    private Account account;

    public LoginResult() {
    }

    public LoginResult(LoginError error, String username, String password, Account account) {
        this.error = error;
        this.username = username;
        this.password = password;
        this.account = account;
    }

    public LoginError getError() {
        return error;
    }

    public void setError(LoginError error) {
        this.error = error;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
    
    
}
