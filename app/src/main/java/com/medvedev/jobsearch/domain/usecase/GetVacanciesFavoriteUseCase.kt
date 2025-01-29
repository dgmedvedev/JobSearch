package com.medvedev.jobsearch.domain.usecase

import com.medvedev.jobsearch.domain.model.vacancy.VacancyFavorite
import com.medvedev.jobsearch.domain.repository.JobRepository

class GetVacanciesFavoriteUseCase(private val repository: JobRepository) {
    suspend operator fun invoke(): List<VacancyFavorite> =
        repository.getVacanciesFavorite()
}