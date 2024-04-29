package com.example.registration.model.users;

public class User {
    String name;
    String email;
    String password;
    String nationality;
    Integer age;
    String gender;


    public User(String name, String email, String password, String nationality, Integer age, Gender gender) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.nationality = nationality;
        this.age = age;
        this.gender = gender.text;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public enum Gender {
        MALE("чоловічий"),
        FEMALE("жіночий");

        private final String text;

        Gender(String text) {
            this.text = text;
        }

        public String getText() {
            return text;
        }
    }
}


