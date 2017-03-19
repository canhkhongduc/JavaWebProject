package model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class AccountProfile implements Serializable {

    private static final long serialVersionUID = -8205509774590071423L;

    @Id
    private String username;

    @OneToOne
    @MapsId
    @JoinColumn(name = "username", referencedColumnName = "username")
    private Account account;

    @Column(columnDefinition = "nvarchar(255)", nullable = false)
    private String fullName;

    private Boolean gender;

    @Temporal(TemporalType.DATE)
    private Date birthdate;

    private String email;

    public AccountProfile() {
    }

    // Here intentionally use default access modifier
    AccountProfile(Account account) {
        this.account = account;
    }

    public AccountProfile(String fullName, Boolean gender, Date birthdate, String email) {
        this.fullName = fullName;
        this.gender = gender;
        this.birthdate = birthdate;
        this.email = email;
    }

    // Here intentionally use default access modifier
    String getUsername() {
        return username;
    }

    public Account getAccount() {
        return account;
    }

    // Here intentionally use default access modifier
    void setAccount(Account account) {
        this.account = account;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Boolean getGender() {
        return gender;
    }

    public void setGender(Boolean gender) {
        this.gender = gender;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
