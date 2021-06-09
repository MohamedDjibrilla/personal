package com.rpi.fabapro.Pojos;

public class clients {
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

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public String getStatusmessage() {
        return statusmessage;
    }

    public void setStatusmessage(String statusmessage) {
        this.statusmessage = statusmessage;
    }

    public String getCommunicationtype() {
        return communicationtype;
    }

    public void setCommunicationtype(String communicationtype) {
        this.communicationtype = communicationtype;
    }

    private String nom;
    private String profession;
    private String UID;
    private String statusmessage;
    private String communicationtype;

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    private String channel;

    public clients(){}

    public clients(String nom, String profession, String UID, String statusmessage, String communicationtype, String db) {
        this.nom = nom;
        this.profession = profession;
        this.UID = UID;
        this.statusmessage = statusmessage;
        this.communicationtype = communicationtype;
        this.channel=db;

    }


}
