package fr.diginamic.appspring.entities;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * Entité qui represente une demande de pièce
 *
 * @version 1.0
 *
 * @author Mathis
 */
@Entity
public class DemandePiece {

    private enum EtatDemande { EN_ATTENTE, VALIDEE, REFUSEE }

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

    private EtatDemande etat;

    private LocalDate dateDemande;

    @ManyToMany
    @JoinTable(name="COMPO_DEMANDE_PIECE",
            joinColumns= @JoinColumn(name="ID_DEM", referencedColumnName="ID"),
            inverseJoinColumns= @JoinColumn(name="ID_PIECE", referencedColumnName="ID")
    )
    private Set<Piece> piecesDemandees;

    @ManyToOne
    @JoinColumn(name="USER_ID")
    private User demandeur;

    public DemandePiece() {
        piecesDemandees = new HashSet<Piece>();
        dateDemande = LocalDate.now();
        etat = EtatDemande.EN_ATTENTE;
    }


    public void addPiece(Piece p){
        if(p != null){
            piecesDemandees.add(p);
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

    public EtatDemande getEtat() {
        return etat;
    }

    public void setEtat(EtatDemande etat) {
        this.etat = etat;
    }

    public LocalDate getDateDemande() {
        return dateDemande;
    }

    public void setDateDemande(LocalDate dateDemande) {
        this.dateDemande = dateDemande;
    }

    public Set<Piece> getPiecesDemandees() {
        return piecesDemandees;
    }

    public void setPiecesDemandees(Set<Piece> piecesDemandees) {
        this.piecesDemandees = piecesDemandees;
    }

    public User getDemandeur() {
        return demandeur;
    }

    public void setDemandeur(User demandeur) {
        this.demandeur = demandeur;
    }
}
