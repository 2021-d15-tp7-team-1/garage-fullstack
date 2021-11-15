package fr.diginamic.appspring.entities;

import fr.diginamic.appspring.enums.TypeTache;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
public class FicheEntretien {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;



    private boolean validation = false;
    private boolean cloture = false;

    private LocalDate dateCreation;
    private LocalDate dateCloture;

    @OneToMany(mappedBy = "fiche")
    private Set<Tache> taches;

    public FicheEntretien() {
        taches = new HashSet<Tache>();
    }

    public FicheEntretien(TypeTache type) {
        validation = false;
        cloture = false;
        dateCreation = LocalDate.now();
        taches = new HashSet<Tache>();
    }

    public void cloturerFiche(){
        cloture = true;
        dateCloture = LocalDate.now();
    }

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

    public boolean isValidation() {
        return validation;
    }

    public void setValidation(boolean validation) {
        this.validation = validation;
    }

    public boolean isCloture() {
        return cloture;
    }

    public void setCloture(boolean cloture) {
        this.cloture = cloture;
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
}
