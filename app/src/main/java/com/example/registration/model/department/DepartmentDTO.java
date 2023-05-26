package com.example.registration.model.department;

public class DepartmentDTO {
    private int id;

    private String departmentName;

    public DepartmentDTO(){}

    public DepartmentDTO(String departmentName){
        this.departmentName = departmentName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }
}
