package fr.diginamic.appspring.entities;

import com.sun.istack.NotNull;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@DiscriminatorValue("P")
@Table(name="PIECE")
public class Piece extends ElemStock {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;
    private String nomPiece;
    private enum typePiece {
        PIECE, ARTICLE
    }

    @ManyToMany(mappedBy="piecesDemandees")
    private Set<DemandePiece> demandes;

    @ManyToMany
    @JoinTable(name="COMPO_PIECE_TACHE",
            joinColumns= @JoinColumn(name="ID_PIECE", referencedColumnName="ID"),
            inverseJoinColumns= @JoinColumn(name="ID_TACHE", referencedColumnName="ID")
    )
    private Set<Tache> tachesPiece;

    public Piece() {
        super();
        tachesPiece = new HashSet<Tache>();
        demandes = new HashSet<DemandePiece>();
    }
    public Piece(String nomPiece) {
        super();
        this.nomPiece = nomPiece;
        demandes = new HashSet<DemandePiece>();
    }

    public String getNomPiece() {
        return nomPiece;
    }

    public void setNomPiece(String nomPiece) {
        this.nomPiece = nomPiece;
    }
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Set<DemandePiece> getDemandes() {
        return demandes;
    }

    public void setDemandes(Set<DemandePiece> demandes) {
        this.demandes = demandes;
    }

    public Set<Tache> getTachesPiece() {
        return tachesPiece;
    }

    public void setTachesPiece(Set<Tache> tachesPiece) {
        this.tachesPiece = tachesPiece;
    }
}
