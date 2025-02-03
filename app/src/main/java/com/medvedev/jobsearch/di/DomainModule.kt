package com.medvedev.jobsearch.di

import com.medvedev.jobsearch.domain.usecase.DeleteVacancyFavoriteUseCase
import com.medvedev.jobsearch.domain.usecase.GetOffersUseCase
import com.medvedev.jobsearch.domain.usecase.GetVacanciesFavoriteUseCase
import com.medvedev.jobsearch.domain.usecase.GetVacanciesUseCase
import com.medvedev.jobsearch.domain.usecase.InsertVacancyFavoriteUseCase
import org.koin.dsl.module

val domainModule = module {

    factory<GetOffersUseCase> {
        GetOffersUseCase(repository = get())
    }

    factory<GetVacanciesUseCase> {
        GetVacanciesUseCase(repository = get())
    }
    factory<GetVacanciesFavoriteUseCase> {
        GetVacanciesFavoriteUseCase(repository = get())
    }
    factory<InsertVacancyFavoriteUseCase> {
        InsertVacancyFavoriteUseCase(repository = get())
    }
    factory<DeleteVacancyFavoriteUseCase> {
        DeleteVacancyFavoriteUseCase(repository = get())
    }
}