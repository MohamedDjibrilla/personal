package com.rpi.fabapro.Pojos;

public class conversation {

    private String position;
    private String com;
    private String couleur;
    private String uid;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }



    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getCom() {
        return com;
    }

    public void setCom(String com) {
        this.com = com;
    }

    public String getCouleur() {
        return couleur;
    }

    public void setCouleur(String couleur) {
        this.couleur = couleur;
    }




public conversation(){}
    public conversation(String position, String couleur, String com, String uid) {
        this.position = position;
        this.couleur = couleur;
        this.com = com;
        this.uid=uid;
    }



}
