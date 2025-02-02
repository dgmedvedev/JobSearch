package com.medvedev.jobsearch.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.medvedev.jobsearch.data.database.dao.JsonDataDao
import com.medvedev.jobsearch.data.database.model.JsonData

@Database(entities = [JsonData::class], version = 1, exportSchema = false)
abstract class JobDatabase : RoomDatabase() {

    abstract fun jsonDataDao(): JsonDataDao

    companion object {
        private const val DB_NAME = "vacancies.db"
        private var instance: JobDatabase? = null

        fun getInstance(context: Context): JobDatabase {
            instance?.let { return it }
            synchronized(this) {
                instance?.let { return it }
                val db = Room.databaseBuilder(
                    context.applicationContext,
                    JobDatabase::class.java,
                    DB_NAME
                ).build()
                instance = db
                return db
            }
        }
    }
}