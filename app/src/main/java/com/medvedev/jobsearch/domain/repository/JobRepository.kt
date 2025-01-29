package com.medvedev.jobsearch.domain.repository

import com.medvedev.jobsearch.domain.model.offer.Offer
import com.medvedev.jobsearch.domain.model.vacancy.Vacancy
import com.medvedev.jobsearch.domain.model.vacancy.VacancyFavorite

interface JobRepository {
    suspend fun getOffers(): List<Offer>
    suspend fun getVacancies(): List<Vacancy>
    suspend fun getVacanciesFavorite(): List<VacancyFavorite>
}