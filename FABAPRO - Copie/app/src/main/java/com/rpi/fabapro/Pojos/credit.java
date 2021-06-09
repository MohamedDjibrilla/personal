package com.rpi.fabapro.Pojos;

public class credit {

    private String UID;
    private String username;
    private Integer credit;


    public credit(){}

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }





    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getCredit() {
        return credit;
    }

    public void setCredit(Integer credit) {
        this.credit = credit;
    }

    public credit(String uid, String username, Integer credit) {
        this.UID = uid;
        this.username = username;
        this.credit = credit;
    }



}
