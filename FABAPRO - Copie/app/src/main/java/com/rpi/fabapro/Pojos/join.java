package com.rpi.fabapro.Pojos;

public class join {

    public join(){}

    private String nom;

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public int getNumero() {
        return Numero;
    }

    public void setNumero(int numero) {
        Numero = numero;
    }

    public String getAddresse() {
        return addresse;
    }

    public void setAddresse(String addresse) {
        this.addresse = addresse;
    }

    private String profession;
    private int Numero;
    private String addresse;

    public join(String nom, String profession, int numero, String addresse) {
        this.nom = nom;
        this.profession = profession;
        Numero = numero;
        this.addresse = addresse;
    }
}
