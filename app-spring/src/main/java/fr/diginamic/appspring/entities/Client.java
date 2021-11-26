package fr.diginamic.appspring.entities;

import fr.diginamic.appspring.enums.TypeClient;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private TypeClient type;
    private String nom;
    private String prenom;
    private String telFixe;
    private String telMobile;

    @Embedded
    private Adresse adresse;

    @OneToMany(mappedBy = "client")
    private Set<FicheEntretien> fichesEntretien;

    @OneToMany(mappedBy = "client")
    private Set<DevisVehicule> devisVehicule;

    public Client() {
        fichesEntretien = new HashSet<FicheEntretien>();
        devisVehicule = new HashSet<DevisVehicule>();
    }

    public Client(TypeClient type, String nom, String prenom, String telFixe, String telMobile) {
        this.type = type;
        this.nom = nom;
        this.prenom = prenom;
        this.telFixe = telFixe;
        this.telMobile = telMobile;
        fichesEntretien = new HashSet<FicheEntretien>();
        devisVehicule = new HashSet<DevisVehicule>();
    }

    public void addFiche(FicheEntretien f) {
        if (f != null) {
            fichesEntretien.add(f);
            System.out.println("Fiche added");
        } else {
            System.err.println("Can't add a null fiche");
        }
    }

    public void addDevis(DevisVehicule d) {
        if (d != null) {
            devisVehicule.add(d);
            System.out.println("Devis added");
        } else {
            System.err.println("Can't add a null devis");
        }
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public TypeClient getType() {
        return type;
    }

    public void setType(TypeClient type) {
        this.type = type;
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

    public String getTelFixe() {
        return telFixe;
    }

    public void setTelFixe(String telFixe) {
        this.telFixe = telFixe;
    }

    public String getTelMobile() {
        return telMobile;
    }

    public void setTelMobile(String telMobile) {
        this.telMobile = telMobile;
    }

    @JsonIgnore
    public Set<FicheEntretien> getFichesEntretien() {
        return fichesEntretien;
    }

    public void setFichesEntretien(Set<FicheEntretien> fichesEntretien) {
        this.fichesEntretien = fichesEntretien;
    }

    public Adresse getAdresse() {
        return adresse;
    }

    public void setAdresse(Adresse adresse) {
        this.adresse = adresse;
    }

    public Set<DevisVehicule> getDevisVehicule() {
        return devisVehicule;
    }

    public void setDevisVehicule(Set<DevisVehicule> devisVehicule) {
        this.devisVehicule = devisVehicule;
    }
}
