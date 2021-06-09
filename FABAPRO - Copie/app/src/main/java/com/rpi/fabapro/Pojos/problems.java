package com.rpi.fabapro.Pojos;

public class problems {
    public problems(){}
    public problems(String userid, String username, String status) {
        Userid = userid;
        Username = username;
        Status = status;
    }

    private String Userid;

    public String getUserid() {
        return Userid;
    }

    public void setUserid(String userid) {
        Userid = userid;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    private String Username;
    private String Status;
}
