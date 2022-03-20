package com.bd.dana.hiltpractice.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.bd.dana.hiltpractice.api.models.app_model.AppTable

@Database (entities = [AppTable::class], version = 1, exportSchema = false)
abstract class AppDataBase: RoomDatabase() {

    abstract fun getAppDao(): AppDao

    companion object {
        private var DB_INSTANCE: AppDataBase? = null

        fun getAppDBInstance(context: Context): AppDataBase {

            if (DB_INSTANCE == null) {

                DB_INSTANCE = Room.databaseBuilder(context.applicationContext, AppDataBase::class.java, "APP_DB")
                    .allowMainThreadQueries()
                    .build()

            }

            return DB_INSTANCE!!

        }

    }

}