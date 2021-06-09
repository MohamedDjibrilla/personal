package com.rpi.fabapro.Pojos;

public class crediter {
    private String id;
    private String recepteur;
    private String canal;
    private String transfertid;
    private String montant;

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    private String Status;

public crediter(){}
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRecepteur() {
        return recepteur;
    }

    public void setRecepteur(String recepteur) {
        this.recepteur = recepteur;
    }

    public String getCanal() {
        return canal;
    }

    public void setCanal(String canal) {
        this.canal = canal;
    }

    public String getTransfertid() {
        return transfertid;
    }

    public void setTransfertid(String transfertid) {
        this.transfertid = transfertid;
    }

    public String getMontant() {
        return montant;
    }

    public void setMontant(String montant) {
        this.montant = montant;
    }



    public crediter(String id, String recepteur, String canal, String transfertid, String montant, String statut) {
        this.id = id;
        this.recepteur = recepteur;
        this.canal = canal;
        this.transfertid = transfertid;
        this.montant = montant;
        this.Status=statut;
    }




    }
