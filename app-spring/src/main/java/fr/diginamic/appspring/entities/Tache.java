package fr.diginamic.appspring.entities;

import fr.diginamic.appspring.enums.PrioriteTache;
import fr.diginamic.appspring.enums.TypeTache;

import javax.persistence.*;

@Entity
public class Tache {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;

    private String intitule;
    private TypeTache type;
    private PrioriteTache priorite;
    private boolean isTerminee = false;

    @ManyToOne
    @JoinColumn(name="FICHE_ID")
    private FicheEntretien fiche;

    public Tache() {
    }

    public Tache(String intitule, TypeTache type) {
        this.intitule = intitule;
        this.type = type;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getIntitule() {
        return intitule;
    }

    public void setIntitule(String intitule) {
        this.intitule = intitule;
    }

    public PrioriteTache getPriorite() {
        return priorite;
    }

    public void setPriorite(PrioriteTache priorite) {
        this.priorite = priorite;
    }

    public boolean isTerminee() {
        return isTerminee;
    }

    public void setTerminee(boolean terminee) {
        isTerminee = terminee;
    }

    public FicheEntretien getFiche() {
        return fiche;
    }

    public void setFiche(FicheEntretien fiche) {
        this.fiche = fiche;
    }

    public TypeTache getType() {
        return type;
    }

    public void setType(TypeTache type) {
        this.type = type;
    }
}
