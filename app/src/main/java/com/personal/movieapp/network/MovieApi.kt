package com.personal.movieapp.network

import com.personal.movieapp.model.MovieDetail
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Path

interface MovieApi {

    @GET("/3/movie/{id}")
    suspend fun getMovieDetails(
        @Path("id") name: String, @Query("api_key") apiKey: String
    ): MovieDetail
}