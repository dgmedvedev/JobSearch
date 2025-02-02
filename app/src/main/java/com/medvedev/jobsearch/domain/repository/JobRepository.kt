package com.medvedev.jobsearch.domain.repository

import com.medvedev.jobsearch.domain.model.offer.Offer
import com.medvedev.jobsearch.domain.model.vacancy.Vacancy

interface JobRepository {
    suspend fun getOffers(): List<Offer>
    suspend fun getVacancies(): List<Vacancy>
    suspend fun getVacanciesFavorite(): List<Vacancy>
    suspend fun insertVacancyFavorite(vacancy: Vacancy)
    suspend fun deleteVacancyFavorite(vacancy: Vacancy)
}