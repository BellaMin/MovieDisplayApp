package com.example.moviedisplay

/**
 * Data class
 * Store movies' information
 */

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class MovieInfo(
    @SerializedName("id") val mID: Long,//movie ID
    @SerializedName("title") val mTittle: String,//movie title
    @SerializedName("overview") val mDescription: String,//movie description
    @SerializedName("poster_path") val mPoster: String,//movie poster path
    @SerializedName("backdrop_path") val mBD: String,//movie backdrop poster path
    @SerializedName("vote_average") val mRating: Float,//movie rating
    @SerializedName("release_date") val mReleaseD: String //movie release date
) : Serializable
