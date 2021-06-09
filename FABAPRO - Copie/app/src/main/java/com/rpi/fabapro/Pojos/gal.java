package com.rpi.fabapro.Pojos;

public class gal {
    private String titre;
    private String description;
    private String ImageUrl;

    public String getPkey() {
        return pkey;
    }

    public void setPkey(String pkey) {
        this.pkey = pkey;
    }

    private String pkey;

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }


    public gal(){}

    public gal(String titre, String description, String imageUrl, String key) {
        this.titre = titre;
        this.description = description;
        ImageUrl = imageUrl;
        pkey=key;
    }


}
