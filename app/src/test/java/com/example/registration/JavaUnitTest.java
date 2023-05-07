package com.example.registration;

import com.example.registration.retrofit.customerApi.CustomerApi;
import com.example.registration.retrofit.RetrofitService;
import com.example.registration.model.users.Customer;

import org.junit.Test;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JavaUnitTest {
    RetrofitService retrofitService = new RetrofitService();
    CustomerApi customerApi =retrofitService.getRetrofit().create(CustomerApi.class);
    @Test
    public void addCustomer(){
        String name = "Andy";
        String surname = "Davy";
        String email = "and@gmail.com";
        String password ="and123";
        Customer customer = new Customer(name, surname, email, password);
        customerApi.save(customer);
    }

//        @Test
    public void printAll(){
        Call<List<Customer>> callList = customerApi.getCustomersAll();
        callList.enqueue(new Callback<List<Customer>>() {
            @Override
            public void onResponse(Call<List<Customer>> call, Response<List<Customer>> response) {
                if(response.isSuccessful()){
                    List<Customer> customers = response.body();
                    System.out.println(customers.get(0).getName());
                    System.out.println("yees");
                }
                else{
                    System.out.println("error");
                }
            }
            @Override
            public void onFailure(Call<List<Customer>> call, Throwable t) {
                System.out.println("ERROR");
            }
        });
    }

}
