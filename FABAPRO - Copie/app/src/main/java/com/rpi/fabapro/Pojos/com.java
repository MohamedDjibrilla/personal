package com.rpi.fabapro.Pojos;

public class com {
public com(){}
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    private String nom;
    private String commentaire;
    private String pkey;

    public String getPkey() {
        return pkey;
    }

    public void setPkey(String pkey) {
        this.pkey = pkey;
    }


    public com(String nom, String commentaire, String pkey) {
        this.nom = nom;
        this.commentaire = commentaire;
        pkey=pkey;
    }
}
