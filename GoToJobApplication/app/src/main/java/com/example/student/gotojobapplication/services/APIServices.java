package com.example.student.gotojobapplication.services;

import com.example.student.gotojobapplication.models.ModelForRecycler;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIServices {

    @GET("api/")
    Call<ModelForRecycler> randomUsers(@Query("results") Integer results);
}
