package com.example.registration.model.users;

import androidx.annotation.NonNull;

import com.example.registration.model.User;

public class Customer extends User {

//    private int id;
//    private String name;
//    private String surname;
//    private String email;
//    private String password;
//
//    public Customer(){}
//    public Customer(String name, String surname, String email, String password ){
//        this.name=name;
//        this.surname=surname;
//        this.email=email;
//        this.password=password;
//    }


    public Customer() {
    }

    public Customer(String name, String surname, String email, String password) {
        super(name, surname, email, password);
    }

    @Override
    public int getId() {
        return super.getId();
    }

    @Override
    public void setId(int id) {
        super.setId(id);
    }

    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public void setName(String name) {
        super.setName(name);
    }

    @Override
    public String getSurname() {
        return super.getSurname();
    }

    @Override
    public void setSurname(String surname) {
        super.setSurname(surname);
    }

    @Override
    public String getEmail() {
        return super.getEmail();
    }

    @Override
    public void setEmail(String email) {
        super.setEmail(email);
    }

    @Override
    public String getPassword() {
        return super.getPassword();
    }

    @Override
    public void setPassword(String password) {
        super.setPassword(password);
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}

