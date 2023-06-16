package com.example.registration.task;

public class DepartmentCustomCount {
    private String departmentName;
    private int customCount;

    public DepartmentCustomCount(String departmentName, int customCount) {
        this.departmentName = departmentName;
        this.customCount = customCount;
    }

    public int getCustomCount() {
        return customCount;
    }

    public void setCustomCount(int customCount) {
        this.customCount = customCount;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }
}
