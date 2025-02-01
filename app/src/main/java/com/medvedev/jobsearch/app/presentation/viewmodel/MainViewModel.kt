package com.medvedev.jobsearch.app.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.medvedev.jobsearch.domain.model.offer.Offer
import com.medvedev.jobsearch.domain.model.vacancy.Vacancy
import com.medvedev.jobsearch.domain.usecase.GetOffersUseCase
import com.medvedev.jobsearch.domain.usecase.GetVacanciesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(
    private val getOffersUseCase: GetOffersUseCase,
    private val getVacanciesUseCase: GetVacanciesUseCase
) : ViewModel() {

    private val _state = MutableLiveData<State>()
    val state: LiveData<State>
        get() = _state

    fun loadData() {
        viewModelScope.launch {
            try {
                val offers = withContext(Dispatchers.IO) {
                    getOffersUseCase()
                }
                _state.value = State.OffersLoaded(offers = offers)
            } catch (error: Exception) {
                _state.value = State.ErrorLoadingOffers(error = error.message.toString())
            }
        }
        viewModelScope.launch {
            try {
                val vacancies = withContext(Dispatchers.IO) {
                    getVacanciesUseCase()
                }
                _state.value = State.VacanciesLoaded(vacancies = vacancies)
            } catch (error: Exception) {
                _state.value = State.ErrorLoadingVacancies(error = error.message.toString())
            }
        }
    }

    sealed interface State {
        data class OffersLoaded(val offers: List<Offer>) : State
        data class VacanciesLoaded(val vacancies: List<Vacancy>) : State
        data class ErrorLoadingOffers(val error: String) : State
        data class ErrorLoadingVacancies(val error: String) : State
    }
}