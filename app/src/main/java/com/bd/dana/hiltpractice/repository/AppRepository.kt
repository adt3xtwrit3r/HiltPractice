package com.bd.dana.hiltpractice.repository

import com.bd.dana.hiltpractice.api.endpoint.ApiService
import javax.inject.Inject

class AppRepository

@Inject
constructor(private val apiService: ApiService) {
    suspend fun getTvShows() = apiService.getTvShows()
}