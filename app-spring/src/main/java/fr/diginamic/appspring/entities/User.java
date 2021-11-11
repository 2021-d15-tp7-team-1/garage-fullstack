package fr.diginamic.appspring.entities;

import javax.persistence.*;
import java.util.Set;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;

    private String userName;
    private String password;

    private String nom;
    private String prenom;

    @ManyToMany(mappedBy="users")
    private Set<Role> userRoles;

    public User() {
    }

    public User(String userName, String password, String nom, String prenom) {
        this.userName = userName;
        this.password = password;
        this.nom = nom;
        this.prenom = prenom;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public Set<Role> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(Set<Role> userRoles) {
        this.userRoles = userRoles;
    }
}
