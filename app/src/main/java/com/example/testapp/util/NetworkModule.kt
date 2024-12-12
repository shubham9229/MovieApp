package com.example.testapp.util

import com.example.testapp.data.repo.MovieRepo
import com.example.testapp.data.service.MovieService
import com.example.testapp.domain.usecase.MovieUsecase
import com.example.testapp.presentation.viewmodel.MovieViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {

    single { getRetrofitClient() }

    single { getMovieService(get()) }

    viewModel { MovieViewModel(get()) }

    factory { MovieUsecase(get()) }

    factory { MovieRepo(get()) }

}

fun getRetrofitClient(): Retrofit {
    return Retrofit.Builder()
        .baseUrl("https://www.omdbapi.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

fun getMovieService(retrofit: Retrofit): MovieService {
    return retrofit.create(MovieService::class.java)
}