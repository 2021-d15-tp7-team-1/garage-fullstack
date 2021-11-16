package fr.diginamic.appspring.entities;

import com.sun.istack.NotNull;
import fr.diginamic.appspring.enums.EtatVehicule;

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

    @ManyToOne
    @JoinColumn(name = "devis_v_ID")
    @NotNull
    private DevisVehicule devisV;
    private EtatVehicule etatV;

    public Vehicule() {
        super();
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

    public DevisVehicule getDevisV() {
        return devisV;
    }

    public void setDevisV(DevisVehicule devisV) {
        this.devisV = devisV;
    }

    public EtatVehicule getEtatV() {
        return etatV;
    }

    public void setEtatV(EtatVehicule etatV) {
        this.etatV = etatV;
    }
}
