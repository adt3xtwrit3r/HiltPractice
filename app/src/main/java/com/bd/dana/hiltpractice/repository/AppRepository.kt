package com.bd.dana.hiltpractice.repository

import com.bd.dana.hiltpractice.api.endpoint.ApiService
import com.bd.dana.hiltpractice.api.endpoint.ApiService2
import com.bd.dana.hiltpractice.api.models.app_model.AppTable
import com.bd.dana.hiltpractice.db.AppDao
import javax.inject.Inject

class AppRepository

@Inject
constructor
    (private val apiService: ApiService,
     private val apiService2: ApiService2,
     private val appDao: AppDao) {

    suspend fun getTvShows() = apiService.getTvShows()

    suspend fun getTvShowEpi() = apiService2.getTvShowsEpi()

    suspend fun getAllInstalledApps(): List<AppTable> {
        return appDao.getAllInstalledApps()
    }

    suspend fun insertAllInstalledApp(appList: List<AppTable>): List<Long> {
        return appDao.insertInstalledApp(appList)
    }

    suspend fun deleteAllAppData() {
        return appDao.deleteAllAppData()
    }
}