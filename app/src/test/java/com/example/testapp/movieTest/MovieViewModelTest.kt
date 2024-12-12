package com.example.testapp.movieTest

import com.example.testapp.Util.CoroutinesTestRule
import com.example.testapp.Util.getOrAwaitValue
import com.example.testapp.data.model.Movie
import com.example.testapp.data.model.MovieDetails
import com.example.testapp.data.model.MovieSearchResponse
import com.example.testapp.domain.usecase.MovieUsecase
import com.example.testapp.presentation.viewmodel.MovieViewModel
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
@ExperimentalCoroutinesApi
class MovieViewModelTest {

    @get:Rule
    var corountinesTestRule = CoroutinesTestRule()

    @Mock
    lateinit var usecase: MovieUsecase
    lateinit var viewModel: MovieViewModel


    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        viewModel = MovieViewModel(usecase)
    }

    @Test
    fun getMovieListTest() = corountinesTestRule.runBlockingTest {
        val apiModel = MovieSearchResponse(Search = listOf(Movie("Avataar","2005","qwefd","")), totalResults = "1", Response = "True")
        whenever(usecase.getMoviesList(any(), any())) doReturn apiModel

        viewModel.getMoviesList("Avataar", true)
        val data = viewModel.movieListLiveData.getOrAwaitValue()
        val result = data.getContentIfNotConsumed()?.first

        assertEquals(apiModel.Search, result)

    }

}