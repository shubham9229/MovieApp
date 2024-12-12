package com.example.testapp.domain.usecase

import com.example.testapp.data.model.MovieDetails
import com.example.testapp.data.model.MovieSearchResponse
import com.example.testapp.data.repo.MovieRepo

class MovieUsecase(private val repo: MovieRepo) {

    suspend fun getMoviesList(searchTerm: String, firstCall: Boolean): MovieSearchResponse {
        return repo.getMoviesList(searchTerm, firstCall)
    }

    suspend fun getMovieDetails(imdbID: String): MovieDetails {
        return repo.getMovieDetails(imdbID)
    }

}