package com.medvedev.jobsearch.app.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.medvedev.jobsearch.domain.model.vacancy.Vacancy
import com.medvedev.jobsearch.domain.usecase.DeleteVacancyFavoriteUseCase
import com.medvedev.jobsearch.domain.usecase.GetVacanciesUseCase
import com.medvedev.jobsearch.domain.usecase.InsertVacancyFavoriteUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RelevantVacanciesViewModel(
    private val getVacanciesUseCase: GetVacanciesUseCase,
    private val insertVacancyFavoriteUseCase: InsertVacancyFavoriteUseCase,
    private val deleteVacancyFavoriteUseCase: DeleteVacancyFavoriteUseCase
) : ViewModel() {

    private val _vacancies = MutableLiveData<List<Vacancy>>()
    val vacancies: LiveData<List<Vacancy>>
        get() = _vacancies

    private val _error = MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            try {
                _vacancies.value = withContext(Dispatchers.IO) { getVacanciesUseCase() }
            } catch (e: Exception) {
                _error.value = e.message.toString()
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