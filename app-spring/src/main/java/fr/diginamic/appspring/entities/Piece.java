package fr.diginamic.appspring.entities;

import com.sun.istack.NotNull;
import fr.diginamic.appspring.enums.TypePiece;

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
    private TypePiece typePiece;
//    @ManyToMany
//    @JoinTable(name="COMPO",
//            joinColumns= @JoinColumn(name="ID_PIECE", referencedColumnName="ID"),
//            inverseJoinColumns= @JoinColumn(name="ID_TACHE", referencedColumnName="ID")
//    )
//    private Set<Tache> tachePiece;

    public Piece() {
        super();
        //tachePiece = new HashSet<Tache>();
    }
    public Piece(String nomPiece) {
        super();
        this.nomPiece = nomPiece;
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

    public TypePiece getTypePiece() {
        return typePiece;
    }

    public void setTypePiece(TypePiece typePiece) {
        this.typePiece = typePiece;
    }
}
