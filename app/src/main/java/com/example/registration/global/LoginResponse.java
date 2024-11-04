package com.example.registration.global;

import java.util.Date;

public class LoginResponse {
    private int id;
    private String email;
    private Date expiration;

    public LoginResponse(int id, String email, Date expiration) {
        this.id = id;
        this.email = email;
        this.expiration = expiration;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getExpiration() {
        return expiration;
    }

    public void setExpiration(Date expiration) {
        this.expiration = expiration;
    }
}
