package fr.diginamic.appspring.entities;

import fr.diginamic.appspring.enums.PrioriteTache;
import fr.diginamic.appspring.enums.TypeTache;

import javax.persistence.*;

import java.util.HashSet;
import java.util.Set;

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

    @ManyToOne
    @JoinColumn(name="USER_ID")
    private User mecanicienAttribue;

    @ManyToMany(mappedBy="tachesPiece", fetch = FetchType.EAGER)
    private Set<Piece> piecesNecessaires;

    public Tache() {
        piecesNecessaires = new HashSet<Piece>();
    }

    public Tache(String intitule, TypeTache type) {
        this.intitule = intitule;
        this.type = type;
        piecesNecessaires = new HashSet<Piece>();
    }

    public void addPiece(Piece p){
        if(p != null){
            piecesNecessaires.add(p);
            System.out.println("Piece added");
        }
        else {
            System.err.println("Can't add a null piece");
        }
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

    public User getMecanicienAttribue() {
        return mecanicienAttribue;
    }

    public void setMecanicienAttribue(User mecanicienAttribue) {
        this.mecanicienAttribue = mecanicienAttribue;
    }

    public Set<Piece> getPiecesNecessaires() {
        return piecesNecessaires;
    }

    public void setPiecesNecessaires(Set<Piece> piecesNecessaires) {
        this.piecesNecessaires = piecesNecessaires;
    }
}
