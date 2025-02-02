package com.medvedev.jobsearch.data.network.model.offer

import com.google.gson.annotations.SerializedName

data class ButtonDto(
    @SerializedName("text")
    val text: String? = null
)