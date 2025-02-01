package com.medvedev.jobsearch.app.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.medvedev.jobsearch.domain.model.vacancy.Vacancy
import com.medvedev.jobsearch.domain.usecase.GetVacanciesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RelevantVacanciesViewModel(
    private val getVacanciesUseCase: GetVacanciesUseCase
) : ViewModel() {

    private val _state = MutableLiveData<State>()
    val state: LiveData<State>
        get() = _state

    fun loadData() {
        viewModelScope.launch {
            try {
                val vacancies = withContext(Dispatchers.IO) {
                    getVacanciesUseCase()
                }
                _state.value = State.Loaded(vacancies = vacancies)
            } catch (e: Exception) {
                _state.value = State.Error(error = e.message.toString())
            }
        }
    }

    sealed interface State {
        data class Loaded(val vacancies: List<Vacancy>) : State
        data class Error(val error: String) : State
    }
}