package com.medvedev.jobsearch.di

import com.medvedev.jobsearch.presentation.viewmodel.FavoriteVacanciesViewModel
import com.medvedev.jobsearch.presentation.viewmodel.MainViewModel
import com.medvedev.jobsearch.presentation.viewmodel.RelevantVacanciesViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    viewModel<MainViewModel> {
        MainViewModel(
            getOffersUseCase = get(),
            getVacanciesUseCase = get(),
            getVacanciesFavoriteUseCase = get(),
            insertVacancyFavoriteUseCase = get(),
            deleteVacancyFavoriteUseCase = get()
        )
    }

    viewModel<FavoriteVacanciesViewModel> {
        FavoriteVacanciesViewModel(
            getVacanciesFavoriteUseCase = get(),
            insertVacancyFavoriteUseCase = get(),
            deleteVacancyFavoriteUseCase = get()
        )
    }

    viewModel<RelevantVacanciesViewModel> {
        RelevantVacanciesViewModel(
            getVacanciesUseCase = get(),
            getVacanciesFavoriteUseCase = get(),
            insertVacancyFavoriteUseCase = get(),
            deleteVacancyFavoriteUseCase = get()
        )
    }
}