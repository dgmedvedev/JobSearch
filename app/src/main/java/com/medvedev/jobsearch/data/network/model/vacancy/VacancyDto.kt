package com.medvedev.jobsearch.data.network.model.vacancy

import com.google.gson.annotations.SerializedName

data class VacancyDto(
    @SerializedName("id")
    val id: String? = null,
    @SerializedName("lookingNumber")
    val lookingNumber: Int? = null,
    @SerializedName("title")
    val title: String? = null,
    @SerializedName("address")
    val address: AddressDto? = null,
    @SerializedName("company")
    val company: String? = null,
    @SerializedName("experience")
    val experience: ExperienceDto? = null,
    @SerializedName("publishedDate")
    val publishedDate: String? = null,
    @SerializedName("isFavorite")
    val isFavorite: Boolean? = null,
    @SerializedName("salary")
    val salary: SalaryDto? = null,
    @SerializedName("schedules")
    val schedules: List<String>? = null,
    @SerializedName("appliedNumber")
    val appliedNumber: Int? = null,
    @SerializedName("description")
    val description: String? = null,
    @SerializedName("responsibilities")
    val responsibilities: String? = null,
    @SerializedName("questions")
    val questions: List<String>? = null
)