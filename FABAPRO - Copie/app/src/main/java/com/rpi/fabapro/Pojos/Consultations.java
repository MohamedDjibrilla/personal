package com.rpi.fabapro.Pojos;

public class Consultations {
    private String username;
    private String uid;
    private int nombre;
    public Consultations(){}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public int getNombre() {
        return nombre;
    }

    public void setNombre(int nombre) {
        this.nombre = nombre;
    }



    public Consultations(String username, String uid, int nombre) {
        this.username = username;
        this.uid = uid;
        this.nombre = nombre;
    }



}
