package com.bd.dana.hiltpractice.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bd.dana.hiltpractice.api.models.app_model.AppTable

@Dao
interface AppDao {

    @Query("SELECT * FROM appTable")
    suspend fun getAllInstalledApps(): List<AppTable>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertInstalledApp(appList: List<AppTable>): List<Long>

    @Query("DELETE FROM appTable")
    suspend fun deleteAllAppData()

}