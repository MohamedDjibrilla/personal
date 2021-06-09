package com.rpi.fabapro.Pojos;

public class professionnels {
    private String id;
    private String imageurl;
    private String name;
    private String profession;
    private String disponibilite;



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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





    public professionnels(String id, String name, String profession, String disponibilite, String imageurl) {
        this.id = id;
        this.imageurl = imageurl;
        this.name = name;
        this.profession = profession;
        this.disponibilite = disponibilite;

    }

    public professionnels(){
        //required constructor
    }






}
