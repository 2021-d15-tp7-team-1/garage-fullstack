package fr.diginamic.appspring.entities;

import com.sun.istack.NotNull;

import javax.persistence.*;

@Entity
@DiscriminatorValue("V")
@Table(name="VEHICULE")
public class Vehicule extends ElemStock {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

    private String modele;
    private String marque;
    private String couleur;

    @ManyToOne
    @JoinColumn(name = "devis_v_ID")
    private DevisVehicule devisV;

    private enum etatVehicule {
        NEUF,OCCASION
    };

    public Vehicule() {
        super();
    }

    public Vehicule(String modele, String marque, String couleur) {
        this.modele = modele;
        this.marque = marque;
        this.couleur = couleur;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    public String getModele() {
        return modele;
    }

    public void setModele(String modele) {
        this.modele = modele;
    }

    public String getMarque() {
        return marque;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    public String getCouleur() {
        return couleur;
    }

    public void setCouleur(String couleur) {
        this.couleur = couleur;
    }

    public DevisVehicule getDevisV() {
        return devisV;
    }

    public void setDevisV(DevisVehicule devisV) {
        this.devisV = devisV;
    }
}
