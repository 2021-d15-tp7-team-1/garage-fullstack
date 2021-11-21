package fr.diginamic.appspring.form;

import fr.diginamic.appspring.enums.EtatVehicule;

public class VehiculeForm {
    public float prix;
    public float prixFacture;
    private String marque;
    private String modele;
    private int quantiteStock;
    private String couleur;
    private EtatVehicule etatVehicule;

    public String getMarque() {
        return marque;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    public int getQuantiteStock() {
        return quantiteStock;
    }

    public void setQuantiteStock(int quantiteStock) {
        this.quantiteStock = quantiteStock;
    }

    public String getModele() {
        return modele;
    }

    public void setModele(String modele) {
        this.modele = modele;
    }

    public float getPrix() {
        return prix;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

    public float getPrixFacture() {
        return prixFacture;
    }

    public void setPrixFacture(float prixFacture) {
        this.prixFacture = prixFacture;
    }

    public String getCouleur() {
        return couleur;
    }

    public void setCouleur(String couleur) {
        this.couleur = couleur;
    }

    public EtatVehicule getEtatVehicule() {
        return etatVehicule;
    }

    public void setEtatVehicule(EtatVehicule etatVehicule) {
        this.etatVehicule = etatVehicule;
    }
}

