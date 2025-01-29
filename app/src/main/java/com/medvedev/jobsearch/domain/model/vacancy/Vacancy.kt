package com.medvedev.jobsearch.domain.model.vacancy

import com.medvedev.jobsearch.data.network.model.vacancy.AddressDto
import com.medvedev.jobsearch.data.network.model.vacancy.ExperienceDto
import com.medvedev.jobsearch.data.network.model.vacancy.SalaryDto

data class Vacancy(
    val id: String?,
    val lookingNumber: Int?,
    val title: String?,
    val address: AddressDto?,
    val company: String?,
    val experience: ExperienceDto?,
    val publishedDate: String?,
    val isFavorite: Boolean?,
    val salary: SalaryDto?,
    val schedules: List<String>?,
    val appliedNumber: Int?,
    val description: String?,
    val responsibilities: String?,
    val questions: List<String>?
)