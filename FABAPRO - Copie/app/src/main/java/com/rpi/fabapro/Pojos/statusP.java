package com.rpi.fabapro.Pojos;

public class statusP {
    private String pkey;
    private String nom;
    private String profession;
    private String disponibilite;
    private String ServiceOffert;
    private String l;
    private String m;
    private String m1;
    private String j;
    private String v;
    private String s;
    private String s1;
 public statusP(){}

    public String getServiceOffert() {
        return ServiceOffert;
    }

    public void setServiceOffert(String serviceOffert) {
        ServiceOffert = serviceOffert;
    }
    public String getPkey() {
        return pkey;
    }

    public void setPkey(String pkey) {
        this.pkey = pkey;
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

    public String getDisponibilite() {
        return disponibilite;
    }

    public void setDisponibilite(String disponibilite) {
        this.disponibilite = disponibilite;
    }

    public String getL() {
        return l;
    }

    public void setL(String l) {
        this.l = l;
    }

    public String getM() {
        return m;
    }

    public void setM(String m) {
        this.m = m;
    }

    public String getM1() {
        return m1;
    }

    public void setM1(String m1) {
        this.m1 = m1;
    }

    public String getJ() {
        return j;
    }

    public void setJ(String j) {
        this.j = j;
    }

    public String getV() {
        return v;
    }

    public void setV(String v) {
        this.v = v;
    }

    public String getS() {
        return s;
    }

    public void setS(String s) {
        this.s = s;
    }

    public String getS1() {
        return s1;
    }

    public void setS1(String s1) {
        this.s1 = s1;
    }



    public statusP(String pkey, String nom, String profession, String disponibilite, String serviceOffert, String l, String m, String m1, String j, String v, String s, String s1) {
        this.pkey = pkey;
        this.nom = nom;
        this.profession = profession;
        this.disponibilite = disponibilite;
        ServiceOffert = serviceOffert;
        this.l = l;
        this.m = m;
        this.m1 = m1;
        this.j = j;
        this.v = v;
        this.s = s;
        this.s1 = s1;
    }


}
