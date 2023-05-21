package com.example.diploma_v2.api;

import com.example.diploma_v2.entity.Order;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface OrderAPI {
    @GET("/rest/order/{id}")
    Call<List<Order>> findById(@Path("id") Long id);

    @PUT("/rest/order/{id}/{itemId}")
    Call<Order> updateOrder(@Path("id") Long id, @Path("itemId") Long itemId, @Body Order body);

}
