package com.example.testapp.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testapp.data.model.Movie
import com.example.testapp.data.model.MovieDetails
import com.example.testapp.domain.usecase.MovieUsecase
import com.example.testapp.util.Consumer
import kotlinx.coroutines.launch

class MovieViewModel (private val usecase: MovieUsecase) : ViewModel() {
    val movieListLiveData = MutableLiveData<Consumer<Pair<List<Movie>, Boolean>>>()
    val selectedMovieDetails = MutableLiveData<MovieDetails>()
    var isLoading = false
    var isLastPage = false
    val movieList: MutableList<Movie> = mutableListOf()

    fun getMoviesList(searchTerm: String, firstCall: Boolean) {
        viewModelScope.launch {
            try {
                isLoading = true
                val response = usecase.getMoviesList(searchTerm, firstCall)
                if (response.Response == "True") {
                    movieList.addAll(response.Search)
                    movieListLiveData.postValue(Consumer(Pair(response.Search, firstCall)))
                    isLoading = false
                } else {
                    isLastPage = true
                    isLoading = false
                }
            } catch (e : Exception) {
                isLastPage = true
                isLoading = false
            }
        }
    }

    fun getMovieDetails(imdbID: String) {
        viewModelScope.launch {
            try {
                val details = usecase.getMovieDetails(imdbID)
                selectedMovieDetails.postValue(details)
            } catch (_: Exception) {
            }

        }
    }
}