package com.example.diploma_v2.api;

import com.example.diploma_v2.entity.Orders;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface OrdersAPI {
    @GET("/rest/orders/all")
    Call<List<Orders>> findAll();

    @PUT("/rest/orders/{id}")
    Call<Orders> updateStatus(@Path("id") Long id, @Body Orders body);
}
