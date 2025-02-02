package com.medvedev.jobsearch.data.network.model.vacancy

import com.google.gson.annotations.SerializedName

data class SalaryDto(
    @SerializedName("full")
    val fullSalary: String? = null,
    @SerializedName("short")
    val shortSalary: String? = null
)