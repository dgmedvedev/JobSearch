package com.medvedev.jobsearch.data.network.model.offer

import com.google.gson.annotations.SerializedName

data class OffersDto(
    @SerializedName("offers")
    val offers: List<OfferDto>? = null
)