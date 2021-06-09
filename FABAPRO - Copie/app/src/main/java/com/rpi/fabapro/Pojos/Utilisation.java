package com.rpi.fabapro.Pojos;


public class Utilisation {
    private String username;
    private String nom;
    private String profession;
    private String prix;

 public Utilisation(){}
    public Utilisation(String username, String nom, String profession, String prix) {
        this.username = username;
        this.nom = nom;
        this.profession = profession;
        this.prix = prix;
    }



    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

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

    public String getPrix() {
        return prix;
    }

    public void setPrix(String prix) {
        this.prix = prix;
    }


}
