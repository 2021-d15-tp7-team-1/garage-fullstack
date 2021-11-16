package fr.diginamic.appspring.entities;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Facture {

    private enum TypeFacture { VENTE_VEHICULE, ENTRETIEN }

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;

    private TypeFacture type;
    private LocalDate dateCreation;
    private float prix;
    private float prixTTC;

    @OneToOne(mappedBy = "facture")
    private Commande commandeConcernee;

    public Facture() {
        dateCreation = LocalDate.now();
    }

    public Facture(TypeFacture type, float prix) {
        this.type = type;
        this.prix = prix;
        dateCreation = LocalDate.now();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public TypeFacture getType() {
        return type;
    }

    public void setType(TypeFacture type) {
        this.type = type;
    }

    public LocalDate getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDate dateCreation) {
        this.dateCreation = dateCreation;
    }

    public float getPrix() {
        return prix;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

    public float getPrixTTC() {
        return prixTTC;
    }

    public void setPrixTTC(float prixTTC) {
        this.prixTTC = prixTTC;
    }

    public Commande getCommandeConcernee() {
        return commandeConcernee;
    }

    public void setCommandeConcernee(Commande commandeConcernee) {
        this.commandeConcernee = commandeConcernee;
    }
}
