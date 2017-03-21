package model;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OrderBy;

@Entity
public class Role implements Serializable {

    private static final long serialVersionUID = 8326403564391243835L;

    @Id
    @Column(length = 30)
    private String name;

    @Column(nullable = false)
    private String description;

    @ManyToMany(mappedBy = "roles")
    @OrderBy("username")
    private Set<Account> accounts = new LinkedHashSet<>(0);
    
    public Role() {
    }

    public Role(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Account> getAccounts() {
        return accounts;
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(this.name);
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
        final Role other = (Role) obj;
        return Objects.equals(this.name, other.name);
    }
}
