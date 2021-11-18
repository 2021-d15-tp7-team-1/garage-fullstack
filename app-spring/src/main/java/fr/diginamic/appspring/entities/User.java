package fr.diginamic.appspring.entities;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;

    private String userName;
    private String password;
    private String email;

    private String nom;
    private String prenom;

    private boolean isSpaActive;

    @ManyToMany
    @JoinTable(name="COMPO_USER_ROLE",
            joinColumns= @JoinColumn(name="ID_USER", referencedColumnName="ID"),
            inverseJoinColumns= @JoinColumn(name="ID_ROLE", referencedColumnName="ID")
    )
    private Set<Role> userRoles;

    @OneToMany(mappedBy = "demandeur")
    private Set<DemandePiece> demandesPieces;

    @OneToMany(mappedBy = "commercial")
    private Set<DevisVehicule> devisCrees;

    public User() {
        userRoles = new HashSet<Role>();
        demandesPieces = new HashSet<DemandePiece>();
        devisCrees = new HashSet<DevisVehicule>();
        isSpaActive = true;
    }

    public User(String userName, String password, String email, String nom, String prenom) {
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.nom = nom;
        this.prenom = prenom;
        userRoles = new HashSet<Role>();
        demandesPieces = new HashSet<DemandePiece>();
        devisCrees = new HashSet<DevisVehicule>();
        isSpaActive = true;
    }

    public void addRole(Role role){
        if(role != null){
            userRoles.add(role);
            System.out.println("Role added");
        }
        else {
            System.err.println("Can't add a null role");
        }
    }

    public void addDevis(DevisVehicule d){
        if(d != null){
            devisCrees.add(d);
            System.out.println("Devis added");
        }
        else {
            System.err.println("Can't add a null devis");
        }
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isSpaActive() {
        return isSpaActive;
    }

    public void setSpaActive(boolean spaActive) {
        isSpaActive = spaActive;
    }

    public Set<Role> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(Set<Role> userRoles) {
        this.userRoles = userRoles;
    }

    public Set<DemandePiece> getDemandesPieces() {
        return demandesPieces;
    }

    public void setDemandesPieces(Set<DemandePiece> demandesPieces) {
        this.demandesPieces = demandesPieces;
    }

    public Set<DevisVehicule> getDevisCrees() {
        return devisCrees;
    }

    public void setDevisCrees(Set<DevisVehicule> devisCrees) {
        this.devisCrees = devisCrees;
    }
}
