package com.example.moviedisplay

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface API {

    @GET("movie/popular")
    fun fetchPopularMovies(
        @Query("api_key") key: String = "a2bcdca329c931aa395a67b9b8b4eebf",
        @Query("page") pageNo: Int
    ): Call<FetchResponse>

    @GET("movie/upcoming")
    fun fetchUpComingMovies(
            @Query("api_key") key: String = "a2bcdca329c931aa395a67b9b8b4eebf",
            @Query("page") pageNo: Int
    ): Call<FetchResponse>

    @GET("movie/popular")
    fun fetchAllPopularMovies(
            @Query("api_key") key: String = "a2bcdca329c931aa395a67b9b8b4eebf"
    ): Call<FetchResponse>

    @GET("movie/upcoming")
    fun fetchAllUpComingMovies(
        @Query("api_key") key: String = "a2bcdca329c931aa395a67b9b8b4eebf"
    ): Call<FetchResponse>

}