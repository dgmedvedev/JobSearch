package com.medvedev.jobsearch.data.repository

import com.medvedev.jobsearch.data.network.ApiService
import com.medvedev.jobsearch.domain.model.offer.Offer
import com.medvedev.jobsearch.domain.model.vacancy.Vacancy
import com.medvedev.jobsearch.domain.model.vacancy.VacancyFavorite
import com.medvedev.jobsearch.domain.repository.JobRepository

class JobRepositoryImpl(private val apiService: ApiService) : JobRepository {
    override suspend fun getOffers(): List<Offer> {
        TODO("Not yet implemented")
    }

    override suspend fun getVacancies(): List<Vacancy> {
        TODO("Not yet implemented")
    }

    override suspend fun getVacanciesFavorite(): List<VacancyFavorite> {
        TODO("Not yet implemented")
    }
}