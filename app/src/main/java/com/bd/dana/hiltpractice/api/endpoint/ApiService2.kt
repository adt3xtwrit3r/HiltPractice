package com.bd.dana.hiltpractice.api.endpoint

import com.bd.dana.hiltpractice.api.models.ErrorResponse
import com.bd.dana.hiltpractice.api.models.tv_show_epi.TvShowEpi
import com.bd.dana.hiltpractice.helper.Constants
import com.haroldadmin.cnradapter.NetworkResponse
import retrofit2.http.GET

interface ApiService2 {
    @GET(Constants.END_POINT2)
    suspend fun getTvShowsEpi(): NetworkResponse<TvShowEpi, ErrorResponse>
}