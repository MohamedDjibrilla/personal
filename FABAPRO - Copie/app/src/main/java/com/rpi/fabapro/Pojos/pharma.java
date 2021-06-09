package com.rpi.fabapro.Pojos;

public class pharma {
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getLocalite() {
        return localite;
    }

    public void setLocalite(String localite) {
        this.localite = localite;
    }

    private String date;
    private String nom;
    private String localite;

    public pharma(){}

    public pharma(String date, String nom, String localite) {
        this.date = date;
        this.nom = nom;
        this.localite = localite;
    }
}
