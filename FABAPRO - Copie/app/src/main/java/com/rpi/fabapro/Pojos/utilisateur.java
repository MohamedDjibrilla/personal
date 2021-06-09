package com.rpi.fabapro.Pojos;

public class utilisateur {
   private  String Username;
   private  String Email;
   private  String Password;
   private String phone;
   private String Profession;
   private String recommande;

    public String getUsername() {
        return Username;
    }

    public void setUsername(String rname) {
        Username = rname;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getProfession() {
        return Profession;
    }

    public void setProfession(String profession) {
        Profession = profession;
    }

    public String getRecommande() {
        return recommande;
    }

    public void setRecommande(String recommande) {
        this.recommande = recommande;
    }

    public utilisateur(String ame, String email, String password, String phone, String profession, String recommande) {
        this.Username = ame;
        this.Email = email;
        this.Password = password;
        this.phone = phone;
        this.Profession = profession;
        this.recommande = recommande;  }

public utilisateur(){}
}
