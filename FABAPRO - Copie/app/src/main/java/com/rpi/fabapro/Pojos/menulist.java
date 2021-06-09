package com.rpi.fabapro.Pojos;

public class menulist {

    public menulist(){}
    private String titre;
    private String cuisson;
    private String platprix;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    private String ID;

    public menulist(String titre, String cuisson, String platprix, String tempslivraison, String prixlivraison, String description, String imageUrl, String ID) {
        this.titre = titre;
        this.cuisson = cuisson;
        this.platprix = platprix;
        this.tempslivraison = tempslivraison;
        this.prixlivraison = prixlivraison;
        Description = description;
        ImageUrl = imageUrl;
        ID=ID;
    }

    private  String tempslivraison;
    private String prixlivraison;
    private String Description;
    private String ImageUrl;


    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getCuisson() {
        return cuisson;
    }

    public void setCuisson(String cuisson) {
        this.cuisson = cuisson;
    }

    public String getPlatprix() {
        return platprix;
    }

    public void setPlatprix(String platprix) {
        this.platprix = platprix;
    }

    public String getTempslivraison() {
        return tempslivraison;
    }

    public void setTempslivraison(String tempslivraison) {
        this.tempslivraison = tempslivraison;
    }

    public String getPrixlivraison() {
        return prixlivraison;
    }

    public void setPrixlivraison(String prixlivraison) {
        this.prixlivraison = prixlivraison;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

   }
