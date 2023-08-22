package com.example.registration.model.users;

public class ManagerProfileDTO {
    private int id;
    private String name;
    private String surname;
    private String email;

    private String departmentDTOstring;

    public ManagerProfileDTO() {
    }

    public ManagerProfileDTO(int id, String name, String surname, String email) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
    }

    public ManagerProfileDTO(String name, String surname, String email) {
        this.name = name;
        this.surname = surname;
        this.email = email;
    }

    public ManagerProfileDTO(String name, String surname, String email, String departmentDTOstring) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.departmentDTOstring = departmentDTOstring;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDepartmentDTOstring() {
        return departmentDTOstring;
    }

    public void setDepartmentDTOstring(String departmentDTOstring) {
        this.departmentDTOstring = departmentDTOstring;
    }
}
