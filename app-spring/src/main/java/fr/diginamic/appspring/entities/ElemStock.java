package fr.diginamic.appspring.entities;

import javax.persistence.*;
import java.time.LocalDate;
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
    enum etatStock {
        EN_STOCK, EPUISE
    }

    @Column(name = "date_creation")
    private LocalDate creationDate;

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

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }
}
