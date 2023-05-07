package com.example.registration.retrofit.managerApi;

import com.example.registration.model.users.Manager;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ManagerApi {
    @GET("/manager/get-all")
    Call<List<Manager>> getManagersAll();
}
