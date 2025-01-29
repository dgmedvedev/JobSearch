package com.medvedev.jobsearch.data.network

import com.medvedev.jobsearch.data.network.model.offer.OffersDto
import com.medvedev.jobsearch.data.network.model.vacancy.VacanciesDto
import retrofit2.http.GET

interface ApiService {

    @GET("u/0/uc?id=1z4TbeDkbfXkvgpoJprXbN85uCcD7f00r&export=download")
    suspend fun getOffers(): OffersDto

    @GET("u/0/uc?id=1z4TbeDkbfXkvgpoJprXbN85uCcD7f00r&export=download")
    suspend fun getVacancies(): VacanciesDto
}