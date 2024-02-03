package com.vivekishere.foodapp.Retrofit;

import android.content.Context;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {
    public Retrofit retrofit ;

    public static final String BASE_URL ="https://www.themealdb.com/api/json/v1/1/";
    public static RetrofitInstance retrofitInstance;
    public static synchronized RetrofitInstance getInstance(){
        if (retrofitInstance==null){
            retrofitInstance = new RetrofitInstance();
        }
        return retrofitInstance;
    }

    public RetrofitInstance() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
   public RetrofitApi retrofitApi() {
        return retrofit.create(RetrofitApi.class);
    }
}
