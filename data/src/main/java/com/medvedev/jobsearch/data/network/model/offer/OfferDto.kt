package com.medvedev.jobsearch.data.network.model.offer

import com.google.gson.annotations.SerializedName

data class OfferDto(
    @SerializedName("id")
    val id: String? = null,
    @SerializedName("title")
    val title: String? = null,
    @SerializedName("link")
    val link: String? = null,
    @SerializedName("button")
    val button: ButtonDto? = null
)