package com.example.newspaper.apidata;


import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface RestApi {

    @GET
    Call<JsonObject> getFreshNews(@Url String url);

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://newsapi.org/v2/top-headlines/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}
