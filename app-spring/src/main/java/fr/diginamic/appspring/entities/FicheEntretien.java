package fr.diginamic.appspring.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * Entité qui représente une fiche d'entretien du garage
 * @version 1.0
 * @author Mathis
 */
@Entity
public class FicheEntretien {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;

    private boolean isValid;
    private boolean isCloture;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateCreation;
    private LocalDate dateCloture;

    @OneToMany(mappedBy = "fiche")
    private Set<Tache> taches;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "facture_id")
    private Facture factureEntretien;

    @ManyToOne
    @JoinColumn(name="CLIENT_ID")
    @NotNull(message = "Vous devez indiquer un client existant.")
    private Client client;

    public FicheEntretien() {
        taches = new HashSet<Tache>();
        isValid = true;
        isCloture = false;
        dateCreation = LocalDate.now();
    }

    /**
     * Cloture un fiche d'entretien valide
     */
    public void cloturerFiche(){
        isCloture = true;
        dateCloture = LocalDate.now();
    }

    /**
     * Ajoute une tache à la liste des taches de la liste
     * @param t
     */
    public void ajouterTache(Tache t){
        if(t != null){
            taches.add(t);
        }
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setIsCloture(boolean cloture) {
        this.isCloture = cloture;
    }

    public LocalDate getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDate dateCreation) {
        this.dateCreation = dateCreation;
    }

    public LocalDate getDateCloture() {
        return dateCloture;
    }

    public void setDateCloture(LocalDate dateCloture) {
        this.dateCloture = dateCloture;
    }

    public Set<Tache> getTaches() {
        return taches;
    }

    public void setTaches(Set<Tache> taches) {
        this.taches = taches;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }

    public boolean isCloture() {
        return isCloture;
    }

    public void setCloture(boolean cloture) {
        isCloture = cloture;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Facture getFacture() {
        return factureEntretien;
    }

    public void setFacture(Facture facture) {
        this.factureEntretien = facture;
    }
}
