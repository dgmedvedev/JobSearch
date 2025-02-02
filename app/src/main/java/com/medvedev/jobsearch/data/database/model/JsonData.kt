package com.medvedev.jobsearch.data.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "json_vacancies")
data class JsonData(
    @PrimaryKey(autoGenerate = false)
    val id: Int = 0,
    val jsonVacancies: String
)