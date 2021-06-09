package com.rpi.fabapro.Pojos;

public class logger {
    private String email;
    private String userID;
    private String IMEI;
    private String pass;
    private String username;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }


    public logger(){}

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getIMEI() {
        return IMEI;
    }

    public void setIMEI(String IMEI) {
        this.IMEI = IMEI;
    }

    public logger(String email, String userID, String username, String IMEI, String pass) {

        this.email=email;
        this.userID=userID;
        this.IMEI = IMEI;
        this.pass= pass;
    }


}
