package com.medvedev.jobsearch.domain.usecase

import com.medvedev.jobsearch.domain.model.offer.Offer
import com.medvedev.jobsearch.domain.repository.JobRepository

class GetOffersUseCase(private val repository: JobRepository) {
    suspend operator fun invoke(): List<Offer> =
        repository.getOffers()
}