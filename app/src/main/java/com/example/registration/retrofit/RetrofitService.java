package com.example.registration.retrofit;

import com.google.gson.Gson;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitService {
    private Retrofit retrofit;

    public RetrofitService() {
        initialiseRetrofit();
    }

    private void initialiseRetrofit() {
        retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.24.122:5001")
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .build();
    }

    public Retrofit getRetrofit(){
        return retrofit;
    }
}
