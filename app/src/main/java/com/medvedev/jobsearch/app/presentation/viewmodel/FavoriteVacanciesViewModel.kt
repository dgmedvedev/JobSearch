package com.medvedev.jobsearch.app.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.medvedev.jobsearch.domain.model.vacancy.Vacancy
import com.medvedev.jobsearch.domain.usecase.GetVacanciesFavoriteUseCase

class FavoriteVacanciesViewModel(
    private val getVacanciesFavoriteUseCase: GetVacanciesFavoriteUseCase
) : ViewModel() {

    private val _state = MutableLiveData<State>()
    val state: LiveData<State>
        get() = _state

    sealed interface State {
        data class Loaded(val vacancies: List<Vacancy>) : State
        data class Error(val error: String) : State
    }
}