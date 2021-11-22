package fr.diginamic.appspring.entities;

import fr.diginamic.appspring.enums.TypeTache;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
public class FicheEntretien {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;

    private boolean isValid = false;
    private boolean isCloture = false;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateCreation;
    private LocalDate dateCloture;

    @OneToMany(mappedBy = "fiche")
    private Set<Tache> taches;

    @ManyToOne
    @JoinColumn(name="CLIENT_ID")
    @NotNull(message = "Vous devez indiquer un client existant.")
    private Client client;

    public FicheEntretien() {
        taches = new HashSet<Tache>();
        isValid = false;
        isCloture = false;
        dateCreation = LocalDate.now();
    }

    public void cloturerFiche(){
        isCloture = true;
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
}
