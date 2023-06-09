package com.example.tmdbestud.repository

import com.example.tmdbestud.api.request.MoviesApi
import com.example.tmdbestud.api.response.GetMoviesResponse

class DataRepository(private val moviesApi: MoviesApi) {

    suspend fun getPopularMovies(page: Int): GetMoviesResponse {
        return moviesApi.getPopularMovies(page)
    }

    suspend fun getTopRatedMovies(page: Int): GetMoviesResponse {
        return moviesApi.getTopRatedMovies(page)
    }

    suspend fun getUpcomingMovies(page: Int): GetMoviesResponse {
        return moviesApi.getUpcomingMovies(page)
    }
}

