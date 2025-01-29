package com.medvedev.jobsearch.data.network.model.vacancy

import com.google.gson.annotations.SerializedName

data class SalaryDto(
    @SerializedName("full")
    val full: String? = null,
    @SerializedName("short")
    val _short: String? = null
)