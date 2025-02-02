package com.medvedev.jobsearch.app.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.medvedev.jobsearch.domain.model.offer.Offer
import com.medvedev.jobsearch.domain.model.vacancy.Vacancy
import com.medvedev.jobsearch.domain.usecase.DeleteVacancyFavoriteUseCase
import com.medvedev.jobsearch.domain.usecase.GetOffersUseCase
import com.medvedev.jobsearch.domain.usecase.GetVacanciesUseCase
import com.medvedev.jobsearch.domain.usecase.InsertVacancyFavoriteUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(
    private val getOffersUseCase: GetOffersUseCase,
    private val getVacanciesUseCase: GetVacanciesUseCase,
    private val insertVacancyFavoriteUseCase: InsertVacancyFavoriteUseCase,
    private val deleteVacancyFavoriteUseCase: DeleteVacancyFavoriteUseCase
) : ViewModel() {

    private val _offers = MutableLiveData<List<Offer>>()
    val offers: LiveData<List<Offer>>
        get() = _offers

    private val _vacancies = MutableLiveData<List<Vacancy>>()
    val vacancies: LiveData<List<Vacancy>>
        get() = _vacancies

    private val _error = MutableLiveData<Unit>()
    val error: LiveData<Unit>
        get() = _error

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            try {
                _offers.value = withContext(Dispatchers.IO) { getOffersUseCase() }
            } catch (e: Exception) {
                _error.value = Unit
            }
        }
        viewModelScope.launch {
            try {
                _vacancies.value = withContext(Dispatchers.IO) { getVacanciesUseCase() }
            } catch (e: Exception) {
                _error.value = Unit
            }
        }
    }

    fun onVacancyIconPressed(vacancy: Vacancy) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                if (vacancy.isFavorite) {
                    insertVacancyFavoriteUseCase(vacancy)
                } else {
                    deleteVacancyFavoriteUseCase(vacancy)
                }
            }
        }
    }
}