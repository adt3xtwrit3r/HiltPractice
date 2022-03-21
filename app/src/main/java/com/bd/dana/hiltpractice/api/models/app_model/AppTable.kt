package com.bd.dana.hiltpractice.api.models.app_model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "appTable")
data class AppTable(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0,

    @ColumnInfo(name = "appName")
    var appName: String? = "",

    @ColumnInfo(name = "packageName")
    var packageName: String? = "",

    @ColumnInfo(name = "appSchedule")
    var appSchedule:Long? = 0,

    @ColumnInfo(name = "appScheduled")
    var appScheduled:Boolean? = false,

    @ColumnInfo(name = "scheduleRequestCode")
    var scheduleRequestCode:Int? = 0

) : Parcelable
