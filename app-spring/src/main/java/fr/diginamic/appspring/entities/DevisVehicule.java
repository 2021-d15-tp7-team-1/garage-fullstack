package fr.diginamic.appspring.entities;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

/**
 * Entité qui représente un devis d'une vente de vehicule
 *
 * @version 1.0
 *
 */
@Entity
public class DevisVehicule {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;
    private String libelle;
    private int quantite;
    private double prixTTC;
    private boolean isValide = false;
    private LocalDate dateCreation;
    private LocalDate dateValidation;

    @ManyToOne
    @JoinColumn(name="CLIENT_ID")
    private Client client;

    @OneToOne(mappedBy = "devis")
    private Commande commande;

    @ManyToOne
    @JoinColumn(name="VEHICULE_ID")
    private Vehicule vehicule;

    @ManyToOne
    @JoinColumn(name="COMMERCIAL_ID")
    private User commercial;

    public DevisVehicule() {
    }

    public DevisVehicule(String libelle, int quantite, double prixTTC) {
        this.libelle = libelle;
        this.quantite = quantite;
        this.prixTTC = prixTTC;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public double getPrixTTC() {
        return prixTTC;
    }

    public void setPrixTTC(double prixTTC) {
        this.prixTTC = prixTTC;
    }

    public boolean isValide() {
        return isValide;
    }

    public void setValide(boolean valide) {
        isValide = valide;
    }

    public LocalDate getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDate dateCreation) {
        this.dateCreation = dateCreation;
    }

    public LocalDate getDateValidation() {
        return dateValidation;
    }

    public void setDateValidation(LocalDate dateValidation) {
        this.dateValidation = dateValidation;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Vehicule getVehicule() {
        return vehicule;
    }

    public void setVehicule(Vehicule vehicule) {
        this.vehicule = vehicule;
    }

    public Commande getCommande() {
        return commande;
    }

    public void setCommande(Commande commande) {
        this.commande = commande;
    }

    public User getCommercial() {
        return commercial;
    }

    public void setCommercial(User commercial) {
        this.commercial = commercial;
    }
}