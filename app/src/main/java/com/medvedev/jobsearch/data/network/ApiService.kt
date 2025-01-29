package com.medvedev.jobsearch.data.network

import com.google.gson.JsonObject
import retrofit2.http.GET

interface ApiService {

    @GET("u/0/uc?id=1z4TbeDkbfXkvgpoJprXbN85uCcD7f00r&export=download")
    suspend fun getJson(): JsonObject
}