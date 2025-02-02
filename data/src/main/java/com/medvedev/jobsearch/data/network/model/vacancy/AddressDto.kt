package com.medvedev.jobsearch.data.network.model.vacancy

import com.google.gson.annotations.SerializedName

data class AddressDto(
    @SerializedName("town")
    val town: String? = null,
    @SerializedName("street")
    val street: String? = null,
    @SerializedName("house")
    val house: String? = null
)