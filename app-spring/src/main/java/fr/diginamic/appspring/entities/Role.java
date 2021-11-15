package fr.diginamic.appspring.entities;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Role {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;

    private String nomRole;

    @ManyToMany
    @JoinTable(name="COMPO_USER_ROLE",
            joinColumns= @JoinColumn(name="ID_ROLE", referencedColumnName="ID"),
            inverseJoinColumns= @JoinColumn(name="ID_USER", referencedColumnName="ID")
    )
    private Set<User> users;

    public Role() {
    }

    public Role(String nomRole) {
        this.nomRole = nomRole;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNomRole() {
        return nomRole;
    }

    public void setNomRole(String nomRole) {
        this.nomRole = nomRole;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }
}
