package com.bd.dana.hiltpractice.api.models.app_model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "appTable")
data class AppTable(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private val id: Int = 0,

    @ColumnInfo(name = "appName")
    private val appName: String?,

    @ColumnInfo(name = "packageName")
    private val packageName: String?,

    @ColumnInfo(name = "appSchedule")
    private val appSchedule:Long?,

    @ColumnInfo(name = "scheduleRequestCode")
    private val scheduleRequestCode:Int?

)
