package com.medvedev.jobsearch.data.network

import com.medvedev.jobsearch.data.network.model.offer.OffersDto
import com.medvedev.jobsearch.data.network.model.vacancy.VacanciesDto
import retrofit2.http.GET

interface ApiService {

    @GET(GET_PARAM_VALUE)
    suspend fun getOffers(): OffersDto

    @GET(GET_PARAM_VALUE)
    suspend fun getVacancies(): VacanciesDto

    companion object {
        private const val GET_PARAM_VALUE =
            "u/0/uc?id=1z4TbeDkbfXkvgpoJprXbN85uCcD7f00r&export=download"
    }
}