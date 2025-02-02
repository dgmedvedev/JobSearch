package com.medvedev.jobsearch.domain.usecase

import com.medvedev.jobsearch.domain.model.vacancy.Vacancy
import com.medvedev.jobsearch.domain.repository.JobRepository

class InsertVacancyFavoriteUseCase(private val repository: JobRepository) {
    suspend operator fun invoke(vacancy: Vacancy) {
        repository.insertVacancyFavorite(vacancy = vacancy)
    }
}