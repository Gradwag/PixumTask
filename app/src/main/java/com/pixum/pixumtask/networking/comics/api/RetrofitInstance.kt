package com.pixum.pixumtask.networking.comics.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private const val API_URL = "http://gateway.marvel.com/v1/public/"   // ts=1&apikey=63dedebb703655f52a1b47a9eb665974&hash=684390565c45415107a20ed7088e2b47
    val api : ComicsApi by lazy {
        Retrofit.Builder()
            .baseUrl(API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ComicsApi::class.java)
    }
}