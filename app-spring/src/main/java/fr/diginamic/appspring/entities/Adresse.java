package fr.diginamic.appspring.entities;

import javax.persistence.Embeddable;

@Embeddable
public class Adresse {
    private String libelle;
    private String ville;
    private int codePostal;

    public Adresse() {
    }

    public Adresse(String libelle, String ville, int codePostal) {
        this.libelle = libelle;
        this.ville = ville;
        this.codePostal = codePostal;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public int getCodePostal() {
        return codePostal;
    }

    public void setCodePostal(int codePostal) {
        this.codePostal = codePostal;
    }
}
