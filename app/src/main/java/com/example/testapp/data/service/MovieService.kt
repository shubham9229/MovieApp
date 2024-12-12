package com.example.testapp.data.service

import com.example.testapp.data.model.MovieDetails
import com.example.testapp.data.model.MovieSearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieService {
    @GET("/")
    suspend fun getMoviesList(
        @Query("apikey") apiKey: String,
        @Query("s") search: String,
        @Query("page") page: Int
    ): MovieSearchResponse

    @GET("/")
    suspend fun getMovieDetails(
        @Query("apikey") apiKey: String,
        @Query("i") imdbID: String
    ): MovieDetails

}