package com.example.registration.model.users;

public class User {
    String ownerId;
    String userToken;
    String name;
    String email;
    String password;
    String nationality;
    Integer age;
    String gender;

    public User() {
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

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

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
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


