package com.example.registration.task;

import java.util.List;

import retrofit2.http.GET;

public interface CustomCountApi {
    @GET("/counts")
    public List<DepartmentCustomCount> getDepartmentCustomCounts() {
        return departmentService.getDepartmentCustomCounts();
    }
}
