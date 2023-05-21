package com.example.diploma_v2.api;

import com.example.diploma_v2.entity.Employees;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface LoginAPI {
    @GET("users/login/{login}")
    Call<Employees>findByLogin(@Path("login") String login);
}
