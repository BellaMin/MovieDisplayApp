package com.example.moviedisplay

import com.google.gson.annotations.SerializedName

/**
 * Data class
 *
 */

data class FetchResponse(
    @SerializedName("page") val pageNo: Int,
    @SerializedName("results") val movieListing: MutableList<MovieInfo>,
    @SerializedName("total_pages") val totalPages: Int
)