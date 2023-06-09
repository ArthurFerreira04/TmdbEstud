package com.example.tmdbestud.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tmdbestud.api.response.Movie
import com.example.tmdbestud.repository.DataRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MainViewModel(private val dataRepository: DataRepository) : ViewModel(){

    val popularMovie = MutableLiveData<List<Movie>>()
    val ratedMovies = MutableLiveData<List<Movie>>()
    val upcomingMovies = MutableLiveData<List<Movie>>()
    val movieError : MutableLiveData<Throwable> = MutableLiveData()

    fun initializeLaunch() {
        viewModelScope.launch {
            runCatching {
                val popularResult = dataRepository.getPopularMovies(1)
                val topRatedResult = dataRepository.getTopRatedMovies(1)
                val upcomingResult = dataRepository.getUpcomingMovies(1)

                popularMovie.postValue(popularResult.movies)
                ratedMovies.postValue(topRatedResult.movies)
                upcomingMovies.postValue(upcomingResult.movies)
            }.onFailure {
                movieError.postValue(it)
            }
        }
    }

    fun initializeAsync() {
        viewModelScope.launch {
            try {
                val popularResult = async { dataRepository.getPopularMovies(1) }
                val topRatedResult = async { dataRepository.getTopRatedMovies(1) }
                val upcomingResult = async { dataRepository.getUpcomingMovies(1) }

                popularMovie.postValue(popularResult.await().movies)
                ratedMovies.postValue(topRatedResult.await().movies)
                upcomingMovies.postValue(upcomingResult.await().movies)
            } catch (throwable: Throwable) {
                movieError.postValue(throwable)
            }
        }
    }



}