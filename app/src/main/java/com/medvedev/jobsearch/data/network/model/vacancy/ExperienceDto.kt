package com.medvedev.jobsearch.data.network.model.vacancy

import com.google.gson.annotations.SerializedName

data class ExperienceDto(
    @SerializedName("previewText")
    val previewText: String? = null,
    @SerializedName("text")
    val text: String? = null
)