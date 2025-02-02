package com.medvedev.jobsearch.app.presentation.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.medvedev.jobsearch.R
import com.medvedev.jobsearch.data.database.JobDatabase
import com.medvedev.jobsearch.data.network.ApiFactory
import com.medvedev.jobsearch.data.repository.JobRepositoryImpl
import com.medvedev.jobsearch.domain.usecase.DeleteVacancyFavoriteUseCase
import com.medvedev.jobsearch.domain.usecase.GetOffersUseCase
import com.medvedev.jobsearch.domain.usecase.GetVacanciesFavoriteUseCase
import com.medvedev.jobsearch.domain.usecase.GetVacanciesUseCase
import com.medvedev.jobsearch.domain.usecase.InsertVacancyFavoriteUseCase

class ViewModelFactory(private val context: Context) : ViewModelProvider.Factory {

    private val apiService by lazy { ApiFactory.apiService }

    private val jsonDataDao by lazy { JobDatabase.getInstance(context).jsonDataDao() }

    private val jobRepository by lazy {
        JobRepositoryImpl(
            apiService = apiService,
            jsonDataDao = jsonDataDao
        )
    }

    private val getOffersUseCase by lazy {
        GetOffersUseCase(repository = jobRepository)
    }

    private val getVacanciesUseCase by lazy {
        GetVacanciesUseCase(repository = jobRepository)
    }

    private val getVacanciesFavoriteUseCase by lazy {
        GetVacanciesFavoriteUseCase(repository = jobRepository)
    }

    private val insertVacancyFavoriteUseCase by lazy {
        InsertVacancyFavoriteUseCase(repository = jobRepository)
    }

    private val deleteVacancyFavoriteUseCase by lazy {
        DeleteVacancyFavoriteUseCase(repository = jobRepository)
    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(
                getOffersUseCase = getOffersUseCase,
                getVacanciesUseCase = getVacanciesUseCase,
                insertVacancyFavoriteUseCase = insertVacancyFavoriteUseCase,
                deleteVacancyFavoriteUseCase = deleteVacancyFavoriteUseCase
            ) as T
        } else if (modelClass.isAssignableFrom(FavoriteVacanciesViewModel::class.java)) {
            return FavoriteVacanciesViewModel(
                getVacanciesFavoriteUseCase = getVacanciesFavoriteUseCase
            ) as T
        } else if (modelClass.isAssignableFrom(RelevantVacanciesViewModel::class.java)) {
            return RelevantVacanciesViewModel(
                getVacanciesUseCase = getVacanciesUseCase,
                insertVacancyFavoriteUseCase = insertVacancyFavoriteUseCase,
                deleteVacancyFavoriteUseCase = deleteVacancyFavoriteUseCase
            ) as T
        }
        throw IllegalArgumentException(context.getString(R.string.unknown_viewmodel_class))
    }
}