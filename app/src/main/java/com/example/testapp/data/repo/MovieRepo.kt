package com.example.testapp.data.repo

import com.example.testapp.data.model.MovieDetails
import com.example.testapp.data.model.MovieSearchResponse
import com.example.testapp.data.service.MovieService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MovieRepo(private val apiService: MovieService) {

    private var pageNo = 1
    private val apiKey = "caa7cd70"

    suspend fun getMoviesList(searchTerm: String, firstCall: Boolean): MovieSearchResponse = withContext(Dispatchers.IO) {
        if (firstCall) {
            pageNo = 1
        }
        return@withContext apiService.getMoviesList(apiKey,searchTerm, pageNo++)
    }

    suspend fun getMovieDetails(imdbID: String): MovieDetails = withContext(Dispatchers.IO){
        return@withContext apiService.getMovieDetails(apiKey, imdbID)
    }

}