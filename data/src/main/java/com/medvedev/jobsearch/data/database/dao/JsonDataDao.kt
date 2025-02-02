package com.medvedev.jobsearch.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.medvedev.jobsearch.data.database.model.JsonData

@Dao
interface JsonDataDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertJsonData(jsonData: JsonData)

    @Query("SELECT * FROM json_vacancies LIMIT 1")
    suspend fun getJsonData(): JsonData?

    @Query("UPDATE json_vacancies SET jsonVacancies = :jsonVacancies WHERE id = :id")
    suspend fun updateJsonVacancies(id: Int, jsonVacancies: String)
}