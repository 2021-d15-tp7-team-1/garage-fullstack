package fr.diginamic.appspring.entities;

import fr.diginamic.appspring.enums.EtatStock;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@DiscriminatorColumn(name="stock_type")

@Table(name="ELEM_STOCK")
public abstract class ElemStock {


    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

    private int quantiteStock;
    private float prix;
    private float prixFacture;
    private EtatStock etatStock;

    @Temporal(TemporalType.DATE)
    @Column(name = "date_debut")
    private Date creationDate;

    public ElemStock() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getQuantiteStock() {
        return quantiteStock;
    }

    public void setQuantiteStock(int quantiteStock) {
        this.quantiteStock = quantiteStock;
    }

    public float getPrix() {
        return prix;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

    public float getPrixFacture() {
        return prixFacture;
    }

    public void setPrixFacture(float prixFacture) {
        this.prixFacture = prixFacture;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public EtatStock getEtatStock() {
        return etatStock;
    }

    public void setEtatStock(EtatStock etatStock) {
        this.etatStock = etatStock;
    }
}
