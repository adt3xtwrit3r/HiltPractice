package com.bd.dana.hiltpractice.repository

import com.bd.dana.hiltpractice.api.endpoint.ApiService
import com.bd.dana.hiltpractice.api.endpoint.ApiService2
import javax.inject.Inject

class AppRepository

@Inject
constructor
    (private val apiService: ApiService,
     private val apiService2: ApiService2) {

    suspend fun getTvShows() = apiService.getTvShows()

    suspend fun getTvShowEpi() = apiService2.getTvShowsEpi()
}