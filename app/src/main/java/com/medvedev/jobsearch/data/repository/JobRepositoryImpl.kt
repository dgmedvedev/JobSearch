package com.medvedev.jobsearch.data.repository

import com.medvedev.jobsearch.data.mapper.toOfferDomainList
import com.medvedev.jobsearch.data.mapper.toVacancyDomainList
import com.medvedev.jobsearch.data.network.ApiService
import com.medvedev.jobsearch.domain.model.offer.Offer
import com.medvedev.jobsearch.domain.model.vacancy.Vacancy
import com.medvedev.jobsearch.domain.repository.JobRepository

class JobRepositoryImpl(private val apiService: ApiService) : JobRepository {
    override suspend fun getOffers(): List<Offer> =
        apiService.getOffers().offers?.toOfferDomainList() ?: listOf()

    override suspend fun getVacancies(): List<Vacancy> =
        apiService.getVacancies().vacancies?.toVacancyDomainList() ?: listOf()

    override suspend fun getVacanciesFavorite(): List<Vacancy> =
        getVacancies().filter { vacancy -> vacancy.isFavorite }
}