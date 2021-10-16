
package com.example.moviedisplay

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object MovieRepository {

    private val api: API //Instance of API interface

    init {

        /**
         * Initiate Retrofit by using it's build function
         */
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        /**
         * Initiate api by pre-initiated retrofit
         */

        api = retrofit.create(API::class.java)
    }

    fun getUpComingMovies(
        page: Int = 1, //default
        onSuccess: (movies: MutableList<MovieInfo>) -> Unit,
        onError: () -> Unit
    ) {
        api.fetchUpComingMovies(pageNo = page)
            .enqueue(object : Callback<FetchResponse> {
                override fun onResponse(
                    call: Call<FetchResponse>,
                    response: Response<FetchResponse>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()

                        if (responseBody != null) {
                            onSuccess.invoke(responseBody.movieListing)
                        } else {
                            onError.invoke()
                        }
                    }
                }

                override fun onFailure(call: Call<FetchResponse>, t: Throwable) {
                    onError.invoke()
                }
            })
    }

    /**
     * Fetching popular movies
     * page 1 is default
     */

    fun getPopularMovies(
        page: Int = 1, //default
        onSuccess: (movies: MutableList<MovieInfo>) -> Unit,
        onError: () -> Unit
    ) {
        api.fetchPopularMovies(pageNo = page)
            .enqueue(object : Callback<FetchResponse> {
                override fun onResponse(
                    call: Call<FetchResponse>,
                    response: Response<FetchResponse>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()

                        if (responseBody != null) {
                            onSuccess.invoke(responseBody.movieListing)
                        } else {
                            onError.invoke()
                        }
                    }
                }

                override fun onFailure(call: Call<FetchResponse>, t: Throwable) {
                    onError.invoke()
                }
            })
    }

    fun storeUpComingMovies(
        onSuccess: (movies: MutableList<MovieInfo>) -> Unit,
        onError: () -> Unit
    ) {
        api.fetchAllUpComingMovies()
            .enqueue(object : Callback<FetchResponse> {
                override fun onResponse(
                    call: Call<FetchResponse>,
                    response: Response<FetchResponse>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()

                        if (responseBody != null) {
                            onSuccess.invoke(responseBody.movieListing)
                        } else {
                            onError.invoke()
                        }
                    }
                }

                override fun onFailure(call: Call<FetchResponse>, t: Throwable) {
                    onError.invoke()
                }
            })
    }

    fun storePopularMovies(
            onSuccess: (movies: MutableList<MovieInfo>) -> Unit,
            onError: () -> Unit
    ) {
        api.fetchAllPopularMovies()
                .enqueue(object : Callback<FetchResponse> {
                    override fun onResponse(
                            call: Call<FetchResponse>,
                            response: Response<FetchResponse>
                    ) {
                        if (response.isSuccessful) {
                            val responseBody = response.body()

                            if (responseBody != null) {
                                onSuccess.invoke(responseBody.movieListing)
                            } else {
                                onError.invoke()
                            }
                        }
                    }

                    override fun onFailure(call: Call<FetchResponse>, t: Throwable) {
                        onError.invoke()
                    }
                })
    }

}