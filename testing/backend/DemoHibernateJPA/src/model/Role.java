package model;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity
public class Role implements java.io.Serializable {

    private static final long serialVersionUID = -6621685571541536736L;

    private String name;
    private String description;
    private Set<Account> accounts = new HashSet<>(0);

    public Role() {
    }

    public Role(String name, String description) {
        this.name = name;
        this.description = description;
    }

    @Id
    @Column(length = 30)
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(nullable = false)
    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * If we use mappedBy instead of join table, only the entity that has the
     * join table can manipulate the many-to-many relationship. In this example:
     * - Role(s) can be added and removed from account by manipulating
     * Account.getRoles().
     * - Account(s) cannot be added or removed from role by
     * manipulating Role.getAccounts().
     * - When saving/updating a new accounts, roles associated with it will also be saved/updated.
     * - When saving/updating a new role, accounts associated with it will not be saved/updated.
     */
//    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "roles")
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "Account_Role", joinColumns = @JoinColumn(name = "roleName"), inverseJoinColumns = @JoinColumn(name = "accountId"))
    public Set<Account> getAccounts() {
        return this.accounts;
    }

    public void setAccounts(Set<Account> accounts) {
        this.accounts = accounts;
    }

}
