package fr.diginamic.appspring.entities;

import fr.diginamic.appspring.enums.TypePiece;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.HashSet;
import java.util.Set;

/**
 * Entité qui représente une pièce du stock
 *
 * @version 1.0
 *
 * @see ElemStock
 *
 * @author Mathis
 */
@Entity
@DiscriminatorValue("P")
@Table(name="PIECE")
public class Piece extends ElemStock {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;
    private String nomPiece;

    private TypePiece type;

    @ManyToMany(mappedBy="piecesDemandees")
    private Set<DemandePiece> demandes;

    @ManyToMany(fetch = FetchType.EAGER)
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

    public Piece(int quantiteStock, float prix, float prixFacture, String nomPiece, TypePiece type) {
        super(quantiteStock, prix, prixFacture);
        this.nomPiece = nomPiece;
        this.type = type;
        tachesPiece = new HashSet<Tache>();
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

    @JsonIgnore
    public Set<Tache> getTachesPiece() {
        return tachesPiece;
    }

    public void setTachesPiece(Set<Tache> tachesPiece) {
        this.tachesPiece = tachesPiece;
    }

    public TypePiece getType() {
        return type;
    }

    public void setType(TypePiece type) {
        this.type = type;
    }
}
