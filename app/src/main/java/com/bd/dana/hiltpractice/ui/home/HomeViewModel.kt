package com.bd.dana.hiltpractice.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bd.dana.hiltpractice.api.models.tv_show.TvShowsModelItem
import com.bd.dana.hiltpractice.api.models.tv_show_epi.TvShowEpi
import com.bd.dana.hiltpractice.helper.Constants.exhaustive
import com.bd.dana.hiltpractice.repository.AppRepository
import com.haroldadmin.cnradapter.NetworkResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel
@Inject
constructor(private val repository: AppRepository) : ViewModel() {

    fun getTvShows(): LiveData<List<TvShowsModelItem>> {

        val responseBody = MutableLiveData<List<TvShowsModelItem>>()
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.getTvShows()
            withContext(Dispatchers.Main) {
                when (response) {
                    is NetworkResponse.Success -> {
                        responseBody.value = response.body
                    }
                    is NetworkResponse.ServerError -> {
                        val message = "দুঃখিত, এই মুহূর্তে আমাদের সার্ভার কানেকশনে সমস্যা হচ্ছে, কিছুক্ষণ পর আবার চেষ্টা করুন"
                    }
                    is NetworkResponse.NetworkError -> {
                        val message = "দুঃখিত, এই মুহূর্তে আপনার ইন্টারনেট কানেকশনে সমস্যা হচ্ছে"
                    }
                    is NetworkResponse.UnknownError -> {
                        val message = "কোথাও কোনো সমস্যা হচ্ছে, আবার চেষ্টা করুন"
                    }
                }.exhaustive
            }
        }
        return responseBody
    }

    fun getTvShowsEpi(): LiveData<TvShowEpi> {

        val responseBody = MutableLiveData<TvShowEpi>()
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.getTvShowEpi()
            withContext(Dispatchers.Main) {
                when (response) {
                    is NetworkResponse.Success -> {
                        responseBody.value = response.body
                    }
                    is NetworkResponse.ServerError -> {
                        val message = "দুঃখিত, এই মুহূর্তে আমাদের সার্ভার কানেকশনে সমস্যা হচ্ছে, কিছুক্ষণ পর আবার চেষ্টা করুন"
                    }
                    is NetworkResponse.NetworkError -> {
                        val message = "দুঃখিত, এই মুহূর্তে আপনার ইন্টারনেট কানেকশনে সমস্যা হচ্ছে"
                    }
                    is NetworkResponse.UnknownError -> {
                        val message = "কোথাও কোনো সমস্যা হচ্ছে, আবার চেষ্টা করুন"
                    }
                }.exhaustive
            }
        }
        return responseBody
    }

}