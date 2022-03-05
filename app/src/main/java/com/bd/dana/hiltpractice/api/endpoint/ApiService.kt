package com.bd.dana.hiltpractice.api.endpoint

import com.bd.dana.hiltpractice.api.models.ErrorResponse
import com.bd.dana.hiltpractice.api.models.tv_show.TvShowsModelItem
import com.bd.dana.hiltpractice.helper.Constants
import com.haroldadmin.cnradapter.NetworkResponse
import retrofit2.http.GET

interface ApiService {

    @GET(Constants.END_POINT)
    suspend fun getTvShows(): NetworkResponse<List<TvShowsModelItem>, ErrorResponse>

}