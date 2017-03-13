package model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.NaturalId;

@Entity
public class Account implements Serializable {

    private static final long serialVersionUID = 8314745598970424584L;

    @Id
    @GeneratedValue
    private Long id;

    @NaturalId
    private String username;

    @Column(length = 128)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "Account_Role", joinColumns = @JoinColumn(name = "accountId"), inverseJoinColumns = @JoinColumn(name = "roleName"))
    @Cascade(CascadeType.SAVE_UPDATE)
    private Set<Role> roles = new HashSet<>(0);

    @OneToOne(mappedBy = "account")
    @Cascade({CascadeType.SAVE_UPDATE, CascadeType.DELETE})
    private AccountProfile profile;

    public Account() {
    }

    public Account(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Account(String username, String password, AccountProfile profile) {
        this.username = username;
        this.password = password;
        this.profile = profile;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void addRole(Role role) {
        this.roles.add(role);
    }

    public void removeRole(Role role) {
        this.roles.remove(role);
    }

    public AccountProfile getProfile() {
        return profile;
    }

    public void setProfile(AccountProfile profile) {
        this.profile = profile;
        profile.setAccount(this);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.username);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Account other = (Account) obj;
        return Objects.equals(this.username, other.username);
    }
}
