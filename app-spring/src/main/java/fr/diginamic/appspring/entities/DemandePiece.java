package fr.diginamic.appspring.entities;

import com.sun.istack.NotNull;
import fr.diginamic.appspring.enums.EtatDemande;
import fr.diginamic.appspring.enums.TypePiece;

import javax.persistence.*;
import java.util.Date;

@Entity
@DiscriminatorValue("P")
@Table(name="PIECE")
public class DemandePiece extends ElemStock {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;
    private EtatDemande etatDemande;
    private Date dateDemande;
    private boolean isValid;
    @ManyToOne
    @JoinColumn(name = "devis_v_ID")
    @NotNull
    private DevisVehicule devisV;
    public DemandePiece() {
        super();
    }
}
