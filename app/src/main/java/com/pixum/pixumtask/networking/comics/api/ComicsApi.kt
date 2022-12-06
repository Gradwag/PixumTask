package com.pixum.pixumtask.networking.comics.api

import com.pixum.pixumtask.networking.comics.model.ComicResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ComicsApi {
    @GET("comics")
    fun getComics(
        @Query("ts") ts: Int,
        @Query("apikey") apikey: String,
        @Query("hash") hash: String
    ): Call<ComicResponse>
}