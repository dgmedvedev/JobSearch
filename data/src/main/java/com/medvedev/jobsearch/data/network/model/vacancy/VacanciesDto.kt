package com.medvedev.jobsearch.data.network.model.vacancy

import com.google.gson.annotations.SerializedName

data class VacanciesDto(
    @SerializedName("vacancies")
    val vacancies: List<VacancyDto>? = null
)