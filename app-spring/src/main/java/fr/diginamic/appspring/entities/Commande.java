package fr.diginamic.appspring.entities;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Commande {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;

    private boolean estLivre = false;
    private boolean aLivrer = false;

    private LocalDate dateCommande;

    @OneToOne
    @JoinColumn(name = "devis_id", referencedColumnName = "id")
    private DevisVehicule devis;

    @OneToOne
    @JoinColumn(name = "facture_id", referencedColumnName = "id")
    private Facture facture;
}
