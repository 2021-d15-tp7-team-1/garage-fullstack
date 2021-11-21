package fr.diginamic.appspring.form;

import fr.diginamic.appspring.enums.TypePiece;

public class PieceForm {
    private TypePiece type;
    private String nom;
    private int quantiteStock ;
    private int pu;
    private int pu_facture;

    public TypePiece getType() {
        return type;
    }

    public void setType(TypePiece type) {
        this.type = type;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getQuantiteStock() {
        return quantiteStock;
    }

    public void setQuantiteStock(int quantiteStock) {
        this.quantiteStock = quantiteStock;
    }

    public int getPu() {
        return pu;
    }

    public void setPu(int pu) {
        this.pu = pu;
    }

    public int getPu_facture() {
        return pu_facture;
    }

    public void setPu_facture(int pu_facture) {
        this.pu_facture = pu_facture;
    }
}

